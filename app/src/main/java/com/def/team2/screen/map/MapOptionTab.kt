package com.def.team2.screen.map

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.def.team2.R


class MapOptionTab @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  : LinearLayout(context, attrs, defStyleAttr) {
    private val animatorSet = AnimatorSet()
    private val animator = mutableListOf<ValueAnimator>()
    private val path = Path()
    private val paint = Paint()
    private val decelerateInterpolator = DecelerateInterpolator(2.5f)

    private var button = FloatArray(5)
    private var state = State.CLOSED
    private var animationDuration: Int = 0
    private var width: Float = 0.toFloat()
    private var height: Float = 0.toFloat()
    private var buttonLeftInitial: Float = 0.toFloat()
    private var buttonRightInitial: Float = 0.toFloat()
    private var yPosition: Float = 0.toFloat()
    private var iconOpenedDrawable: Drawable? = null
    private var iconClosedDrawable: Drawable? = null
    private var onClickListener: OnClickListener? = null
    private var backgroundColorStart: Int = 0
    private var backgroundColorEnd: Int = Color.WHITE
    private var linearGradient: LinearGradient? = null
    private var buttonSize: Int = 0
    private var buttonWidth: Int = 0
    private var buttonPosition: Int = BUTTON_POSITION_LEFT
    private var buttonMarginRight: Int = 0
    private var buttonMarginLeft: Int = 0
    private var showMenuItems: Boolean = false
    /**
     * @return True if menu is opened. False otherwise.
     */
    val isOpened: Boolean
        get() {
            return state == State.OPENED
        }

    private enum class State {
        OPENED,
        CLOSED
    }

    init {
        setWillNotDraw(false)
        setupAttributes()
        setupAnimators()
        paint.isAntiAlias = true
    }

    private fun setupAttributes() {
        iconOpenedDrawable = ResourcesCompat.getDrawable(resources, R.drawable.btn_map_option_minus, null)
        iconClosedDrawable = ResourcesCompat.getDrawable(resources, R.drawable.btn_map_option_plus, null)
        backgroundColorStart = ContextCompat.getColor(context, R.color.colorLightGreen)
        backgroundColorEnd = Color.WHITE
        buttonSize = resources.getDimensionPixelSize(R.dimen.map_option_button_size)
        buttonWidth = resources.getDimensionPixelSize(R.dimen.map_option_button_width)
        gravity = Gravity.CENTER
        if (context is Activity) {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            linearGradient = LinearGradient(
                0f,
                0f,
                displayMetrics.widthPixels.toFloat(),
                0f,
                backgroundColorStart,
                backgroundColorEnd,
                Shader.TileMode.MIRROR
            )
        }
    }

    private fun setupAnimators() {
        for (i in 0..3) {
            animator.add(ValueAnimator())
        }
        animator[LEFT].addUpdateListener { valueAnimator -> button[LEFT] = valueAnimator.animatedValue as Float }
        animator[RIGHT].addUpdateListener { valueAnimator -> button[RIGHT] = valueAnimator.animatedValue as Float }
        animator[TOP].addUpdateListener { valueAnimator -> button[TOP] = valueAnimator.animatedValue as Float }
        animator[BOTTOM].addUpdateListener { valueAnimator ->
            button[BOTTOM] = valueAnimator.animatedValue as Float
            invalidate()
        }
        animationDuration = resources.getInteger(R.integer.animationDuration)
        animatorSet.run {
            duration = animationDuration.toLong()
            interpolator = decelerateInterpolator
            playTogether(animator as Collection<Animator>)
        }
    }

    private fun setupMenuItems() {
        for (i in 0 until childCount) {
            getChildAt(i).visibility = if (showMenuItems) VISIBLE else GONE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupMenuItems()
    }

    /**
     * Opens the menu if it's closed or close it if it's opened.
     */
    fun toggle() {
        if (state == State.OPENED)
            close()
        else
            open()
    }

    /**
     * Open the menu.
     */
    fun open() {
        state = State.OPENED
        showIcons(true)
        animator[LEFT].setFloatValues(button[LEFT], 0f)
        animator[RIGHT].setFloatValues(button[RIGHT], width)
        animator[TOP].setFloatValues(button[TOP], 0f)
        animator[BOTTOM].setFloatValues(button[BOTTOM], height)
        animatorSet.cancel()
        animatorSet.start()
        if (iconOpenedDrawable is Animatable) {
            (iconOpenedDrawable as Animatable).start()
        }
    }

    /**
     * Close the menu.
     */
    fun close() {
        updateDimensions(width, height)
        state = State.CLOSED
        showIcons(false)
        animator[LEFT].setFloatValues(0f, button[LEFT])
        animator[RIGHT].setFloatValues(width, button[RIGHT])
        animator[TOP].setFloatValues(0f, button[TOP])
        animator[BOTTOM].setFloatValues(height, button[BOTTOM])
        animatorSet.cancel()
        animatorSet.start()
        if (iconClosedDrawable is Animatable) {
            (iconClosedDrawable as Animatable).start()
        }
        animate()
            .y(yPosition)
            .setDuration(animationDuration.toLong())
            .setInterpolator(decelerateInterpolator)
            .start()
    }

    override fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateDimensions(w.toFloat(), h.toFloat())
        yPosition = y
    }

