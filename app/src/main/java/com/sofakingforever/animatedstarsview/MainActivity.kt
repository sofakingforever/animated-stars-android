package com.sofakingforever.animatedstarsview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()

        stars_white.onStart()
        stars.onStart()
    }

    override fun onStop() {

        stars_white.onStop()
        stars.onStop()

        super.onStop()
    }
}
