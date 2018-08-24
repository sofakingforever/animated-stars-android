# AnimatedStarsView for Android

Kotlin Android view that draws animated stars on a canvas


### Preview (Click for video)
[![Stars](https://github.com/sofakingforever/animated-stars-android/blob/master/screenshot.png?raw=true)](http://www.youtube.com/watch?v=mpwT7fZcn10)


## Quick Start Guide

### Step 1
Add library to your gradle module

```gradle
repositories {
    maven { url "http://dl.bintray.com/sofakingforever/libraries" }
}

dependencies {
    compile 'com.sofakingforever.libraries:stars:1.0.2@aar'
}
```

### Step 2
Insert View via XML (or code)

```xml

    <com.sofakingforever.stars.AnimatedStarsView
        android:id="@+id/stars"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:starsView_bigStarThreshold="10dp"
        app:starsView_maxStarSize="16dp"
        app:starsView_minStarSize="1dp"
        app:starsView_starColors="@array/star_colors"
        app:starsView_starCount="50" />
```

### Step 3
Add the color array
```xml
    <integer-array name="star_colors_small">
        <!-- This is how you can configure the ratio of star colors-->
        <item>@color/star_color_1</item>
        <item>@color/star_color_1</item>
        <item>@color/star_color_1</item>
        <item>@color/star_color_1</item>
        <item>@color/star_color_2</item>
        <item>@color/star_color_3</item>
    </integer-array>
```

### Step 4
Kotlin Implementation Example - Call `onStart` and `onStop`
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
### DONE!

### Originally developed for [Wakey - Beautiful Alarm Clock](https://play.google.com/store/apps/details?id=com.sofaking.moonworshipper&hl=en_US)

Wakey is a simple & beautiful animated alarm clock, featuring a spectacular design and an immersive experience - guaranteed to wake you up with a smile everyday!

With our smiling sunrise, and grumpy lunar animations, this is the most unique alarm clock in our solar system.

![Wakey Alarm Clock](https://cdn-images-1.medium.com/max/2000/1*DhcklS1xNZwHogX0wDQEyw.png)
