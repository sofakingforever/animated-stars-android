package com.sofakingforever.stars.entities

class StarConstraints(private val minStarSize: Int, private val maxStarSize: Int, val bigStarThreshold: Int) {

    fun getRandomStarSize(): Double = (minStarSize + Math.random() * (maxStarSize - minStarSize))

}