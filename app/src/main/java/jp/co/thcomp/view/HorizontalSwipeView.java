package jp.co.thcomp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class HorizontalSwipeView extends FrameLayout{
    public static interface OnHiddenLayoutStatusChangeListener{
        public void onChangeHiddenLayoutStatus(HorizontalSwipeView view, HiddenLayoutStatus status);
    }

    private static final long SwipeIntervalMS = 100;

    public static enum HiddenLayoutStatus {
        Open(10000),
        Close(0);

        int pos;
        HiddenLayoutStatus(int toPos){
            pos = toPos;
        }

        public int position(){
            return pos;
        }
    }
    private HorizontalScrollView mRootLayout;
    private FrameLayout mMainContentLayout;
    private FrameLayout mHiddenContentLayout;
    private HiddenLayoutStatus mStatus= HiddenLayoutStatus.Close;
    private long mDownTimeMS = 0;
    private float mDownPositionX = 0f;
    private OnHiddenLayoutStatusChangeListener mHiddenLayoutStatusChangeListener;

    public HorizontalSwipeView(Context context) {
        super(context);
        init();
    }

    public HorizontalSwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalSwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnHiddenLayoutStatusChangeListener(OnHiddenLayoutStatusChangeListener listener){
        mHiddenLayoutStatusChangeListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        // change main content layout
        ViewGroup.LayoutParams params = mMainContentLayout.getLayoutParams();
        if(params.width < 0) {
            ViewParent parent = mMainContentLayout.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup parentViewGroup = (ViewGroup) parent;
                params.width = r - l;

                parentViewGroup.updateViewLayout(mMainContentLayout, params);
            }
        }
    }

    private void init(){
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootLayout = (HorizontalScrollView)inflater.inflate(R.layout.evl_horizontal_swipeview_layout, this, false);
        mRootLayout.setOnTouchListener(mScrollTouchListener);
        super.addView(mRootLayout);

        mMainContentLayout = (FrameLayout)findViewById(R.id.Evl_MainContent);
        mHiddenContentLayout = (FrameLayout)findViewById(R.id.Evl_HiddenContent);
    }

    @Override
    public void addView(View child) {
        if(child.equals(mRootLayout)){
            super.addView(child);
        }else {
            if(mMainContentLayout.getChildCount() == 0){
                mMainContentLayout.addView(child);
            }else{
                mHiddenContentLayout.addView(child);
            }
        }
    }

    @Override
    public void addView(View child, int index) {
        if(child.equals(mRootLayout)){
            super.addView(child, index);
        }else {
            if(mMainContentLayout.getChildCount() == 0){
                mMainContentLayout.addView(child, index);
            }else{
                mHiddenContentLayout.addView(child, index);
            }
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(child.equals(mRootLayout)){
            super.addView(child, index, params);
        }else {
            if(mMainContentLayout.getChildCount() == 0){
                mMainContentLayout.addView(child, index, params);
            }else{
                mHiddenContentLayout.addView(child, index, params);
            }
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if(child.equals(mRootLayout)){
            super.addView(child, params);
        }else {
            if(mMainContentLayout.getChildCount() == 0){
                mMainContentLayout.addView(child, params);
            }else{
                mHiddenContentLayout.addView(child, params);
            }
        }
    }

    @Override
    public void addView(View child, int width, int height) {
        if(child.equals(mRootLayout)){
            super.addView(child, width, height);
        }else {
            if(mMainContentLayout.getChildCount() == 0){
                mMainContentLayout.addView(child, width, height);
            }else{
                mHiddenContentLayout.addView(child, width, height);
            }
        }
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if(child.equals(mRootLayout)){
            return super.addViewInLayout(child, index, params);
        }else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if(child.equals(mRootLayout)){
            return super.addViewInLayout(child, index, params, preventRequestLayout);
        }else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void removeAllViews() {
        mMainContentLayout.removeAllViews();
        mHiddenContentLayout.removeAllViews();
    }

    @Override
    public void removeAllViewsInLayout() {
        mMainContentLayout.removeAllViewsInLayout();
        mHiddenContentLayout.removeAllViewsInLayout();
    }

    @Override
    public void removeViewAt(int index) {
        if(mMainContentLayout.getChildCount() > index){
            mMainContentLayout.removeViewAt(index);
        }else{
            mHiddenContentLayout.removeViewAt(index - mMainContentLayout.getChildCount());
        }
    }

    @Override
    public void removeViewInLayout(View view) {
        mMainContentLayout.removeViewInLayout(view);
        mHiddenContentLayout.removeViewInLayout(view);
    }

    @Override
    public void removeViews(int start, int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeViewsInLayout(int start, int count) {
        throw new UnsupportedOperationException();
    }

    public void reuseLayout(){
        requestLayout();
    }

    public void swipe(HiddenLayoutStatus nextStatus, boolean needAnimation){
        mStatus = nextStatus;
        if(needAnimation) {
            mRootLayout.smoothScrollTo(nextStatus.position(), 0);
        }else{
            mRootLayout.scrollTo(nextStatus.position(), 0);
        }

        if(mHiddenLayoutStatusChangeListener != null){
            mHiddenLayoutStatusChangeListener.onChangeHiddenLayoutStatus(this, nextStatus);
        }
    }

    private OnTouchListener mScrollTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            boolean ret = false;

            switch(action){
                case MotionEvent.ACTION_DOWN:
                    mDownTimeMS = System.currentTimeMillis();
                    mDownPositionX = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(mDownTimeMS == 0){
                        mDownTimeMS = System.currentTimeMillis();
                        mDownPositionX = event.getX();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(System.currentTimeMillis() - mDownTimeMS > SwipeIntervalMS){
                        float currentPositionX = event.getX();
                        if(currentPositionX - mDownPositionX > 0){
                            // swipe from left to right(close)
                            if(mStatus.equals(HiddenLayoutStatus.Open)){
                                swipe(HiddenLayoutStatus.Close, true);
                            }
                        }else{
                            // swipe from right to left(open)
                            if(mStatus.equals(HiddenLayoutStatus.Close)){
                                swipe(HiddenLayoutStatus.Open, true);
                            }
                        }
                    }else{
                        // return to source position
                        swipe(mStatus, true);
                    }
                    ret = true;
                    mDownTimeMS = 0;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mDownTimeMS = 0;
                    break;
            }

            return ret;
        }
    };
}
