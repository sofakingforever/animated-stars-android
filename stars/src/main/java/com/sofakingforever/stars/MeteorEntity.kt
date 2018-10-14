package com.sofakingforever.stars

import android.graphics.Canvas
import android.graphics.Paint

internal class MeteorEntity(starConstraints: Star.StarConstraints, var x: Int, var y: Int, var color: Int, viewWidth: Int, viewHeight: Int, private val colorListener: () -> Int, private val onDoneListener: () -> Unit) {

    //    private val length: Double = (starConstraints.minStarSize + Math.random() * (starConstraints.maxStarSize - starConstraints.minStarSize))
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var shape: Star.StarShape = Star.StarShape.Dot

    private val meteor: Meteor = Meteor(starConstraints, x, y, color, viewWidth, viewHeight, colorListener, onDoneListener)
    private val trail: Trail = Trail(meteor.star.length, meteor.star.fillPaint)

    init {

        // init fill paint for small and big stars
        fillPaint.color = color


    }

    fun calculateFrame(viewWidth: Int, viewHeight: Int) {
        trail.addPixel(meteor.star.x, meteor.star.y)
        trail.calculatePixels()
        meteor.calculateFrame(viewWidth, viewHeight)
    }

    fun onDraw(canvas: Canvas?): Canvas? {
        var newCanvas = canvas
        newCanvas = meteor.onDraw(newCanvas)
        newCanvas = trail.onDraw(newCanvas)

        return newCanvas
    }

    internal class Meteor(starConstraints: Star.StarConstraints, var x: Int, var y: Int, var color: Int, viewWidth: Int, viewHeight: Int, private val colorListener: () -> Int, private val onDoneListener: () -> Unit) {

        private var onDoneInvoked = false
        internal val star: Star = Star(starConstraints, x, y, false, 1.0, color, viewWidth, viewHeight, colorListener)


        fun calculateFrame(viewWidth: Int, viewHeight: Int) {
//            star.calculateFrame(viewWidth, viewHeight)
            star.x = star.x - (star.length / 2.0).toInt()
            star.y = star.y + (star.length / 2.0).toInt()

            if (star.x < viewWidth * -1 && !onDoneInvoked) {
                onDoneListener.invoke()
                onDoneInvoked = true
            }
        }

        fun onDraw(canvas: Canvas?): Canvas? {
            canvas?.drawCircle(star.x.toFloat(), star.y.toFloat(), star.length.toFloat() / 2f, star.fillPaint)

            return canvas
        }

    }


    internal class Trail(val length: Double, val fillPaint: Paint) {

        private val trailPixels: MutableList<TrailPixel> = mutableListOf()

        fun addPixel(x: Int, y: Int) {
            trailPixels.add(TrailPixel(x, y, length))
        }

        fun calculatePixels() {
            trailPixels.forEach { it.calculatePixel() }
        }

        fun onDraw(canvas: Canvas?): Canvas? {

            val newList = trailPixels.toList()
            newList.forEach {
                val newPaint = fillPaint
                newPaint.alpha = (it.opacity * 255.0).toInt()
                canvas?.drawCircle(it.x.toFloat(), it.y.toFloat(), it.length.toFloat() / 2f, fillPaint)
            }
            return canvas
        }


        internal class TrailPixel(val x: Int, val y: Int, val length: Double) {
            fun calculatePixel() {
                opacity -= 0.015

                if (opacity < 0) {
                    opacity = 0.0
                }
            }

            var opacity: Double = 0.90


        }
    }
}