package jp.co.thcomp.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class MonthlyCalendarView extends FrameLayout {
	private static final int WeeklyCalendarViewResIds[] = {
		R.id.Evl_Week01,
		R.id.Evl_Week02,
		R.id.Evl_Week03,
		R.id.Evl_Week04,
		R.id.Evl_Week05,
	};
	private WeeklyCalendarView[] mWeeklyCalendarViews = new WeeklyCalendarView[WeeklyCalendarViewResIds.length];
	private TextView mTvMonthText;
	private DailyCalendarView[] mLlCalendarHeader = new DailyCalendarView[WeeklyCalendarView.DailyCalendarViewResIds.length];

	public MonthlyCalendarView(Context context) {
		super(context);
		initLayout();
		initColor();
	}

	public MonthlyCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
		initColor();
	}

	public MonthlyCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout();
		initColor();
	}

	protected void initLayout(){
		Context context = getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.evl_calendar_layout, this, true);
		for(int i=0, size=WeeklyCalendarViewResIds.length; i<size; i++){
			mWeeklyCalendarViews[i] = (WeeklyCalendarView)findViewById(WeeklyCalendarViewResIds[i]);
			mWeeklyCalendarViews[i].findViewById(R.id.Evl_TopBorder).setVisibility(View.GONE);
		}

		Resources resources = context.getResources();
		String[] dayOfWeekArray = resources.getStringArray(R.array.Evl_DayOfWeekStringArray);
		LinearLayout calendarHeader = (LinearLayout)findViewById(R.id.Evl_MonthlyCalendarHeader);
		TextView tvDayOfWeek = null;
		for(int i=0, size=WeeklyCalendarView.DailyCalendarViewResIds.length; i<size; i++){
			mLlCalendarHeader[i] = (DailyCalendarView) calendarHeader.findViewById(WeeklyCalendarView.DailyCalendarViewResIds[i]);
			mLlCalendarHeader[i].findViewById(R.id.Evl_MonthOfYear).setVisibility(View.GONE);
			mLlCalendarHeader[i].findViewById(R.id.Evl_DateNumber).setVisibility(View.GONE);
			(tvDayOfWeek = (TextView)mLlCalendarHeader[i].findViewById(R.id.Evl_DayOfWeek)).setVisibility(View.VISIBLE);
			tvDayOfWeek.setText(dayOfWeekArray[i]);
			mLlCalendarHeader[i].findViewById(R.id.Evl_DateItemsScr).setVisibility(View.VISIBLE);
		}
		mTvMonthText = (TextView)findViewById(R.id.Evl_MonthText);
		setVisibilityForParts(new Integer[]{View.GONE, View.VISIBLE, View.GONE});
	}

	private void initColor(){
		Context context = getContext();
		Resources resources = context.getResources();

		int textColorForSunday = resources.getColor(R.color.Evl_TextColorForSunday);
		int textColorForWeekday = resources.getColor(R.color.Evl_TextColorForWeekday);
		int textColorForSaturday = resources.getColor(R.color.Evl_TextColorForSaturday);
		int bgColorForSunday = resources.getColor(R.color.Evl_DailyCalenderBgColor);
		int bgColorForWeekday = resources.getColor(R.color.Evl_DailyCalenderBgColor);
		int bgColorForSaturday = resources.getColor(R.color.Evl_DailyCalenderBgColor);

		mLlCalendarHeader[Calendar.SUNDAY - 1].setTextColorForSunday(textColorForSunday);
		mLlCalendarHeader[Calendar.SUNDAY - 1].setBackgroundColorForSunday(bgColorForSunday);
		mLlCalendarHeader[Calendar.MONDAY - 1].setTextColorForSunday(textColorForWeekday);
		mLlCalendarHeader[Calendar.MONDAY - 1].setBackgroundColorForSunday(bgColorForWeekday);
		mLlCalendarHeader[Calendar.TUESDAY - 1].setTextColorForSunday(textColorForWeekday);
		mLlCalendarHeader[Calendar.TUESDAY - 1].setBackgroundColorForSunday(bgColorForWeekday);
		mLlCalendarHeader[Calendar.WEDNESDAY - 1].setTextColorForSunday(textColorForWeekday);
		mLlCalendarHeader[Calendar.WEDNESDAY - 1].setBackgroundColorForSunday(bgColorForWeekday);
		mLlCalendarHeader[Calendar.THURSDAY - 1].setTextColorForSunday(textColorForWeekday);
		mLlCalendarHeader[Calendar.THURSDAY - 1].setBackgroundColorForSunday(bgColorForWeekday);
		mLlCalendarHeader[Calendar.FRIDAY - 1].setTextColorForSunday(textColorForWeekday);
		mLlCalendarHeader[Calendar.FRIDAY - 1].setBackgroundColorForSunday(bgColorForWeekday);
		mLlCalendarHeader[Calendar.SATURDAY - 1].setTextColorForSunday(textColorForSaturday);
		mLlCalendarHeader[Calendar.SATURDAY - 1].setBackgroundColorForSunday(bgColorForSaturday);
	}

	public void setMonth(Calendar calendarOfDate){
		Calendar tempCalendar = (Calendar) calendarOfDate.clone();
		tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayOfYear = tempCalendar.get(Calendar.DAY_OF_YEAR);
		int dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
		tempCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear - (dayOfWeek - 1));
		setFirstDate(tempCalendar);
	}
	
	public void setFirstDate(Calendar calendarOfDate){
		int dayOfYear = calendarOfDate.get(Calendar.DAY_OF_YEAR);
		Calendar tempCalendar = (Calendar) calendarOfDate.clone();
		Context context = getContext();
		Resources resources = context.getResources();
		String[] monthOfYearArray = resources.getStringArray(R.array.Evl_MonthOfYearStringArray);

		tempCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear + mWeeklyCalendarViews.length);
		mTvMonthText.setText(monthOfYearArray[tempCalendar.get(Calendar.MONTH)]);

		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			calendarOfDate.set(Calendar.DAY_OF_YEAR, dayOfYear + (i * WeeklyCalendarView.DayOfWeek));
			mWeeklyCalendarViews[i].setFirstDate(calendarOfDate);
		}
	}
	
	public void setTextColors(Integer textColors[]){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setTextColors(textColors);
		}
	}

	public void setBackgroundColors(Integer bgColors[]){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundColors(bgColors);
		}
	}

	public void setBackgroundDrawables(Drawable bgDrawables[]){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundDrawables(bgDrawables);
		}
	}

	public void setTextColorForPublicHoliday(int textColor){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setTextColorForPublicHoliday(textColor);
		}
	}

	public void setTextColorForSunday(int textColor){
		mLlCalendarHeader[Calendar.SUNDAY - 1].setTextColorForSunday(textColor);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setTextColorForSunday(textColor);
		}
	}

	public void setTextColorForSaturday(int textColor){
		mLlCalendarHeader[Calendar.SATURDAY - 1].setTextColorForSaturday(textColor);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setTextColorForSaturday(textColor);
		}
	}

	public void setTextColorForWeekday(int textColor){
		mLlCalendarHeader[Calendar.MONDAY - 1].setTextColorForWeekday(textColor);
		mLlCalendarHeader[Calendar.TUESDAY - 1].setTextColorForWeekday(textColor);
		mLlCalendarHeader[Calendar.WEDNESDAY - 1].setTextColorForWeekday(textColor);
		mLlCalendarHeader[Calendar.THURSDAY - 1].setTextColorForWeekday(textColor);
		mLlCalendarHeader[Calendar.FRIDAY - 1].setTextColorForWeekday(textColor);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setTextColorForWeekday(textColor);
		}
	}
	
	public void setBackgroundColorForPublicHoliday(int bgColor){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundColorForPublicHoliday(bgColor);
		}
	}

	public void setBackgroundColorForSunday(int bgColor){
		mLlCalendarHeader[Calendar.SUNDAY - 1].setBackgroundColorForSunday(bgColor);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundColorForSunday(bgColor);
		}
	}

	public void setBackgroundColorForSaturday(int bgColor){
		mLlCalendarHeader[Calendar.SATURDAY - 1].setBackgroundColorForSaturday(bgColor);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundColorForSaturday(bgColor);
		}
	}

	public void setBackgroundColorForWeekday(int bgColor){
		mLlCalendarHeader[Calendar.MONDAY - 1].setTextColorForWeekday(bgColor);
		mLlCalendarHeader[Calendar.TUESDAY - 1].setTextColorForWeekday(bgColor);
		mLlCalendarHeader[Calendar.WEDNESDAY - 1].setTextColorForWeekday(bgColor);
		mLlCalendarHeader[Calendar.THURSDAY - 1].setTextColorForWeekday(bgColor);
		mLlCalendarHeader[Calendar.FRIDAY - 1].setTextColorForWeekday(bgColor);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundColorForWeekday(bgColor);
		}
	}
	
	public void setBackgroundDrawableForPublicHoliday(Drawable bgDrawable){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundDrawableForPublicHoliday(bgDrawable);
		}
	}

	public void setBackgroundDrawableForSunday(Drawable bgDrawable){
		mLlCalendarHeader[Calendar.SUNDAY - 1].setBackgroundDrawableForSunday(bgDrawable);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundDrawableForSunday(bgDrawable);
		}
	}

	public void setBackgroundDrawableForSaturday(Drawable bgDrawable){
		mLlCalendarHeader[Calendar.SATURDAY - 1].setBackgroundDrawableForSaturday(bgDrawable);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundDrawableForSaturday(bgDrawable);
		}
	}

	public void setBackgroundDrawableForWeekday(Drawable bgDrawable){
		mLlCalendarHeader[Calendar.MONDAY - 1].setBackgroundDrawableForWeekday(bgDrawable);
		mLlCalendarHeader[Calendar.TUESDAY - 1].setBackgroundDrawableForWeekday(bgDrawable);
		mLlCalendarHeader[Calendar.WEDNESDAY - 1].setBackgroundDrawableForWeekday(bgDrawable);
		mLlCalendarHeader[Calendar.THURSDAY - 1].setBackgroundDrawableForWeekday(bgDrawable);
		mLlCalendarHeader[Calendar.FRIDAY - 1].setBackgroundDrawableForWeekday(bgDrawable);
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setBackgroundDrawableForWeekday(bgDrawable);
		}
	}

	public void setVisibilityForParts(Integer visibilities[]){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setVisibilityForParts(visibilities);
		}
	}

	public WeeklyCalendarView getWeeklyCalendarView(int index){
		return mWeeklyCalendarViews[index];
	}

	public WeeklyCalendarView[] getAllWeeklyCalendarViews(){
		return mWeeklyCalendarViews;
	}

	public DailyCalendarView getDailyCalendarView(int index){
		DailyCalendarView ret = null;
		int indexOfWeek = index / WeeklyCalendarView.DayOfWeek;
		int indexOfDayInWeek = index % WeeklyCalendarView.DayOfWeek;

		try{
			ret = mWeeklyCalendarViews[indexOfWeek].getDailyCalendarView(indexOfDayInWeek);
		}catch(IndexOutOfBoundsException e){
		}

		return ret;
	}

	public DailyCalendarView getDailyCalendarView(Calendar targetDate){
		Calendar firstDate = mWeeklyCalendarViews[0].getDailyCalendarView(0).mDateCalendar;
		int diffDate = targetDate.get(Calendar.DAY_OF_YEAR) - firstDate.get(Calendar.DAY_OF_YEAR);
		return getDailyCalendarView(diffDate);
	}

	public void setOnDatePickListener(Calendar targetDate, OnDatePickListener listener, Object userData){
		DailyCalendarView targetCalendarView = getDailyCalendarView(targetDate);
		if(targetCalendarView != null){
			targetCalendarView.setOnDatePickListener(listener, userData);
		}
	}

	public void setOnDatePickListener(OnDatePickListener listener, Object userData){
		for(int i=0, size=mWeeklyCalendarViews.length; i<size; i++){
			mWeeklyCalendarViews[i].setOnDatePickListener(listener, userData);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		int maxWidthOfChild = Integer.MIN_VALUE;
		int maxHeightOfChild = Integer.MIN_VALUE;
		for(WeeklyCalendarView view : mWeeklyCalendarViews){
			int tempWidth = view.getWidth();
			int tempHeight = view.getHeight();

			if(tempWidth > maxWidthOfChild){
				maxWidthOfChild = tempWidth;
			}
			if(tempHeight > maxHeightOfChild){
				maxHeightOfChild = tempHeight;
			}
		}

		if(maxWidthOfChild > 0 && maxHeightOfChild > 0){
			for(WeeklyCalendarView view : mWeeklyCalendarViews){
				//view.measure(maxWidthOfChild, maxHeightOfChild);
				view.layout(view.getLeft(), view.getTop(), view.getLeft() + maxWidthOfChild, view.getTop() + maxHeightOfChild);
				view.invalidate(view.getLeft(), view.getTop(), view.getLeft() + maxWidthOfChild, view.getTop() + maxHeightOfChild);
			}
		}
	}
}
