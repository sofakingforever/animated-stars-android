[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-animated--stars--android-green.svg?style=flat )]( https://android-arsenal.com/details/1/7202 )

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
    implementation 'com.sofakingforever.libraries:stars:1.1.2@aar'
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
        app:starsView_meteoritesColors="@array/meteorites_colors"
        app:starsView_meteoritesEnabled="true"
        app:starsView_meteoritesInterval="2000"
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


    <integer-array name="meteorites_colors">

        <item>@color/star_color_2</item>
        <item>@color/star_color_4</item>
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

### Originally developed for [Wakey - Beautiful Alarm Clock](https://wakey.app/?ref=github)

Wakey is a simple & beautiful animated alarm clock, featuring a spectacular design and an immersive experience - guaranteed to wake you up with a smile everyday!

With our smiling sunrise, and grumpy lunar animations, this is the most unique alarm clock in our solar system.

![Wakey Alarm Clock](https://cdn-images-1.medium.com/max/2000/1*DhcklS1xNZwHogX0wDQEyw.png)

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

