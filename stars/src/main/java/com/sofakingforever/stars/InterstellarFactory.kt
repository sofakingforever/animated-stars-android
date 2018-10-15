package com.sofakingforever.stars

import com.sofakingforever.stars.entities.BaseStar
import com.sofakingforever.stars.entities.RoundyStar
import com.sofakingforever.stars.entities.ShinyStar
import com.sofakingforever.stars.entities.TinyStar

object InterstellarFactory {

    fun create(starConstraints: StarConstraints, x: Int, y: Int, color: Int, listener: BaseStar.StarCompleteListener): Star {
        val starSize = starConstraints.getRandomStarSize()

        return if (starSize >= starConstraints.bigStarThreshold) {

            // big star ones randomly be Shiny or Round
            if (Math.random() < 0.7) {
                ShinyStar(starConstraints, x, y, color, listener)
            } else {
                RoundyStar(starConstraints, x, y, color, listener)
            }
        } else {
            // others will ne tiny
            TinyStar(starConstraints, x, y, color, listener)
        }
    }

}