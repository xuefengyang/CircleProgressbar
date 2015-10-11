# CircleProgressbar
这是一个圆形的进度条,继承自View类。可以自定义颜色和大小。
#demo  
这是预览的效果  

!["preview"](http://img.blog.csdn.net/20150118005853959?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveHVlZmVuZ195YW5n/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center) 

#用法 
```xml
<com.yxf.circleprogressbar.CircleProgressBar
        android:id="@+id/progressbar1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        circleprogressbar:progress_complete_color="@color/color2"
        circleprogressbar:progress_fill_color="@color/color5"
         circleprogressbar:progress_text_color="@color/color6"
        circleprogressbar:progress_maxprogress="100"
        circleprogressbar:progress_progress="10"
        circleprogressbar:progress_text_size="50sp"
        circleprogressbar:progress_text_visibility="visible"
        circleprogressbar:progress_uncomplete_color="@color/color1" >
    </com.yxf.circleprogressbar.CircleProgressBar>
```
可以自定义的属性包括  
`progress_complete_color` `progress_uncomplete_color` `progress_fill_color` `progress_text_color`  
`progress_fill_radius` `progress_text_size` `progress_progress` `progress_maxprogress` `progress_text_visibility` 
