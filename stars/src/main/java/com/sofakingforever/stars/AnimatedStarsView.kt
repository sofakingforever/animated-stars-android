package com.sofakingforever.stars

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import java.util.*
import java.util.Collections.emptyList
import java.util.concurrent.Executors
import kotlin.concurrent.timerTask


/**
 * Kotlin Android view that draws animated stars on a canvas
 *
 * Used in Wakey - Beautiful Alarm Clock for Android: http://bit.ly/2uI8pgL
 * Check out the article on Medium: http://bit.ly/2NlFJBW
 * Or see what it looks like on YouTube: https://www.youtube.com/watch?v=v1-228CkoQc
 *
 * Crafted with ❤️ by sofakingforever
 */
class AnimatedStarsView
@kotlin.jvm.JvmOverloads
constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val fps: Long = 1000 / 60
    private val defaultStarCount: Int = 25
    private val threadExecutor = Executors.newSingleThreadExecutor()

    private var starCount: Int
    private var starColors: IntArray
    private var bigStarThreshold: Int
    private var minStarSize: Int
    private var maxStarSize: Int

    private var starsCalculatedFlag: Boolean = false
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var starConstraints: Star.StarConstraints


    private var stars: List<Star> = emptyList()

    private lateinit var timer: Timer
    private lateinit var task: TimerTask

    private val random: Random = Random()
    private var initiated: Boolean = false
    private var started: Boolean = false

    /**
     * init view's attributes
     */
    init {

        val array = context.obtainStyledAttributes(attrs, R.styleable.AnimatedStarsView, defStyleAttr, 0)

        starColors = intArrayOf()
        starCount = array.getInt(R.styleable.AnimatedStarsView_starsView_starCount, defaultStarCount)
        minStarSize = array.getDimensionPixelSize(R.styleable.AnimatedStarsView_starsView_minStarSize, 4)
        maxStarSize = array.getDimensionPixelSize(R.styleable.AnimatedStarsView_starsView_maxStarSize, 24)
        bigStarThreshold = array.getDimensionPixelSize(R.styleable.AnimatedStarsView_starsView_bigStarThreshold, Integer.MAX_VALUE)
        starConstraints = Star.StarConstraints(minStarSize, maxStarSize, bigStarThreshold)

        val starColorsArrayId = array.getResourceId(R.styleable.AnimatedStarsView_starsView_starColors, 0)

        if (starColorsArrayId != 0) {
            starColors = context.resources.getIntArray(starColorsArrayId)
        }

        array.recycle()

    }


    /**
     * Must call this in Activity's onStart
     */
    fun onStart() {

        if (!started) {
            timer = Timer()
            task = timerTask {
                invalidateStars()
            }

            timer.scheduleAtFixedRate(task, 0, fps)

            started = true
        }
    }


    /**
     * Must call this in Activity's onStop
     */
    fun onStop() {

        if (started) {
            task.cancel()
            timer.cancel()

            started = false

        }
    }

    /**
     * get view's size and init stars every time the size of the view has changed
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h

        if (viewWidth > 0 && viewHeight > 0) {
            if (!initiated || stars.isEmpty()) {
                initStars()
            }
        }
    }


    /**
     * Draw stars on view's canvas
     */
    override fun onDraw(canvas: Canvas?) {

        if (!initiated && !started) return

        // create a variable canvas object
        var newCanvas = canvas

        if (stars.isNotEmpty()) {
            // draw each star on the canvas
            stars.forEach { newCanvas = it.draw(newCanvas) }

            // reset flag
            starsCalculatedFlag = false
        }

        // finish drawing view
        super.onDraw(newCanvas)

    }


    /**
     * create x stars with a random point location and opacity
     */
    private fun initStars() {

        if (!started) return

        stars = List(starCount) {
            Star(
                    starConstraints, // constraints
                    Math.round(Math.random() * viewWidth).toInt(), // x
                    Math.round(Math.random() * viewHeight).toInt(), // y
                    Math.random(), // opacity
                    starColors[it % starColors.size], // color
                    viewWidth,
                    viewHeight
                    // function for generating new color
            ) { starColors[random.nextInt(starColors.size)] }
        }

        // so we know lateinit var was initiated
        initiated = true

    }

    /**
     * calculate and invalidate all stars for the next frame
     */
    private fun invalidateStars() {

        if (!initiated) return

        // new background thread
        threadExecutor.execute {

            // recalculate stars position and alpha on a background thread
            stars.forEach { it.calculateFrame(viewWidth, viewHeight) }
            starsCalculatedFlag = true

            // then post to ui thread
            postInvalidate()

        }

    }
}


