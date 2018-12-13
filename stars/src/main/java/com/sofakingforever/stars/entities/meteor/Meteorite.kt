package com.sofakingforever.stars.entities.meteor

import android.graphics.*
import android.support.v4.graphics.ColorUtils


internal class Meteorite(val smallestWidth: Int, var x: Int, var y: Int, val starSize: Int, var color: Int, val listener: Meteorite.MeteoriteCompleteListener) {

    private fun getTrailAlpha(): Int {
//        val randomFactor = Math.random() * 255
//        return randomFactor.toInt()
        return 200 + (Math.random() * 55).toInt()



    }

    private val trailLength = (200 + (Math.random() * (smallestWidth / 4))).toFloat()
    private val trailColor: Int = ColorUtils.setAlphaComponent(color, getTrailAlpha())
    private var finished = false
    private val factor = starSize * (Math.random() * 1.9f)


    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = color
    }

    private val trailPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.strokeWidth =
                starSize.toFloat()
    }


    fun calculateFrame(viewWidth: Int, viewHeight: Int) {

        if (finished) {
            return
        }

        // go left
        x -= factor.toInt()

        // go down
        y += factor.toInt()



        trailPaint.shader = LinearGradient(
                x.toFloat(),
                y.toFloat(),
                x.toFloat() + trailLength,
                y.toFloat() - trailLength,
                trailColor,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP)


        if (x < (viewWidth * -0.5)) {
            listener.onMeteoriteComplete()
            finished = true
        }
    }

    fun onDraw(canvas: Canvas?): Canvas? {

        canvas?.drawLine(x.toFloat(), y.toFloat(), x.toFloat() + trailLength, y.toFloat() - trailLength, trailPaint)
        canvas?.drawCircle(x.toFloat(), y.toFloat(), starSize.toFloat() / 2f, starPaint)
        return canvas
    }


    interface MeteoriteCompleteListener {
        fun onMeteoriteComplete()
    }
}

