package com.sofakingforever.stars.entities

import android.graphics.Canvas

interface Star {

    var x: Int
    var y: Int
    val color: Int

    var alphaDouble: Double

    var multiplierFactor: Int
    var incrementFactor: Double
    val starSize : Double



    fun onDraw(canvas: Canvas?): Canvas?

    fun calculateFrame()

}