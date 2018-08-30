# IntertwineLoadingView
两个交织的小球的loading view

### [更多](https://github.com/samlss/FunnyViews)

 <br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/IntertwineLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/IntertwineLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### 默认效果
![gif1](https://github.com/samlss/IntertwineLoadingView/blob/master/screenshots/screenshot1.gif)

### 自定义动画时间和颜色效果
![gif2](https://github.com/samlss/IntertwineLoadingView/blob/master/screenshots/screenshot2.gif)

### 使用<br>
在根目录的build.gradle添加这一句代码：
```java
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```java
dependencies {
    implementation 'com.github.samlss:IntertwineLoadingView:1.0'
}
```


布局中：
```java
  <com.iigo.library.IntertwineLoadingView
            app:animDuration="1000"
            app:firstBallColor="@android:color/holo_red_dark"
            app:secondBallColor="@android:color/holo_orange_dark"
            android:layout_width="70dp"
            android:layout_height="70dp"/>

```

<br>

代码：
```java
  intertwineLoadingView.setDuration(1000); //设置动画时间
  intertwineLoadingView.setFirstBallColor(Color.RED); //设置第一个球的颜色
  intertwineLoadingView.setSecondBallColor(Color.YELLOW); //设置第二个球的颜色

  intertwineLoadingView.start(); //开始动画
  intertwineLoadingView.stop(); //停止动画
  
  intertwineLoadingView.pause(); //暂停动画
  intertwineLoadingView.resume(); //恢复动画
  intertwineLoadingView.release(); //在不需要使用该loading view时，可以调用该接口释放
```
<br>

属性说明：

| 属性      |              说明              |
| --------- | :-----------------------------------: |
| firstBallColor | 第一个球的颜色 |
| secondBallColor | 第二个球的颜色 |
| animDuration | 设置动画时间 |

<br>



## [LICENSE](https://github.com/samlss/IntertwineLoadingView/blob/master/LICENSE)