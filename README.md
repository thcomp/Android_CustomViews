# Android_CustomViews
It provides following custom view.
* FormattableTextView: It is the TextView includes format string
```
<jp.co.thcomp.view.FormattableTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:Evl_FormatText="%1$s can have %2$.1f parameters"
    app:Evl_TextParam001="FormattableTextView"
    app:Evl_FloatParam002="20.0"/>
=>
FormattableTextView can have 20.0 parameters
```
* LockPatternView: function of 3x3 pattern lock as Android preset key guard screen.
* MonthlyCalendarView: view for monthly calendar
* WeeklyCalendarView: view for weekly calendar
* DailyCalendarView: view for daily calendar
* HorizontalSwipeView: view container for swipe mene like iOS
