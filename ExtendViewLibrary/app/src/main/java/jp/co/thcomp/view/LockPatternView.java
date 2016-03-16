package jp.co.thcomp.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import jp.co.thcomp.util.LogUtil;

public class LockPatternView extends View {
	private static final String TAG = "LockPatternView";
	private static final int DEFAULT_ROW_SIZE = 3;
	private static final int DEFAULT_COL_SIZE = 3;
	private static final int DELAY_INTERVAL_MS = 1000;
	private static final int STATE_INIT = 0;
	private static final int STATE_DRAW_NORMAL = 1;
	private static final int STATE_DRAWING_PATTERN = 2;
	private static final int STATE_DRAWING_RESULT_PATTERN = 3;

	private static boolean Initialized = false;
	private static int LockPatternViewDotSize = 0;
	private static int LockPatternViewMinMarginSize = 0;
	private static int LockPatternViewMaxDotSpaceSize = 0;
	private static int LockPatternViewMinDotSpaceSize = 0;
	private static Paint NormalDotPaint = new Paint();
	private static Paint SelectedDotPaint = new Paint();
	private static Paint OkDotPaint = new Paint();
	private static Paint NgDotPaint = new Paint();

	private int mState = STATE_INIT;
	private DotInfo mDotInfos[][] = null;
	private DotInfo mSelectedDotInfos[] = null;
	private int mSelectedDotSize = 0;
	private Handler mHandler;
	private OnLockPatternListener mListener;
	private Boolean mResult = null;
	private Runnable mRedrawRunnable = new Runnable(){
		@Override
		public void run() {
			invalidate();
		}
	};
	private Runnable mClearRunnable = new Runnable(){
		@Override
		public void run() {
			mState = STATE_INIT;
			mResult = null;
			invalidate();
		}
	};
	
	public LockPatternView(Context context) {
		super(context);
		initialize();
	}

	public LockPatternView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public LockPatternView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private synchronized void initialize(){
		Context context = getContext();
		if(!Initialized){
			Resources res = context.getResources();

			Initialized = true;
			LockPatternViewDotSize = res.getDimensionPixelSize(R.dimen.Evl_LockPatternViewDotSize);
			LockPatternViewMinMarginSize = LockPatternViewDotSize / 2;
			LockPatternViewMinDotSpaceSize = LockPatternViewDotSize * 2;
			LockPatternViewMaxDotSpaceSize = res.getDimensionPixelSize(R.dimen.Evl_LockPatternViewMaxDotSpaceSize);
			NormalDotPaint.setColor(Color.GRAY);
			SelectedDotPaint.setColor(Color.MAGENTA);
			OkDotPaint.setColor(Color.BLUE);
			NgDotPaint.setColor(Color.RED);
		}
		mHandler = new Handler(context.getMainLooper());
	}

	public void setOnLockPatternListener(OnLockPatternListener listener){
		mListener = listener;
	}

	public void clear(){
		mResult = null;
		mHandler.post(mClearRunnable);
	}

