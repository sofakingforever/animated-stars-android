package com.sofakingforever.stars.entities.stars

import android.graphics.Canvas
import android.graphics.Paint
import com.sofakingforever.stars.entities.StarConstraints

class TinyStar(starConstraints: StarConstraints, x: Int, y: Int, color: Int, listener: StarCompleteListener) : BaseStar(starConstraints, x, y, color, listener) {
    override var incrementFactor: Double = Math.random() * .045

    override fun initPaintColor(): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        return paint
    }

    override fun onDraw(canvas: Canvas?): Canvas? {
        paint?.also {
            it.alpha = alphaInt
            canvas?.drawCircle(x.toFloat(), y.toFloat(), starSize.toFloat() / 2f, it)

        }
        return canvas
    }
}