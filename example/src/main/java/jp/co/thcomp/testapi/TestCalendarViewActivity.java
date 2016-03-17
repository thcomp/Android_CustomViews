package jp.co.thcomp.testapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import jp.co.thcomp.example.R;
import jp.co.thcomp.view.DailyCalendarView;
import jp.co.thcomp.view.MonthlyCalendarView;
import jp.co.thcomp.view.WeeklyCalendarView;

public class TestCalendarViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewGroup scrollView = (ViewGroup)View.inflate(this, R.layout.test_calendarview_layout, null);
		ViewGroup layoutView = (ViewGroup)scrollView.getChildAt(0);
		setContentView(scrollView);

		Calendar today = Calendar.getInstance();

		for(int i=0, size=layoutView.getChildCount(); i<size; i++){
			View childView = layoutView.getChildAt(i);
			if(childView instanceof MonthlyCalendarView){
				((MonthlyCalendarView)childView).setMonth(today);
			}else if(childView instanceof WeeklyCalendarView){
				((WeeklyCalendarView)childView).setFirstDate(today);
			}else if(childView instanceof DailyCalendarView){
				((DailyCalendarView)childView).setDate(today);
			}
		}
	}
}
