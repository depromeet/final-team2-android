package com.def.team2.screen.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView


class VerticalTextView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var topDown = true

    init {

        val gravity = gravity
        topDown = if (Gravity.isVertical(gravity) && gravity and Gravity.VERTICAL_GRAVITY_MASK == Gravity.BOTTOM) {
            setGravity(gravity and Gravity.HORIZONTAL_GRAVITY_MASK or Gravity.TOP)
            false
        } else
            true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState

        canvas.save()

        if (topDown) {
            canvas.translate(width.toFloat(), 0f)
            canvas.rotate(90f)
        } else {
            canvas.translate(0f, height.toFloat())
            canvas.rotate(-90f)
        }


        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())

        layout.draw(canvas)
        canvas.restore()
    }


//    private val path = Path()
//    private val bounds = Rect()
//    private var direction = ORIENTATION_DOWN_TO_UP
//    private var isShowUnderLine = false
//
//    init {
//        attrs?.let {
//            val a = context.obtainStyledAttributes(it, R.styleable.VerticalTextView)
//            direction = a.getInt(R.styleable.VerticalTextView_direction, 1)
//            isShowUnderLine = a.getBoolean(R.styleable.VerticalTextView_show_underline, false)
//
//            a.recycle()
//
//            requestLayout()
//            invalidate()
//        }
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        paint.getTextBounds(text.toString(), 0, text.length, bounds)
//
//        if (direction == ORIENTATION_LEFT_TO_RIGHT || direction == ORIENTATION_RIGHT_TO_LEFT) {
//            setMeasuredDimension(
//                measureHeight(widthMeasureSpec),
//                measureWidth(heightMeasureSpec)
//            )
//        } else if (direction == ORIENTATION_UP_TO_DOWN || direction == ORIENTATION_DOWN_TO_UP) {
//            setMeasuredDimension(
//                measureWidth(widthMeasureSpec),
//                measureHeight(heightMeasureSpec)
//            )
//        }
//
//    }
//
//    private fun measureWidth(measureSpec: Int): Int {
//        val specMode = MeasureSpec.getMode(measureSpec)
//        val specSize = MeasureSpec.getSize(measureSpec)
//
//        return if (specMode == MeasureSpec.EXACTLY) {
//            specSize
//        } else {
//            val result = bounds.height() + paddingTop + paddingBottom
//            if (specMode == MeasureSpec.AT_MOST) {
//                min(result, specSize)
//            } else {
//                result
//            }
//        }
//    }
//
//    private fun measureHeight(measureSpec: Int): Int {
//        val specMode = MeasureSpec.getMode(measureSpec)
//        val specSize = MeasureSpec.getSize(measureSpec)
//
//        return if (specMode == MeasureSpec.EXACTLY) {
//            specSize
//        } else {
//            val result = bounds.width() + paddingStart + paddingEnd
//            if (specMode == MeasureSpec.AT_MOST) {
//                min(result, specSize)
//            } else {
//                result
//            }
//        }
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        canvas.save()
//
//        var startX = 0
//        var startY = 0
//        var stopX = 0
//        var stopY = 0
//        when (direction) {
//            ORIENTATION_UP_TO_DOWN -> {
//                startX = width - bounds.height() shr 1
//                startY = height - bounds.width() shr 1
//                stopX = width - bounds.height() shr 1
//                stopY = height + bounds.width() shr 1
//                path.moveTo(startX.toFloat(), startY.toFloat())
//                path.lineTo(stopX.toFloat(), stopY.toFloat())
//            }
//            ORIENTATION_DOWN_TO_UP -> {
//                startX = width + bounds.height() shr 1
//                startY = height + bounds.width() shr 1
//                stopX = width + bounds.height() shr 1
//                stopY = height - bounds.width() shr 1
//                path.moveTo(startX.toFloat(), startY.toFloat())
//                path.lineTo(stopX.toFloat(), stopY.toFloat())
//            }
//            ORIENTATION_LEFT_TO_RIGHT -> {
//                startX = width - bounds.width() shr 1
//                startY = height + bounds.height() shr 1
//                stopX = width + bounds.width() shr 1
//                stopY = height + bounds.height() shr 1
//                path.moveTo(startX.toFloat(), startY.toFloat())
//                path.lineTo(stopX.toFloat(), stopY.toFloat())
//            }
//            ORIENTATION_RIGHT_TO_LEFT -> {
//                startX = width + bounds.width() shr 1
//                startY = height - bounds.height() shr 1
//                stopX = width - bounds.width() shr 1
//                stopY = height - bounds.height() shr 1
//                path.moveTo(startX.toFloat(), startY.toFloat())
//                path.lineTo(stopX.toFloat(), stopY.toFloat())
//            }
//        }
//
//        this.paint.color = this.currentTextColor
//        canvas.drawTextOnPath(text.toString(), path, 0f, 0f, this.paint)
//
////        val linePaint = Paint()
////        linePaint.color = Color.BLACK
////        linePaint.style = Paint.Style.STROKE
////        linePaint.strokeWidth = 20f
////
////        Log.e("check location", "startX: ${startX}, startY: ${startY}, stopX: ${stopX}, stopY: ${stopY}")
////        canvas.drawLine(startX.toFloat() + 10, startY.toFloat(), stopX.toFloat() + 10, stopY.toFloat(), linePaint)
////        canvas.drawLine(47f, 0f, 47f, 132f, linePaint)
//
//        canvas.restore()
//    }
//
//    companion object {
//        const val ORIENTATION_UP_TO_DOWN = 0
//        const val ORIENTATION_DOWN_TO_UP = 1
//        const val ORIENTATION_LEFT_TO_RIGHT = 2
//        const val ORIENTATION_RIGHT_TO_LEFT = 3
//    }
}