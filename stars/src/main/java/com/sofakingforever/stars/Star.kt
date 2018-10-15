package com.sofakingforever.stars

import android.graphics.Canvas

interface Star {

    val x: Int
    val y: Int
    val color: Int

    var alphaDouble: Double

    var multiplierFactor: Int
    var incrementFactor: Double
    val starSize : Double



    fun onDraw(canvas: Canvas?): Canvas?

    fun calculateFrame()

}