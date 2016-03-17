package jp.co.thcomp.view;

import java.util.Calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class WeeklyCalendarView extends FrameLayout {
	static final int DailyCalendarViewResIds[] = {
		R.id.Evl_Date01,
		R.id.Evl_Date02,
		R.id.Evl_Date03,
		R.id.Evl_Date04,
		R.id.Evl_Date05,
		R.id.Evl_Date06,
		R.id.Evl_Date07,
	};
	static final int DayOfWeek = DailyCalendarViewResIds.length;
	private DailyCalendarView[] mDailyCalendarViews = new DailyCalendarView[DailyCalendarViewResIds.length];

	public WeeklyCalendarView(Context context) {
		super(context);
		initLayout();
	}

	public WeeklyCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
	}

	public WeeklyCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout();
	}

	protected void initLayout(){
		Context context = getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.evl_calendar_week, this, true);
		for(int i=0, size=DailyCalendarViewResIds.length; i<size; i++){
			mDailyCalendarViews[i] = (DailyCalendarView)findViewById(DailyCalendarViewResIds[i]);
		}
	}

	public void setFirstDate(Calendar calendarOfDate){
		Calendar tempCalendar = (Calendar) calendarOfDate.clone();
		int dayOfYear = tempCalendar.get(Calendar.DAY_OF_YEAR);
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			tempCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear + i);
			mDailyCalendarViews[i].setDate(tempCalendar);
		}
	}
	
	public void setTextColors(Integer textColors[]){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setTextColors(textColors);
		}
	}

	public void setBackgroundColors(Integer bgColors[]){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundColors(bgColors);
		}
	}

	public void setBackgroundDrawables(Drawable bgDrawables[]){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundDrawables(bgDrawables);
		}
	}

	public void setTextColorForPublicHoliday(int textColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setTextColorForPublicHoliday(textColor);
		}
	}

	public void setTextColorForSunday(int textColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setTextColorForSunday(textColor);
		}
	}

	public void setTextColorForSaturday(int textColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setTextColorForSaturday(textColor);
		}
	}

	public void setTextColorForWeekday(int textColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setTextColorForWeekday(textColor);
		}
	}
	
	public void setBackgroundColorForPublicHoliday(int bgColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundColorForPublicHoliday(bgColor);
		}
	}

	public void setBackgroundColorForSunday(int bgColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundColorForSunday(bgColor);
		}
	}

	public void setBackgroundColorForSaturday(int bgColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundColorForSaturday(bgColor);
		}
	}

	public void setBackgroundColorForWeekday(int bgColor){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundColorForWeekday(bgColor);
		}
	}
	
	public void setBackgroundDrawableForPublicHoliday(Drawable bgDrawable){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundDrawableForPublicHoliday(bgDrawable);
		}
	}

	public void setBackgroundDrawableForSunday(Drawable bgDrawable){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundDrawableForSunday(bgDrawable);
		}
	}

	public void setBackgroundDrawableForSaturday(Drawable bgDrawable){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundDrawableForSaturday(bgDrawable);
		}
	}

	public void setBackgroundDrawableForWeekday(Drawable bgDrawable){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setBackgroundDrawableForWeekday(bgDrawable);
		}
	}

	public void setVisibilityForParts(Integer visibilities[]){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setVisibilityForParts(visibilities);
		}
	}

	public DailyCalendarView getDailyCalendarView(int index){
		DailyCalendarView ret = null;

		try{
			ret = mDailyCalendarViews[index];
		}catch(IndexOutOfBoundsException e){
		}

		return ret;
	}

	public DailyCalendarView getDailyCalendarView(Calendar targetDate){
		DailyCalendarView ret = null;
		Calendar firstDateCalendar = mDailyCalendarViews[0].mDateCalendar;
		
		if(firstDateCalendar != null){
			int diffDate = targetDate.get(Calendar.DAY_OF_YEAR) - mDailyCalendarViews[0].mDateCalendar.get(Calendar.DAY_OF_YEAR);
			try{
				ret = mDailyCalendarViews[diffDate];
			}catch(IndexOutOfBoundsException e){
			}
		}

		return ret;
	}

	public DailyCalendarView[] getAllDailyCalendarViews(){
		return mDailyCalendarViews;
	}

	public void setOnDatePickListener(Calendar targetDate, OnDatePickListener listener, Object userData){
		DailyCalendarView targetCalendarView = getDailyCalendarView(targetDate);
		if(targetCalendarView != null){
			targetCalendarView.setOnDatePickListener(listener, userData);
		}
	}

	public void setOnDatePickListener(OnDatePickListener listener, Object userData){
		for(int i=0, size=mDailyCalendarViews.length; i<size; i++){
			mDailyCalendarViews[i].setOnDatePickListener(listener, userData);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		int maxWidthOfChild = Integer.MIN_VALUE;
		int maxHeightOfChild = Integer.MIN_VALUE;
		for(DailyCalendarView view : mDailyCalendarViews){
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
			for(DailyCalendarView view : mDailyCalendarViews){
				//view.measure(maxWidthOfChild, maxHeightOfChild);
				view.layout(view.getLeft(), view.getTop(), view.getLeft() + maxWidthOfChild, view.getTop() + maxHeightOfChild);
				view.invalidate(view.getLeft(), view.getTop(), view.getLeft() + maxWidthOfChild, view.getTop() + maxHeightOfChild);
			}
		}
	}
}