	public void setResult(boolean ret){
		mState = STATE_DRAWING_RESULT_PATTERN;
		mResult = Boolean.valueOf(ret);
		mHandler.post(mRedrawRunnable);
		mHandler.postDelayed(mClearRunnable, DELAY_INTERVAL_MS);
	}

//	public boolean setRowSize(int row){
//		boolean ret = false;
//
//		if(mState == STATE_INIT){
//			ret = true;
//			DEFAULT_ROW_SIZE = row;
//		}
//
//		return ret;
//	}
//	
//	public boolean setColumnSize(int col){
//		boolean ret = false;
//
//		if(mState == STATE_INIT){
//			ret = true;
//			DEFAULT_COL_SIZE = col;
//		}
//
//		return ret;
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			notifyInputLockPattern();
			break;
		case MotionEvent.ACTION_DOWN:
			mHandler.removeCallbacks(mRedrawRunnable);
			mHandler.removeCallbacks(mClearRunnable);
			mState = STATE_INIT;
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			if(selectDot(event)){
				mState = STATE_DRAWING_PATTERN;
				invalidate();
			}
			break;
		}
		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mDotInfos = null;
		mState = STATE_INIT;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		LogUtil.d(TAG, "onDraw: state = " + mState);
		switch(mState){
		case STATE_INIT:
			mState = STATE_DRAW_NORMAL;
			mSelectedDotSize = 0;
			drawPatternNormal(canvas);
			break;
		case STATE_DRAW_NORMAL:
			drawPatternNormal(canvas);
			break;
		case STATE_DRAWING_PATTERN:
		case STATE_DRAWING_RESULT_PATTERN:
			drawPatternNormal(canvas);
			drawSelectedPattern(canvas);
			break;
		}
	}

	private void notifyInputLockPattern(){
		OnLockPatternListener listener = mListener;
		if(listener != null){
			DotInfo selectedDotInfos[] = mSelectedDotInfos;
			StringBuilder patternCode = new StringBuilder();
			for(int i=0, size=mSelectedDotSize; i<size; i++){
				patternCode.append(selectedDotInfos[i].value);
			}
			listener.onInputLockPattern(patternCode.toString());
		}
	}
	
	private void drawPatternNormal(Canvas canvas){
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int canvasWidth = getWidth() - paddingLeft - paddingRight;
		int canvasHeight = getHeight() - paddingTop - paddingBottom;

		ViewGroup.LayoutParams params = getLayoutParams();
		int requireWidth = params.width;
		int requireHeight = params.height;

		if(mDotInfos == null){
			mDotInfos = new DotInfo[DEFAULT_ROW_SIZE][DEFAULT_COL_SIZE];
			int value = 0;
			for(int i=0, sizeI=DEFAULT_ROW_SIZE; i<sizeI; i++){
				for(int j=0, sizeJ=DEFAULT_COL_SIZE; j<sizeJ; j++){
					mDotInfos[i][j] = new DotInfo();
					mDotInfos[i][j].value = value;
					value++;
				}
			}
			mSelectedDotInfos = new DotInfo[DEFAULT_ROW_SIZE * DEFAULT_COL_SIZE];

			int offsetHorizontal = LockPatternViewMinMarginSize;
			int offsetVertical = LockPatternViewMinMarginSize;
			int minCanvasWidth = offsetHorizontal * 2 + LockPatternViewDotSize * DEFAULT_COL_SIZE + LockPatternViewMinDotSpaceSize * (DEFAULT_COL_SIZE - 1);
			int minCanvasHeight = offsetVertical * 2 + LockPatternViewDotSize * DEFAULT_COL_SIZE + LockPatternViewMinDotSpaceSize * (DEFAULT_COL_SIZE - 1);

			if((canvasWidth >= minCanvasWidth) && (canvasHeight >= minCanvasHeight)){
				int spaceHorizontal = LockPatternViewMinDotSpaceSize;
				int spaceVertical = LockPatternViewMinDotSpaceSize;

				int needCanvasWidth = canvasWidth;
				int needCanvasHeight = canvasHeight;

				switch(requireWidth){
					case ViewGroup.LayoutParams.WRAP_CONTENT:
						needCanvasWidth = offsetHorizontal * 2 + LockPatternViewDotSize * DEFAULT_COL_SIZE + spaceHorizontal * (DEFAULT_COL_SIZE - 1);
						break;
					case ViewGroup.LayoutParams.MATCH_PARENT:
						break;
					default:
						spaceHorizontal = (canvasWidth - (offsetHorizontal * 2) - LockPatternViewDotSize * DEFAULT_COL_SIZE) / (DEFAULT_COL_SIZE - 1);
						break;
				}

				switch(requireHeight){
					case ViewGroup.LayoutParams.WRAP_CONTENT:
						needCanvasHeight = offsetVertical * 2 + LockPatternViewDotSize * DEFAULT_COL_SIZE + spaceVertical * (DEFAULT_COL_SIZE - 1);
						break;
					case ViewGroup.LayoutParams.MATCH_PARENT:
						break;
					default:
						spaceVertical = (canvasHeight - (offsetVertical * 2) - LockPatternViewDotSize * DEFAULT_ROW_SIZE) / (DEFAULT_ROW_SIZE - 1);
						break;
				}

				int halfLockPatternViewDotSize = LockPatternViewDotSize / 2;
				for(int i=0, sizeI=DEFAULT_ROW_SIZE; i<sizeI; i++){
					for(int j=0, sizeJ=DEFAULT_COL_SIZE; j<sizeJ; j++){
						int left = offsetHorizontal + j * (LockPatternViewDotSize + spaceHorizontal);
						int top = offsetVertical + i * (LockPatternViewDotSize + spaceVertical);
	
						mDotInfos[i][j].dotRect.set(left, top, left + LockPatternViewDotSize, top + LockPatternViewDotSize);
						mDotInfos[i][j].touchRangeRect.set(left - halfLockPatternViewDotSize, top - halfLockPatternViewDotSize, left + LockPatternViewDotSize + halfLockPatternViewDotSize, top + LockPatternViewDotSize + halfLockPatternViewDotSize);
					}
				}

				if((needCanvasWidth != canvasWidth) || (needCanvasHeight != canvasHeight)){
					ViewParent parent = getParent();
					if(parent instanceof ViewGroup){
						params.width = needCanvasWidth;
						params.height = needCanvasHeight;
						((ViewGroup)parent).updateViewLayout(this, params);
					}
					return;
				}
			}
		}

		for(int i=0, sizeI=DEFAULT_ROW_SIZE; i<sizeI; i++){
			for(int j=0, sizeJ=DEFAULT_COL_SIZE; j<sizeJ; j++){
				LogUtil.d(TAG, "draw circle: (" + i + ", " + j + ") = (" + mDotInfos[i][j].dotRect.centerX() + ", " + mDotInfos[i][j].dotRect.centerY() + ", " + mDotInfos[i][j].dotRect.width() / 2 + ")");
				canvas.drawCircle(mDotInfos[i][j].dotRect.centerX(), mDotInfos[i][j].dotRect.centerY(), mDotInfos[i][j].dotRect.width() / 2, NormalDotPaint);
			}
		}
	}

	private boolean selectDot(MotionEvent event){
		boolean ret = false;

		LogUtil.d(TAG, "selectDot: mSelectedDotSize = " + mSelectedDotSize);
		if(mSelectedDotSize < DEFAULT_ROW_SIZE * DEFAULT_COL_SIZE){
			DotInfo selectedDotInfos[] = mSelectedDotInfos;
			DotInfo dotInfos[][] = mDotInfos;
			DotInfo lastSelectedDotInfo = mSelectedDotSize > 0 ? selectedDotInfos[mSelectedDotSize - 1] : null;
//			int posX = (int)event.getRawX();
//			int posY = (int)event.getRawY();
			int posX = (int)event.getX();
			int posY = (int)event.getY();
			boolean brokenFlag = false;

			for(int i=0, sizeI=DEFAULT_ROW_SIZE; i<sizeI; i++){
				for(int j=0, sizeJ=DEFAULT_COL_SIZE; j<sizeJ; j++){
					if(dotInfos[i][j].touchRangeRect.contains(posX, posY)){
						if(lastSelectedDotInfo == dotInfos[i][j]){
							// ignore
							break;
						}else{
							selectedDotInfos[mSelectedDotSize] = dotInfos[i][j];
							mSelectedDotSize++;
							ret = true;
						}
						brokenFlag = true;
						break;
					}
				}

				if(brokenFlag){
					break;
				}
			}
			
		}
		
		return ret;
	}

	private void drawSelectedPattern(Canvas canvas){
		DotInfo dotInfos[] = mSelectedDotInfos;
		Rect currentDotRect = null;
		Rect prevDotRect = null;
		Paint usePaint = mResult == null ? SelectedDotPaint : (mResult ? OkDotPaint : NgDotPaint);
		
		for(int i=0, size=mSelectedDotSize; i<size; i++){
			currentDotRect = dotInfos[i].dotRect;
			canvas.drawCircle(currentDotRect.centerX(), currentDotRect.centerY(), currentDotRect.width() / 2, usePaint);
			if(prevDotRect != null){
				canvas.drawLine(prevDotRect.centerX(), prevDotRect.centerY(), currentDotRect.centerX(), currentDotRect.centerY(), usePaint);
			}
			prevDotRect = currentDotRect;
		}
	}
	
	private static class DotInfo{
		public Rect dotRect = new Rect();
		public Rect touchRangeRect = new Rect();
		public int value = 0;
	}

	public static interface OnLockPatternListener{
		public void onInputLockPattern(String patternCode);
	}
}
