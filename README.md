# WeekChooseDailog
Android week choose dailog。一个安卓的周选择日历


一款周选择日历的实现。效果如GIF图：

![这里写图片描述](http://img.blog.csdn.net/20170412114256834?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvQW5kcm9pZE1za3k=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

喜欢可以点个star

###使用

已经加入jitpack库调用方法；

```

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}Copy
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.AndroidMsky:WeekChooseDailog:v1.0'
	}
```

调用方法也很简单：
show方法传入当前，年，月，日。
```
initPopupWindow();
leftPopupWindow.show(nowY, nowM, nowD, tvDate, new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    leftPopupWindow.diss();
                }
            });
```
选择日期后的回调数据为开始和结束的两组，年，月，日：

```
  int[] is;
  is = DateUtil.getWeekDay(startY, startM, startD);
            endY = is[3];
            endM = is[4];
            endD = is[5];
            startY = is[0];
            startM = is[1];
            startD = is[2];
```

了解更多：http://blog.csdn.net/AndroidMsky/article/details/70064017
