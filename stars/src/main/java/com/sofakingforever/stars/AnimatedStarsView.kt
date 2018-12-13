package com.sofakingforever.stars

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.sofakingforever.stars.entities.InterstellarFactory
import com.sofakingforever.stars.entities.Star
import com.sofakingforever.stars.entities.StarConstraints
import com.sofakingforever.stars.entities.stars.BaseStar
import com.sofakingforever.stars.entities.meteor.Meteorite
import java.util.*
import java.util.concurrent.Executors
import kotlin.concurrent.timerTask


/**
 * Kotlin Android view that draws animated stars on a canvas
 *
 * Used in Wakey - Cute & Gentle Alarm Clock for Android: http://bit.ly/2uI8pgL
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
    private var meteoritesColors: IntArray
    private var bigStarThreshold: Int
    private var minStarSize: Int
    private var maxStarSize: Int

    private var starsCalculatedFlag: Boolean = false
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var starConstraints: StarConstraints

    private var stars: MutableList<Star> = mutableListOf()
    private var meteorite: Meteorite? = null

    private lateinit var starsIterator: MutableIterator<Star>
    private lateinit var timer: Timer
    private lateinit var task: TimerTask

    private val random: Random = Random()
    private var initiated: Boolean = false
    private var started: Boolean = false

    private lateinit var meteoriteListener: Meteorite.MeteoriteCompleteListener
    private var meteoritesEnabled: Boolean
    private var meteoritesInterval: Int

    private var calculateRunnable: Runnable? = null

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
        starConstraints = StarConstraints(minStarSize, maxStarSize, bigStarThreshold)

        meteoritesColors = intArrayOf()
        meteoritesEnabled = array.getBoolean(R.styleable.AnimatedStarsView_starsView_meteoritesEnabled, false)
        meteoritesInterval = array.getInt(R.styleable.AnimatedStarsView_starsView_meteoritesInterval, 5000)

        val starColorsArrayId = array.getResourceId(R.styleable.AnimatedStarsView_starsView_starColors, 0)
        val meteoritesColorsArrayId = array.getResourceId(R.styleable.AnimatedStarsView_starsView_meteoritesColors, 0)

        if (starColorsArrayId != 0) {
            starColors = context.resources.getIntArray(starColorsArrayId)
        }

        if (meteoritesColorsArrayId != 0) {
            meteoritesColors = context.resources.getIntArray(meteoritesColorsArrayId)

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


        synchronized(stars) {
            if (stars.isNotEmpty()) {
                // onDraw each star on the canvas
                starsIterator = stars.iterator()
                starsIterator.forEach { newCanvas = it.onDraw(newCanvas) }

                newCanvas = meteorite?.onDraw(newCanvas)

                // reset flag
                starsCalculatedFlag = false
            }
        }
        // finish drawing view
        super.onDraw(newCanvas)

    }


    /**
     * create x stars with a random point location and alphaDouble
     */
    private fun initStars() {

        if (!started) return
//        val generateColor = { starColors[random.nextInt(starColors.size)] }

        meteoriteListener = object : Meteorite.MeteoriteCompleteListener {
            override fun onMeteoriteComplete() {
                if (meteoritesEnabled) {
                    postDelayed({

                        meteorite = Meteorite(
                                smallestWidth = Math.min(viewWidth, viewHeight),
                                x = viewWidth,
                                y = Math.round(Math.random() * (viewHeight * 2 / 3)).toInt(),
                                color = meteoritesColors[random.nextInt(meteoritesColors.size)],
                                starSize = starConstraints.getRandomStarSize().toInt(),
                                listener = meteoriteListener
                        )

                    }, meteoritesInterval.toLong())
                }
            }

        }

        synchronized(stars) {
            stars = MutableList(starCount) {

                val starCompleteListener = object : BaseStar.StarCompleteListener {
                    override fun onStarAnimationComplete() {
                        stars[it] = createStar(it, this)
                    }

                }
                createStar(it, starCompleteListener)

            }
        }

        meteoriteListener.onMeteoriteComplete()

        // so we know lateinit var was initiated
        initiated = true

    }

    private fun createStar(it: Int, starCompleteListener: BaseStar.StarCompleteListener): Star {
        return InterstellarFactory.create(starConstraints = starConstraints,
                x = Math.round(Math.random() * viewWidth).toInt(),
                y = Math.round(Math.random() * viewHeight).toInt(),
                color = starColors[it % starColors.size],
                listener = starCompleteListener)
    }


    /**
     * calculate and invalidate all stars for the next frame
     */
    private fun invalidateStars() {

        // not initiated
        if (!initiated) return

        // not finised drawing
        if (starsCalculatedFlag) return

        if (calculateRunnable == null) {
            calculateRunnable = Runnable {

                synchronized(stars) {
                    // recalculate stars position and alphaInt on a background thread
                    starsIterator = stars.iterator()
                    starsIterator.forEach { it.calculateFrame() }
                }
                meteorite?.calculateFrame(viewWidth, viewHeight)
                starsCalculatedFlag = true

                // then post to ui thread
                postInvalidate()
            }
        }


        // new background thread
        threadExecutor.execute(calculateRunnable)

    }
}


