package com.sofakingforever.stars.entities.meteor

import android.graphics.Canvas
import android.graphics.Paint

internal class MeteoriteTrail(val length: Int, val fillPaint: Paint) {

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


    internal class TrailPixel(val x: Int, val y: Int, val length: Int) {

        var opacity: Double = 1.0

        fun calculatePixel() {
            opacity -= 0.012

            if (opacity < 0) {
                opacity = 0.0
            }
        }


    }
}