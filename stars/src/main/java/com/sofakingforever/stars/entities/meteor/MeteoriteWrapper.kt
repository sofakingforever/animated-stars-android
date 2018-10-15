package com.sofakingforever.stars.entities.meteor

import android.graphics.Canvas
import android.graphics.Paint
import com.sofakingforever.stars.entities.BaseStar

internal class MeteoriteWrapper(var x: Int, var y: Int, starSize: Int, var color: Int, val listener: BaseStar.StarCompleteListener) {


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).also { it.color = color }

    private val meteor: Meteorite = Meteorite(x, y, starSize, color, paint, listener)
    private val trail: MeteoriteTrail = MeteoriteTrail(starSize, paint)

    fun calculateFrame(viewWidth: Int, viewHeight: Int) {
        trail.addPixel(meteor.x, meteor.y)
        trail.calculatePixels()
        meteor.calculateFrame(viewWidth, viewHeight)
    }

    fun onDraw(canvas: Canvas?): Canvas? {
        var newCanvas = canvas
        newCanvas = meteor.onDraw(newCanvas)
        newCanvas = trail.onDraw(newCanvas)

        return newCanvas
    }


}