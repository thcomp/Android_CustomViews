package jp.co.thcomp.testapi;

import android.app.Activity;
import android.os.Bundle;

import jp.co.thcomp.example.R;
import jp.co.thcomp.view.LockPatternView;
import jp.co.thcomp.view.LockPatternView.OnLockPatternListener;

public class TestLockPatternViewActivity extends Activity implements OnLockPatternListener{
	private static final String Passphrase = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_lockpatternview_layout);
		((LockPatternView)findViewById(R.id.ViewLockPattern)).setOnLockPatternListener(this);
	}

	@Override
	public void onInputLockPattern(String arg0) {
		/*
			LockPatternView's dots are indicated each number as 0~9.
			When user draws patten on it, it returns numbers in order.
		 */
		if(Passphrase.equals(arg0)){
			((LockPatternView)findViewById(R.id.ViewLockPattern)).setResult(true);
		}else{
			((LockPatternView)findViewById(R.id.ViewLockPattern)).setResult(false);
		}
	}
}
