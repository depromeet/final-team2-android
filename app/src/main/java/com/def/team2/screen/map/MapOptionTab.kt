package com.def.team2.screen.map

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.def.team2.R


class MapOptionTab : LinearLayout {
    private val animatorSet = AnimatorSet()
    private val animator = mutableListOf<ValueAnimator>()
    private var button = FloatArray(5)
    private val path = Path()
    private var state = State.CLOSED
    private var paint: Paint = Paint()
    private var animationDuration: Int = 0
    private var width: Float = 0.toFloat()
    private var height: Float = 0.toFloat()
    private var buttonLeftInitial: Float = 0.toFloat()
    private var buttonRightInitial: Float = 0.toFloat()
    private var yPosition: Float = 0.toFloat()
    private var iconOpenedDrawable: Drawable? = null
    private var iconClosedDrawable: Drawable? = null
    private var onClickListener: OnClickListener? = null
    //Custom XML Attributes
    private var backgroundColors: Int = 0
    private var buttonSize: Int = 0
    private var buttonPosition: Int = 0
    private var buttonMarginRight: Int = 0
    private var buttonMarginLeft: Int = 0
    private var menuAnchor: Int = 0
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

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        setWillNotDraw(false)
        setupAttributes(attrs)
        gravity = Gravity.CENTER
        setupAnimators()
        setupPaint()
    }

    private fun setupAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MapOptionTab, 0, 0)
        iconOpenedDrawable = if (typedArray.hasValue(R.styleable.MapOptionTab_tbm_iconOpened)) {
            typedArray.getDrawable(R.styleable.MapOptionTab_tbm_iconOpened)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.icon_animated, null)
        }
        iconClosedDrawable = if (typedArray.hasValue(R.styleable.MapOptionTab_tbm_iconClosed)) {
            typedArray.getDrawable(R.styleable.MapOptionTab_tbm_iconClosed)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.icon_close_animated, null)
        }
        backgroundColors = typedArray.getColor(
            R.styleable.MapOptionTab_tbm_backgroundColor,
            ContextCompat.getColor(context, R.color.red)
        )
        buttonSize = typedArray.getDimensionPixelSize(
            R.styleable.MapOptionTab_tbm_buttonSize,
            resources.getDimensionPixelSize(R.dimen.defaultButtonSize)
        )
        buttonMarginRight =
            typedArray.getDimensionPixelSize(R.styleable.MapOptionTab_tbm_buttonMarginRight, 0)
        buttonMarginLeft =
            typedArray.getDimensionPixelSize(R.styleable.MapOptionTab_tbm_buttonMarginLeft, 0)
        buttonPosition =
            typedArray.getInt(R.styleable.MapOptionTab_tbm_buttonPosition, BUTTON_POSITION_CENTER)
        menuAnchor = typedArray.getInt(R.styleable.MapOptionTab_tbm_menuAnchor, MENU_ANCHOR_BOTTOM)
        showMenuItems = typedArray.getBoolean(R.styleable.MapOptionTab_tbm_showItems, false)
        typedArray.recycle()
    }

    private fun setupAnimators() {
        for (i in 0..4) {
            animator.add(ValueAnimator())
        }
        animator[LEFT].addUpdateListener { valueAnimator -> button[LEFT] = valueAnimator.animatedValue as Float }
        animator[RIGHT].addUpdateListener { valueAnimator -> button[RIGHT] = valueAnimator.animatedValue as Float }
        animator[TOP].addUpdateListener { valueAnimator -> button[TOP] = valueAnimator.animatedValue as Float }
        animator[BOTTOM].addUpdateListener { valueAnimator -> button[BOTTOM] = valueAnimator.animatedValue as Float }
        animator[RADIUS].addUpdateListener { valueAnimator ->
            button[RADIUS] = valueAnimator.animatedValue as Float
            invalidate()
        }
        animationDuration = resources.getInteger(R.integer.animationDuration)
        animatorSet.duration = animationDuration.toLong()
        animatorSet.interpolator = DECELERATE_INTERPOLATOR
        animatorSet.playTogether(animator as Collection<Animator>)
    }

    private fun setupMenuItems() {
        for (i in 0 until childCount) {
            getChildAt(i).visibility = if (showMenuItems) VISIBLE else GONE
        }
    }

    private fun setupPaint() {
        paint = Paint()
        paint.color = backgroundColors
        paint.isAntiAlias = true
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
        animator[RADIUS].setFloatValues(button[RADIUS], 0f)
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
        animator[RADIUS].setFloatValues(0f, button[RADIUS])
        animator[TOP].setFloatValues(0f, button[TOP])
        animator[BOTTOM].setFloatValues(height, button[BOTTOM])
        animatorSet.cancel()
        animatorSet.start()
        if (iconClosedDrawable is Animatable) {
            (iconClosedDrawable as Animatable).start()
        }
        this.animate()
            .y(yPosition)
            .setDuration(animationDuration.toLong())
            .setInterpolator(DECELERATE_INTERPOLATOR)
            .start()
    }

    /**
     * Sets MapOptionTab's background color from given resource.
     * @param colorResId Color resource id. For example: R.color.holo_blue_light
     */
    fun setMenuBackgroundColor(colorResId: Int) {
        backgroundColors = ContextCompat.getColor(context, colorResId)
        paint.color = backgroundColors
        invalidate()
    }

    /**
     * Set position of 'Open Menu' button.
     * @param position One of: {@link #BUTTON_POSITION_CENTER}, {@link #BUTTON_POSITION_LEFT}, {@link #BUTTON_POSITION_RIGHT}.
     */
    fun setButtonPosition(position: Int) {
        buttonPosition = position
        invalidate()
    }

    /**
     * Sets diameter of 'Open Menu' button.
     * @param size Diameter in pixels.
     */
    fun setButtonSize(size: Int) {
        buttonSize = size
        invalidate()
    }

    /**
     * Sets left margin for 'Open Menu' button.
     * @param margin Left margin in pixels
     */
    fun setButtonMarginLeft(margin: Int) {
        buttonMarginLeft = margin
    }

    /**
     * Sets right margin for 'Open Menu' button.
     * @param margin Right margin in pixels
     */
    fun setButtonMarginRight(margin: Int) {
        buttonMarginRight = margin
    }

    /**
     * Set anchor point of the menu. Can be either bottom or top.
     * @param anchor One of: {@link #MENU_ANCHOR_BOTTOM}, {@link #MENU_ANCHOR_TOP}.
     */
    fun setAnchor(anchor: Int) {
        menuAnchor = anchor
    }

    /**
     * Sets the passed drawable as the drawable to be used in the open state.
     * @param openDrawable The open state drawable
     * */
    fun setIconOpenDrawable(openDrawable: Drawable) {
        this.iconOpenedDrawable = openDrawable
        invalidate()
    }

    /**
     * Sets the passed drawable as the drawable to be used in the closed state.
     * @param closeDrawable The closed state drawable
     * */
    fun setIconCloseDrawable(closeDrawable: Drawable) {
        this.iconClosedDrawable = closeDrawable
        invalidate()
    }

    /**
     * Sets the passed drawable as the drawable to be used in the open state.
     * @param openDrawable The open state drawable
     * */
    fun setIconOpenedDrawable(openDrawable: Drawable) {
        this.iconOpenedDrawable = openDrawable
        invalidate()
    }

    /**
     * Sets the passed drawable as the drawable to be used in the closed state.
     * @param closeDrawable The closed state drawable
     * */
    fun setIconClosedDrawable(closeDrawable: Drawable) {
        this.iconClosedDrawable = closeDrawable
        invalidate()
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
        canvas.drawPath(
            createRoundedRectPath(
                button[LEFT],
                button[TOP],
                button[RIGHT],
                button[BOTTOM],
                button[RADIUS],
                button[RADIUS]
            ), paint
        )
        if (state == State.CLOSED) {
            iconClosedDrawable?.draw(canvas)
        } else {
            iconOpenedDrawable?.draw(canvas)
        }
    }

    private fun updateDimensions(w: Float, h: Float) {
        width = w
        height = h
        button[RADIUS] = buttonSize.toFloat()
        setButtonPosition(width)
        val ratio = if (iconClosedDrawable is Animatable) {
            3
        } else {
            5
        }
        val iconLeft = button[LEFT] + buttonSize / ratio
        val iconTop = (height - buttonSize) / 2 + buttonSize / ratio
        val iconRight = button[RIGHT] - buttonSize / ratio
        val iconBottom = (height + buttonSize) / 2 - buttonSize / ratio
        iconOpenedDrawable?.setBounds(
            iconLeft.toInt(),
            iconTop.toInt(),
            iconRight.toInt(),
            iconBottom.toInt()
        )
        iconClosedDrawable?.setBounds(
            iconLeft.toInt(),
            iconTop.toInt(),
            iconRight.toInt(),
            iconBottom.toInt()
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
            val translation =
                if (menuAnchor == MENU_ANCHOR_BOTTOM) view.height else -view.height
            view.translationY = if (show) translation.toFloat() else 0f
            view.scaleX = if (show) 0f else 1f
            view.scaleY = if (show) 0f else 1f
            view.visibility = VISIBLE
            view.alpha = if (show) 0f else 1f
            view.animate()
                .scaleX(if (show) 1f else 0f)
                .scaleY(if (show) 1f else 0f)
                .translationY(0f)
                .alpha(if (show) 1f else 0f)
                .setInterpolator(DECELERATE_INTERPOLATOR)
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

    private fun createRoundedRectPath(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        rx: Float,
        ry: Float
    ): Path {
        path.reset()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return createRoundedRectPathApi21(path, left, top, right, bottom, rx, ry)
        } else {
            return createRoundedRectPathPreApi21(path, left, top, right, bottom, rx, ry)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createRoundedRectPathApi21(
        path: Path,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        rx: Float,
        ry: Float
    ): Path {
        var rxValue = if (rx<0) 0f else rx
        var ryValue = if (ry<0) 0f else ry
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) rxValue = width / 2
        if (ry > height / 2) ryValue = height / 2

        val widthMinusCorners = (width - (2 * rxValue))
        val heightMinusCorners = (height - (2 * ryValue))
        path.moveTo(right, top + ryValue)
        path.arcTo(right - 2 * rxValue, top, right, top + 2 * ryValue, 0f, -90f, false)
        path.rLineTo(-widthMinusCorners, 0f)
        path.arcTo(left, top, left + 2 * rxValue, top + 2 * ryValue, 270f, -90f, false)
        path.rLineTo(0f, heightMinusCorners)
        path.arcTo(left, bottom - 2 * ryValue, left + 2 * rxValue, bottom, 180f, -90f, false)
        path.rLineTo(widthMinusCorners, 0f)
        path.arcTo(right - 2 * rxValue, bottom - 2 * ryValue, right, bottom, 90f, -90f, false)
        path.rLineTo(0f, -heightMinusCorners)
        path.close()
        return path
    }

    private fun createRoundedRectPathPreApi21(
        path: Path,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        rx: Float,
        ry: Float
    ): Path {
        var rxValue = if (rx<0) 0f else rx
        var ryValue = if (ry<0) 0f else ry
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) rxValue = width / 2
        if (ry > height / 2) ryValue = height / 2

        val widthMinusCorners = (width - (2 * rxValue))
        val heightMinusCorners = (height - (2 * ryValue))
        path.moveTo(right, top + ryValue)
        path.rQuadTo(0f, -ryValue, -rxValue, -ryValue)
        path.rLineTo(-widthMinusCorners, 0f)
        path.rQuadTo(-rxValue, 0f, -rxValue, ryValue)
        path.rLineTo(0f, heightMinusCorners)
        path.rQuadTo(0f, ryValue, rxValue, ryValue)
        path.rLineTo(widthMinusCorners, 0f)
        path.rQuadTo(rxValue, 0f, rxValue, -ryValue)
        path.rLineTo(0f, -heightMinusCorners)
        path.close()
        return path
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
//        animator = null
//        button = null
//        onClickListener = null
    }

    companion object {
        const val BUTTON_POSITION_LEFT = 0
        const val BUTTON_POSITION_CENTER = 1
        const val BUTTON_POSITION_RIGHT = 2
        const val MENU_ANCHOR_BOTTOM = 3
        const val MENU_ANCHOR_TOP = 4
        private val DECELERATE_INTERPOLATOR = DecelerateInterpolator(2.5f)
        private const val LEFT = 0
        private const val RIGHT = 1
        private const val TOP = 2
        private const val BOTTOM = 3
        private const val RADIUS = 4
    }
}