# AnimatedStarsView for Android

Kotlin Android view that draws animated stars on a canvas

### Preview
[![IMAGE ALT TEXT HERE](https://github.com/sofakingforever/animated-stars-android/blob/master/screenshot.png?raw=true)](http://www.youtube.com/watch?v=mpwT7fZcn10)


## Quick Start Guide

### Add To Gradle
Add library to your gradle module

```gradle
repositories {
    maven { url "http://dl.bintray.com/sofakingforever/libraries" }
}

dependencies {
    compile 'com.sofakingforever.libraries:stars:1.0.2@aar'
}
```

### Layout Implementation Example
Insert View to the layout

```xml

    <com.sofakingforever.stars.AnimatedStarsView
        android:id="@+id/stars"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerHorizontal="true"
        app:starsView_bigStarThreshold="20dp"
        app:starsView_maxStarSize="4dp"
        app:starsView_minStarSize="1dp"
        app:starsView_starColors="@array/star_colors"
        app:starsView_starCount="30" />
```

### Kotlin Implementation Example
Call `onStart` and `onStop`
```kotlin

    override fun onStart() {
        super.onStart()
        stars.onStart()
    }

    override fun onStop() {
        stars.onStop()
        super.onStop()
    }
```


### Originally developed for [Wakey - Beautiful Alarm Clock](http://bit.ly/2Pmlwhg)
