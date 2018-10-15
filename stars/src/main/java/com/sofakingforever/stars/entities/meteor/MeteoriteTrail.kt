package com.sofakingforever.stars.entities.meteor

import android.graphics.Canvas
import android.graphics.Paint

internal class MeteoriteTrail(val length: Int, val fillPaint: Paint) {

    private val trailPixels: MutableList<TrailPixel> = mutableListOf()

    private var isFirst: Boolean = true

    fun addPixel(x: Int, y: Int) {

        trailPixels.add(TrailPixel(x , y, length, isFirst))

        if (isFirst) {
            isFirst = false
        }

//        for (i in length downTo 1 ) {
//            trailPixels.add(TrailPixel(x + i, y - i, length, isFirst))
//
//            if (isFirst) {
//                isFirst = false
//            }
//        }

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


    internal class TrailPixel(val x: Int, val y: Int, val length: Int, first: Boolean) {

        var opacity: Double = if (first) 1.0 else 0.70

        fun calculatePixel() {
            opacity -= 0.01

            if (opacity < 0) {
                opacity = 0.0
            }
        }


    }
}