package com.sofakingforever.stars.entities.stars

import android.graphics.Paint
import com.sofakingforever.stars.entities.Star
import com.sofakingforever.stars.entities.StarConstraints

abstract class BaseStar(
        starConstraints: StarConstraints,
        override var x: Int,
        override var y: Int,
        override var color: Int,
        private val listener: StarCompleteListener) : Star {

    val alphaInt: Int
        get() {
            return when {
                alphaDouble > 1.0 -> 255
                alphaDouble < 0.0 -> 0
                else -> (alphaDouble * 255.0).toInt()
            }
        }

    override var alphaDouble: Double = 0.0
    override var multiplierFactor: Int = 1

    override val starSize: Double = starConstraints.getRandomStarSize()


    internal var paint = initPaintColor()


    abstract fun initPaintColor(): Paint

    private var calculated: Boolean = false

    /**
     * calculate single frame for star (multiplierFactor, alphaDouble, and location if needed)
     */
    override fun calculateFrame() {

        calculated = true


        // calculate direction / multiplierFactor of alphaDouble

        if (alphaDouble > 1) {
            multiplierFactor *= -1
        }

        // calculate new alphaDouble for star
        alphaDouble += incrementFactor * multiplierFactor

        if (alphaDouble < 0.0) {

            // need to remove star and add new one
            listener.onStarAnimationComplete()

            return
        }


    }


    interface StarCompleteListener {
        fun onStarAnimationComplete()
    }


}