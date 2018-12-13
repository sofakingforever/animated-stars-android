package com.sofakingforever.stars.entities.stars

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.sofakingforever.stars.entities.StarConstraints

class ShinyStar(starConstraints: StarConstraints, x: Int, y: Int, color: Int, listener: StarCompleteListener) : BaseStar(starConstraints, x, y, color, listener) {


    private var hRect: RectF? = null
    private var vRect: RectF? = null

    override var incrementFactor: Double = Math.random() * .030

    override fun initPaintColor(): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        return paint
    }

    override fun calculateFrame() {

        if (hRect == null || vRect == null) {


            val hLeft = (x - starSize / 2).toFloat()
            val hRight = (x + starSize / 2).toFloat()
            val hTop = (y - starSize / 6).toFloat()
            val hBottom = (y + starSize / 6).toFloat()

            hRect = RectF(hLeft, hTop, hRight, hBottom)


            val vLeft = (x - starSize / 6).toFloat()
            val vRight = (x + starSize / 6).toFloat()
            val vTop = (y - starSize / 2).toFloat()
            val vBottom = (y + starSize / 2).toFloat()

            vRect = RectF(vLeft, vTop, vRight, vBottom)
        }

        super.calculateFrame()


    }

    override fun onDraw(canvas: Canvas?): Canvas? {
        paint?.also {
            it.alpha = alphaInt

            if (hRect != null && vRect != null) {
                canvas?.drawRoundRect(hRect, 6f, 6f, it)
                canvas?.drawRoundRect(vRect, 6f, 6f, it)

            }
        }
        return canvas
    }
}