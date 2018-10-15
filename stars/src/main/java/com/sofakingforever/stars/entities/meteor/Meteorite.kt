package com.sofakingforever.stars.entities.meteor

import android.graphics.Canvas
import android.graphics.Paint

internal class Meteorite(var x: Int, var y: Int, private val starSize: Int, var color: Int, val paint: Paint, val listener: MeteoriteCompleteListener) {

    private var finished = false
    private val factor = starSize * (Math.random() * 1.5)

    fun calculateFrame(viewWidth: Int, viewHeight: Int) {

        if (finished) {
            return
        }

        // go left
        x -= factor.toInt()

        // go down
        y += factor.toInt()

        if (x < (viewWidth * -0.5)) {
            listener.onMeteoriteComplete()
            finished = true
        }
    }

    fun onDraw(canvas: Canvas?): Canvas? {
        canvas?.drawCircle(x.toFloat(), y.toFloat(), starSize.toFloat() / 2f, paint)

        return canvas
    }

    interface MeteoriteCompleteListener {
        fun onMeteoriteComplete()
    }
}