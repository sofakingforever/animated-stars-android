package com.sofakingforever.stars

class StarConstraints(val minStarSize: Int, val maxStarSize: Int, val bigStarThreshold: Int) {

    fun getRandomStarSize(): Double = (minStarSize + Math.random() * (maxStarSize - minStarSize))

}