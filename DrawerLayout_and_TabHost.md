###1.21 android_note
 when we use the TabHost,we must pay attention the ditail following I will show:
 Default Android studio give us TabHost has a little problem.the TabHost's id must be tabhost instead of tabHost.(PS:I don't know whether my Android Stdio's version is low or not.And I'm troubled about the case for a long time)
```
<TabHost 
android:layout_width="match_parent"
android:layout_height="wrap_contnet"
android:id="@android:id/tabhost">
<LinearLaout
android:layout_width="match_parent"
android:layout_height="wrap_content">
<FrameLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:id="@android:id/tabcontent">

</FrameLayout>
<tab
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:id="@android:id/tabs"
</LinearLayout>
</TabHost>
```
 I think I must rewrite the Layout when I use the TabHost so that I can add My activity into the TabContent, but I worry about troubles of my own imaging.when you getTabHost() the function setContent(Intent intent or int R.id.layout) will help you add Intent into tabhost,also you can add layout as you like.


###DrawerLayout study
 Today I also try to use Drawerlayout into my application, but I just feel boom.Oh my laddy gaga.
 Oh my god!I lost my note I writted this afternoon.Once more.
 I thought it's my android studio' fault.I must add the support package into my project because my android studio cann't do it by it's IDE.Following I will say how to add the packeage into it.
 Click your project with your mouse right key,and choose `open Moudle Setting` then you open dependencies,congratulation you can add the support package into your project.
 
 New finding I want to open the drawer whenever I move my finger from left to right.First I was told I could use `onTouchEvent(MotionEvent mv)` to slove my problem,but less than satisfactory it didn't work when I overrided the function.
 However heacen never seals off all the exits,I find more effective function `dispatchTouchEvent(MotionEvent mv)`.It can firstly handle all event just it's relative to the MotionEvent.So I can write my logical function into it.
 Thanks the writter of [xingye zhang](http://blog.csdn.net/xyz_lmn/article/details/12517911).You can click the Uri to get the information I said(Ps: the image I think it's very helpful).The code I will show you tomorrow.hahahahah.....