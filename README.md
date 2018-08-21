# AnimatedStarsView for Android
https://www.youtube.com/watch?v=9tpJBTTqqSo

Description Coming Soon

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

### Kotlin Implementation Example
Initiate analytics and send events

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
