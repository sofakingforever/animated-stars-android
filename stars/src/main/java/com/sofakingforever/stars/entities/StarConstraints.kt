package com.sofakingforever.stars.entities

class StarConstraints(private val minStarSize: Int, private val maxStarSize: Int, val bigStarThreshold: Int) {

    fun getRandomStarSize(): Double {
        val maxRandom = maxStarSize - minStarSize
        return (minStarSize + Math.random() * maxRandom)
    }

}