    override fun onDraw(canvas: Canvas) {

        linearGradient?.run {
            paint.shader = this
            paint.setShadowLayer(0f, 0f, 0f, Color.parseColor("#41000000"))
        }

//        Log.e("Position", "LEFT: ${button[LEFT]}, TOP: ${button[TOP]}, RIGHT: ${button[RIGHT]}, BOTTOM: ${button[BOTTOM]}")

        if (state == State.CLOSED) {
            canvas.drawPath(
                createRectPath(
                    button[LEFT],
                    button[TOP],
                    button[RIGHT] -100,
                    button[BOTTOM]
                ),
                paint
            )
            iconClosedDrawable?.draw(canvas)
        } else {
            canvas.drawPath(
                createRectPath(
                    button[LEFT],
                    button[TOP],
                    button[RIGHT],
                    button[BOTTOM]
                ),
                paint
            )
            iconOpenedDrawable?.draw(canvas)
        }
    }

    private fun updateDimensions(w: Float, h: Float) {
        width = w
        height = h
        setButtonPosition(width)

        iconOpenedDrawable?.setBounds(
            button[LEFT].toInt(),
            button[TOP].toInt(),
            (button[LEFT] + buttonWidth).toInt(),
            button[BOTTOM].toInt()
        )

        iconClosedDrawable?.setBounds(
            button[LEFT].toInt(),
            button[TOP].toInt(),
            (button[LEFT] + buttonWidth).toInt(),
            button[BOTTOM].toInt()
        )
    }

    private fun setButtonPosition(width: Float) {
        when (buttonPosition) {
            BUTTON_POSITION_CENTER -> button[LEFT] = ((width / 2) - (buttonSize / 2))
            BUTTON_POSITION_LEFT -> button[LEFT] = 0f
            else -> button[LEFT] = width - buttonSize
        }
        val padding = buttonMarginLeft - buttonMarginRight
        button[LEFT] += padding.toFloat()
        button[RIGHT] = button[LEFT] + buttonSize
        button[TOP] = (height - buttonSize) / 2
        button[BOTTOM] = (height + buttonSize) / 2
        buttonLeftInitial = button[LEFT]
        buttonRightInitial = button[RIGHT]
    }

    private fun showIcons(show: Boolean) {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            view.scaleX = if (show) 0f else 1f
            view.scaleY = if (show) 0f else 1f
            view.visibility = VISIBLE
            view.alpha = if (show) 0f else 1f
            view.animate()
                .scaleX(if (show) 1f else 0f)
                .scaleY(if (show) 1f else 0f)
                .translationY(0f)
                .alpha(if (show) 1f else 0f)
                .setInterpolator(decelerateInterpolator)
                .setDuration(if (show) animationDuration / 2L else animationDuration / 3L)
                .setStartDelay(if (show) animationDuration / 3L else 0L)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        view.visibility = if (show) VISIBLE else GONE
                    }
                })
                .start()
        }
    }

    private fun createRectPath(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ): Path {
        path.reset()
        val width = right - left
        val height = bottom - top

        return path.apply {
            moveTo(right, top)
            rLineTo(-width, 0f)
            rLineTo(0f, height)
            rLineTo(width, 0f)
            rLineTo(0f, -height)
            close()
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return (event.x > buttonLeftInitial && event.x < buttonRightInitial)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if ((event.x > buttonLeftInitial && event.getX() < buttonRightInitial) && (event.action == MotionEvent.ACTION_UP)) {
            onClickListener?.onClick(this)
        }
        return true
    }

    override fun onDetachedFromWindow() {
        onDestroy()
        super.onDetachedFromWindow()
    }

    private fun onDestroy() {
        iconOpenedDrawable = null
        iconClosedDrawable = null
        animator.clear()
        onClickListener = null
    }

    companion object {
        const val BUTTON_POSITION_LEFT = 0
        const val BUTTON_POSITION_CENTER = 1
        const val BUTTON_POSITION_RIGHT = 2
        private const val LEFT = 0
        private const val RIGHT = 1
        private const val TOP = 2
        private const val BOTTOM = 3
    }
}