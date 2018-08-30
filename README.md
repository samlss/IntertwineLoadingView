# IntertwineLoadingView
A two intertwined balls loading view.

### [中文](https://github.com/samlss/IntertwineLoadingView/blob/master/README-ZH.md)

### [More](https://github.com/samlss/FunnyViews)

 <br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/IntertwineLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/IntertwineLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### The default  effect
![gif1](https://github.com/samlss/IntertwineLoadingView/blob/master/screenshots/screenshot1.gif)


### The effect of custom animation duration & color
![gif2](https://github.com/samlss/IntertwineLoadingView/blob/master/screenshots/screenshot2.gif)

### Use<br>
Add it in your root build.gradle at the end of repositories：
```java
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```java
dependencies {
    implementation 'com.github.samlss:IntertwineLoadingView:1.0'
}
```


in layout.xml：
```java
  <com.iigo.library.IntertwineLoadingView
            app:animDuration="1000"
            app:firstBallColor="@android:color/holo_red_dark"
            app:secondBallColor="@android:color/holo_orange_dark"
            android:layout_width="70dp"
            android:layout_height="70dp"/>

```

<br>

in java code：
```java
  intertwineLoadingView.setDuration(1000); //Set animation duration
  intertwineLoadingView.setFirstBallColor(Color.RED); //Set the color of the first ball
  intertwineLoadingView.setSecondBallColor(Color.YELLOW); //Set the color of the second ball

  intertwineLoadingView.start(); //start animation
  intertwineLoadingView.stop(); //stop animation
  
  intertwineLoadingView.pause(); //pause animation
  intertwineLoadingView.resume(); //resume animation
  intertwineLoadingView.release(); //Can 'released' when you don't need to use the loading view, for example in the activity's onDestroy()
```
<br>

Attributes description：

| attr      |              description              |
| --------- | :-----------------------------------: |
| firstBallColor | the color of the first ball |
| secondBallColor | the color of the second ball |
| animDuration | the animation duration |

<br>



## [LICENSE](https://github.com/samlss/IntertwineLoadingView/blob/master/LICENSE)