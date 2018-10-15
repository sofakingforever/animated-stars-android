package com.sofakingforever.stars.entities.stars

import android.graphics.Canvas
import android.graphics.Paint
import com.sofakingforever.stars.entities.StarConstraints

class RoundyStar(starConstraints: StarConstraints, x: Int, y: Int, color: Int, listener: StarCompleteListener) : BaseStar(starConstraints, x, y, color, listener) {
    override fun initPaintColor(): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = starSize.toFloat() / 4f
        return paint
    }

    override var incrementFactor: Double = Math.random() * .025

    override fun onDraw(canvas: Canvas?): Canvas? {
        paint?.also {
            it.alpha = alphaInt
            canvas?.drawCircle(x.toFloat(), y.toFloat(), starSize.toFloat() / 2f, it)

        }
        return canvas
    }
}