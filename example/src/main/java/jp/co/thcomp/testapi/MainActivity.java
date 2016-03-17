package jp.co.thcomp.testapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import jp.co.thcomp.example.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LinearLayout targetLayout = (LinearLayout)findViewById(R.id.llTriggerButtons);
		addButton("test LockPatternView", targetLayout, TestLockPatternViewActivity.class);
		addButton("test CalendarView", targetLayout, TestCalendarViewActivity.class);
	}

	private void addButton(String message, LinearLayout targetLayout, final Class<?> launchActivityClass){
		Button button = new Button(this);
		button.setClickable(true);
		button.setText(message);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, launchActivityClass);
				MainActivity.this.startActivity(intent);
			}
		});
		targetLayout.addView(button);
	}
}
