package jp.co.thcomp.view;

import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DailyCalendarView extends FrameLayout {
	static final String TAG_FOR_USERDATA = "tag_for_userdata";

	public static final int PartMonthOfYear = 0;
	public static final int PartDateNumber = 1;
	public static final int PartDayOfWeek = 2;
	public static final int PartMax = 3;
	public static final int DayTypeSunday = 0;
	public static final int DayTypeWeekday = 1;
	public static final int DayTypeSaturday = 2;
	public static final int DayTypePublicHoliday = 3;
	public static final int DayTypeMax = 4;
	private static final int DayStateWeekday = 01;
	private static final int DayStateSunday = 010;
	private static final int DayStateSaturday = 0100;
	private static final int DayStatePublicHoliday = 01000;
	Calendar mDateCalendar;
	private TextView mTxtMonthOfYear;
	private TextView mTxtDateNumber;
	private TextView mTxtDayOfWeek;
	private LinearLayout mLlDateItems;
	private int mDayStatus = DayStateWeekday;
	private int mTextColorForPublicHoliday;
	private int mTextColorForSunday;
	private int mTextColorForWeekday;
	private int mTextColorForSaturday;
	private int mBgColorForPublicHoliday;
	private int mBgColorForSunday;
	private int mBgColorForWeekday;
	private int mBgColorForSaturday;
	private Drawable mBgDrawableForPublicHoliday;
	private Drawable mBgDrawableForSunday;
	private Drawable mBgDrawableForWeekday;
	private Drawable mBgDrawableForSaturday;
	private OnClickListener mUserClickListener;
	private OnDatePickListener mDatePickLister;
	private OnClickListener mRootClickListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			if(mDateCalendar != null && mDatePickLister != null){
				mDatePickLister.onDatePick(mDateCalendar, DailyCalendarView.this.getTag(TAG_FOR_USERDATA.hashCode()));
			}
			if(mUserClickListener != null){
				mUserClickListener.onClick(arg0);
			}
		}
	};

	public DailyCalendarView(Context context) {
		super(context);
		initLayout();
		initColor();
	}

	public DailyCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
		initColor();
	}

	public DailyCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout();
		initColor();
	}

	private void initLayout(){
		Context context = getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.evl_calendar_date, this, true);
		mTxtMonthOfYear = (TextView)findViewById(R.id.Evl_MonthOfYear);
		mTxtMonthOfYear.setVisibility(View.VISIBLE);
		mTxtDateNumber = (TextView)findViewById(R.id.Evl_DateNumber);
		mTxtDayOfWeek = (TextView)findViewById(R.id.Evl_DayOfWeek);
		mTxtDayOfWeek.setVisibility(View.VISIBLE);
		mLlDateItems = (LinearLayout)findViewById(R.id.Evl_DateItems);
	}

	private void initColor(){
		Context context = getContext();
		Resources resources = context.getResources();

		mTextColorForPublicHoliday = resources.getColor(R.color.Evl_TextColorForPublicHoliday);
		mTextColorForSunday = resources.getColor(R.color.Evl_TextColorForSunday);
		mTextColorForWeekday = resources.getColor(R.color.Evl_TextColorForWeekday);
		mTextColorForSaturday = resources.getColor(R.color.Evl_TextColorForSaturday);
		mBgColorForPublicHoliday = resources.getColor(R.color.Evl_DailyCalenderBgColor);
		mBgColorForSunday = resources.getColor(R.color.Evl_DailyCalenderBgColor);
		mBgColorForWeekday = resources.getColor(R.color.Evl_DailyCalenderBgColor);
		mBgColorForSaturday = resources.getColor(R.color.Evl_DailyCalenderBgColor);
	}

	private void setTextColor(){
		int textColor = mTextColorForWeekday;
		if((mDayStatus & DayStatePublicHoliday) == DayStatePublicHoliday){
			textColor = mTextColorForPublicHoliday;
		}else{
			if((mDayStatus & DayStateSunday) == DayStateSunday){
				textColor = mTextColorForSunday;
			}else if((mDayStatus & DayStateSaturday) == DayStateSaturday){
				textColor = mTextColorForSaturday;
			}
		}

		mTxtMonthOfYear.setTextColor(textColor);
		mTxtDateNumber.setTextColor(textColor);
		mTxtDayOfWeek.setTextColor(textColor);
	}
	
	private void setBackgroundColor(){
		int bgColor = mBgColorForWeekday;
		if((mDayStatus & DayStatePublicHoliday) == DayStatePublicHoliday){
			bgColor = mBgColorForPublicHoliday;
		}else{
			if((mDayStatus & DayStateSunday) == DayStateSunday){
				bgColor = mBgColorForSunday;
			}else if((mDayStatus & DayStateSaturday) == DayStateSaturday){
				bgColor = mBgColorForSaturday;
			}
		}
		super.setBackgroundColor(bgColor);
	}
	
	private void setBackgroundDrawable(){
		Drawable bgDrawable = mBgDrawableForWeekday;
		if((mDayStatus & DayStatePublicHoliday) == DayStatePublicHoliday){
			bgDrawable = mBgDrawableForPublicHoliday;
		}else{
			if((mDayStatus & DayStateSunday) == DayStateSunday){
				bgDrawable = mBgDrawableForSunday;
			}else if((mDayStatus & DayStateSaturday) == DayStateSaturday){
				bgDrawable = mBgDrawableForSaturday;
			}
		}
		if(bgDrawable != null){
			super.setBackgroundDrawable(bgDrawable);
		}
	}
	
	public void setDate(Calendar calendarOfDate){
		mDateCalendar = (Calendar) calendarOfDate.clone();
		Context context = getContext();
		Resources resources = context.getResources();
		String[] monthOfYearArray = resources.getStringArray(R.array.Evl_MonthOfYearStringArray);
		String[] dayOfWeekArray = resources.getStringArray(R.array.Evl_DayOfWeekStringArray);
		int dayOfWeek = calendarOfDate.get(Calendar.DAY_OF_WEEK);

		mDayStatus &= DayStatePublicHoliday;	// left only DayStatePublicHoliday flag

		switch(dayOfWeek){
		case Calendar.SUNDAY:
			mDayStatus |= DayStateSunday;
			break;
		case Calendar.SATURDAY:
			mDayStatus |= DayStateSaturday;
			break;
		default:
			mDayStatus |= DayStateWeekday;
			break;
		}

		mTxtMonthOfYear.setText(monthOfYearArray[calendarOfDate.get(Calendar.MONTH)]);
		mTxtDateNumber.setText(String.valueOf(calendarOfDate.get(Calendar.DAY_OF_MONTH)));
		mTxtDayOfWeek.setText(dayOfWeekArray[dayOfWeek - 1]);

		setTextColor();
		setBackgroundColor();
		setBackgroundDrawable();
	}

	public void addDateItemView(View dateItemView){
		mLlDateItems.addView(dateItemView);
	}

	public void addDateItemView(View dateItemView, int index){
		mLlDateItems.addView(dateItemView, index);
	}

	public void addDateItemView(View dateItemView, ViewGroup.LayoutParams params){
		mLlDateItems.addView(dateItemView, params);
	}

	public void addDateItemView(View dateItemView, int width, int height){
		mLlDateItems.addView(dateItemView, width, height);
	}

	public void addDateItemView(View dateItemView, int index, ViewGroup.LayoutParams params){
		mLlDateItems.addView(dateItemView, index, params);
	}

	public void removeAllDateItemViews(){
		mLlDateItems.removeAllViews();
	}

	public void removeDateItemView(View dateItemView){
		mLlDateItems.removeView(dateItemView);
	}

	public void removeAllDateItemViewsInLayout(){
		mLlDateItems.removeAllViewsInLayout();
	}

	public void removeDateItemViewAt(int index){
		mLlDateItems.removeViewAt(index);
	}

	public void removeDateItemViewInLayout(View dateItemView){
		mLlDateItems.removeViewInLayout(dateItemView);
	}

	public void removeDateItemViews(int start, int count){
		mLlDateItems.removeViews(start, count);
	}

	public void removeDateItemViewsInLayout(int start, int count){
		mLlDateItems.removeViewsInLayout(start, count);
	}

	public int getDateItemCount(){
		return mLlDateItems.getChildCount();
	}
	
	public void setPublicHoliday(boolean publicHoliday){
		if(publicHoliday){
			mDayStatus |= DayStatePublicHoliday;
		}else{
			mDayStatus &= (~DayStatePublicHoliday);
		}
		setTextColor();
		setBackgroundColor();
		setBackgroundDrawable();
	}
	
	public void setTextColors(Integer textColors[]){
		for(int i=0, size=textColors.length; i<size; i++){
			switch(i){
			case DayTypeSunday:
				if(textColors[i] != null){
					mTextColorForPublicHoliday = textColors[i];
				}
				break;
			case DayTypeWeekday:
				if(textColors[i] != null){
					mTextColorForWeekday = textColors[i];
				}
				break;
			case DayTypeSaturday:
				if(textColors[i] != null){
					mTextColorForSaturday = textColors[i];
				}
				break;
			case DayTypePublicHoliday:
				if(textColors[i] != null){
					mTextColorForPublicHoliday = textColors[i];
				}
				break;
			}
		}
		
		setTextColor();
	}

	public void setBackgroundColors(Integer bgColors[]){
		for(int i=0, size=bgColors.length; i<size; i++){
			switch(i){
			case DayTypeSunday:
				if(bgColors[i] != null){
					mBgColorForPublicHoliday = bgColors[i];
				}
				break;
			case DayTypeWeekday:
				if(bgColors[i] != null){
					mBgColorForWeekday = bgColors[i];
				}
				break;
			case DayTypeSaturday:
				if(bgColors[i] != null){
					mBgColorForSaturday = bgColors[i];
				}
				break;
			case DayTypePublicHoliday:
				if(bgColors[i] != null){
					mBgColorForPublicHoliday = bgColors[i];
				}
				break;
			}
		}
		
		setBackgroundColor();
	}

	public void setBackgroundDrawables(Drawable bgDrawables[]){
		for(int i=0, size=bgDrawables.length; i<size; i++){
			switch(i){
			case DayTypeSunday:
				if(bgDrawables[i] != null){
					mBgDrawableForPublicHoliday = bgDrawables[i];
				}
				break;
			case DayTypeWeekday:
				if(bgDrawables[i] != null){
					mBgDrawableForWeekday = bgDrawables[i];
				}
				break;
			case DayTypeSaturday:
				if(bgDrawables[i] != null){
					mBgDrawableForSaturday = bgDrawables[i];
				}
				break;
			case DayTypePublicHoliday:
				if(bgDrawables[i] != null){
					mBgDrawableForPublicHoliday = bgDrawables[i];
				}
				break;
			}
		}
		
		setBackgroundDrawable();
	}

	public void setTextColorForPublicHoliday(int textColor){
		mTextColorForPublicHoliday = textColor;

		if((mDayStatus & DayStatePublicHoliday) == DayStatePublicHoliday){
			setTextColor();
		}
	}

	public void setTextColorForSunday(int textColor){
		mTextColorForSunday = textColor;

		if((mDayStatus & DayStateSunday) == DayStateSunday){
			setTextColor();
		}
	}

	public void setTextColorForSaturday(int textColor){
		mTextColorForSaturday = textColor;

		if((mDayStatus & DayStateSaturday) == DayStateSaturday){
			setTextColor();
		}
	}

	public void setTextColorForWeekday(int textColor){
		mTextColorForWeekday = textColor;

		if((mDayStatus & DayStateWeekday) == DayStateWeekday && (mDayStatus & DayStatePublicHoliday) != DayStatePublicHoliday){
			setTextColor();
		}
	}
	
	public void setBackgroundColorForPublicHoliday(int bgColor){
		mBgColorForPublicHoliday = bgColor;

		if((mDayStatus & DayStatePublicHoliday) == DayStatePublicHoliday){
			setBackgroundColor();
		}
	}

	public void setBackgroundColorForSunday(int bgColor){
		mBgColorForSunday = bgColor;

		if((mDayStatus & DayStateSunday) == DayStateSunday){
			setBackgroundColor();
		}
	}

	public void setBackgroundColorForSaturday(int bgColor){
		mBgColorForSaturday = bgColor;

		if((mDayStatus & DayStateSaturday) == DayStateSaturday){
			setBackgroundColor();
		}
	}

	public void setBackgroundColorForWeekday(int bgColor){
		mBgColorForWeekday = bgColor;

		if((mDayStatus & DayStateWeekday) == DayStateWeekday && (mDayStatus & DayStatePublicHoliday) != DayStatePublicHoliday){
			setBackgroundColor();
		}
	}
	
	public void setBackgroundDrawableForPublicHoliday(Drawable bgDrawable){
		mBgDrawableForPublicHoliday = bgDrawable;

		if((mDayStatus & DayStatePublicHoliday) == DayStatePublicHoliday){
			setBackgroundDrawable();
		}
	}

	public void setBackgroundDrawableForSunday(Drawable bgDrawable){
		mBgDrawableForSunday = bgDrawable;

		if((mDayStatus & DayStateSunday) == DayStateSunday){
			setBackgroundDrawable();
		}
	}

	public void setBackgroundDrawableForSaturday(Drawable bgDrawable){
		mBgDrawableForSaturday = bgDrawable;

		if((mDayStatus & DayStateSaturday) == DayStateSaturday){
			setBackgroundDrawable();
		}
	}

	public void setBackgroundDrawableForWeekday(Drawable bgDrawable){
		mBgDrawableForWeekday = bgDrawable;

		if((mDayStatus & DayStateWeekday) == DayStateWeekday && (mDayStatus & DayStatePublicHoliday) != DayStatePublicHoliday){
			setBackgroundDrawable();
		}
	}

	public void setVisibilityForParts(Integer visibilities[]){
		for(int i=0, size=visibilities.length; i<size; i++){
			switch(i){
			case PartMonthOfYear:
				if(visibilities[i] != null){
					mTxtMonthOfYear.setVisibility(visibilities[i]);
				}
				break;
			case PartDateNumber:
				if(visibilities[i] != null){
					mTxtDateNumber.setVisibility(visibilities[i]);
				}
				break;
			case PartDayOfWeek:
				if(visibilities[i] != null){
					mTxtDayOfWeek.setVisibility(visibilities[i]);
				}
				break;
			}
		}
	}

	public void setOnDatePickListener(OnDatePickListener l, Object userData) {
		mDatePickLister = l;
		setTag(TAG_FOR_USERDATA.hashCode(), userData);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(mRootClickListener);
		mUserClickListener = l;
	}
}
