package com.sofakingforever.stars

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * Single star in sky view
 */
internal class Star(starConstraints: StarConstraints, var x: Int, var y: Int, var randomizeLocation: Boolean, var opacity: Double, var color: Int, viewWidth: Int, viewHeight: Int, private val colorListener: () -> Int) {


    var alpha: Int = 0
    var factor: Int = 1
    var increment: Double

    internal val length: Double = (starConstraints.minStarSize + Math.random() * (starConstraints.maxStarSize - starConstraints.minStarSize))
    internal val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)


    private var shape: StarShape

    private lateinit var hRect: RectF
    private lateinit var vRect: RectF

    /**
     * init paint, shape and some parameters
     */
    init {

        // init fill paint for small and big stars
        fillPaint.color = color

        // init stroke paint for the circle stars
        strokePaint.color = color
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = length.toFloat() / 4f

        // init shape of star according to random size
        shape = if (length >= starConstraints.bigStarThreshold) {

            // big star ones will randomly be Star or Circle

            if (Math.random() < 0.7) {
                StarShape.Star
            } else {
                StarShape.Circle
            }
        } else {
            // small ones will be dots

            StarShape.Dot
        }

        // the alpha incerment speed will be decided according to the star's size
        increment = when (shape) {
            StarShape.Circle -> {
                Math.random() * .025
            }
            StarShape.Star -> {
                Math.random() * .030
            }
            StarShape.Dot -> {
                Math.random() * .045
            }
        }

        initLocationAndRectangles(viewWidth, viewHeight, randomizeLocation)
    }

    /**
     * calculate single frame for star (factor, opacity, and location if needed)
     */
    fun calculateFrame(viewWidth: Int, viewHeight: Int) {

        // calculate direction / factor of opacity

        if (opacity >= 1 || opacity <= 0) {
            factor *= -1
        }

        // calculate new opacity for star
        opacity += increment * factor

        // convert to int-based alpha
        alpha = (opacity * 255.0).toInt()

        when {
            alpha > 255 -> {
                // reset alpha to full
                alpha = 255
            }
            alpha <= 0 -> {
                // reset alpha to 0
                alpha = 0

                // and relocate star
                initLocationAndRectangles(viewWidth, viewHeight, true)

                color = colorListener.invoke()

                // init fill paint for small and big stars
                fillPaint.color = color

                // init stroke paint for the circle stars
                strokePaint.color = color
                strokePaint.style = Paint.Style.STROKE
                strokePaint.strokeWidth = length.toFloat() / 4f


            }
        }


    }


    /**
     * init star's position and rectangles if needed
     */
    private fun initLocationAndRectangles(viewWidth: Int, viewHeight: Int, randomizeLocation: Boolean) {

        if (randomizeLocation) {
            // randomize location

            x = Math.round(Math.random() * viewWidth).toInt()
            y = Math.round(Math.random() * viewHeight).toInt()

        }
        // calculate rectangles for big stars

        if (shape == StarShape.Star) {

            val hLeft = (x - length / 2).toFloat()
            val hRight = (x + length / 2).toFloat()
            val hTop = (y - length / 6).toFloat()
            val hBottom = (y + length / 6).toFloat()

            hRect = RectF(hLeft, hTop, hRight, hBottom)


            val vLeft = (x - length / 6).toFloat()
            val vRight = (x + length / 6).toFloat()
            val vTop = (y - length / 2).toFloat()
            val vBottom = (y + length / 2).toFloat()

            vRect = RectF(vLeft, vTop, vRight, vBottom)
        }


    }

    internal fun onDraw(canvas: Canvas?): Canvas? {

        // set current alpha to paint

        fillPaint.alpha = alpha
        strokePaint.alpha = alpha

        // onDraw according to shape

        when (shape) {
            StarShape.Dot -> {
                canvas?.drawCircle(x.toFloat(), y.toFloat(), length.toFloat() / 2f, fillPaint)
            }
            StarShape.Star -> {
                canvas?.drawRoundRect(hRect, 6f, 6f, fillPaint)
                canvas?.drawRoundRect(vRect, 6f, 6f, fillPaint)
            }
            StarShape.Circle -> {
                canvas?.drawCircle(x.toFloat(), y.toFloat(), length.toFloat() / 2f, strokePaint)
            }
        }

        return canvas

    }

    internal enum class StarShape {
        Circle, Star, Dot
    }

    interface Listener {
        fun getNewColor(): Int

    }

    internal class StarConstraints(val minStarSize: Int, val maxStarSize: Int, val bigStarThreshold: Int)
}

