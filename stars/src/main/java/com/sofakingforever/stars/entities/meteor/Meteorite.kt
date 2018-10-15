package com.sofakingforever.stars.entities.meteor

import android.graphics.Canvas
import android.graphics.Paint
import com.sofakingforever.stars.entities.BaseStar

internal class Meteorite(var x: Int, var y: Int, private val starSize: Int, var color: Int, val paint: Paint, val listener: BaseStar.StarCompleteListener) {

    private var onDoneInvoked = false

//        internal val star: TinyStar = TinyStar(starConstraints, x, y, color, listener)


    fun calculateFrame(viewWidth: Int, viewHeight: Int) {
        // go left
        x -= (starSize / 2.0).toInt()

        // go down
        y += (starSize / 2.0).toInt()

        if (x < viewWidth * -1 && !onDoneInvoked) {
            listener.onStarAnimationComplete()
            onDoneInvoked = true
        }
    }

    fun onDraw(canvas: Canvas?): Canvas? {
        canvas?.drawCircle(x.toFloat(), y.toFloat(), starSize.toFloat() / 2f, paint)

        return canvas
    }

}