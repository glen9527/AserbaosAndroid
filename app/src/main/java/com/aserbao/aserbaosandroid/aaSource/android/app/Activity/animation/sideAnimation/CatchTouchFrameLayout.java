package com.aserbao.aserbaosandroid.aaSource.android.app.Activity.animation.sideAnimation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.aserbao.aserbaosandroid.AserbaoApplication;
import com.aserbao.aserbaosandroid.aaSource.android.app.Activity.animation.sideAnimation.rv.adapters.SlideItemAnimationAdapter;

/**
 * 功能:
 *
 * @author aserbao
 * @date : On 2019-09-23 16:41
 * @project:AserbaosAndroid
 * @package:com.aserbao.aserbaosandroid.aaSource.android.app.Activity.animation.sideAnimation
 */
public class CatchTouchFrameLayout extends FrameLayout {

    private static final String TAG = "CatchTouchFrameLayout";
    public  static final int CATCH_TOUCH_FRAME_LAYOUT = 1;

    public CatchTouchFrameLayout( Context context) {
        this(context,null);
    }

    public CatchTouchFrameLayout( Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CatchTouchFrameLayout( Context context,  AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CatchTouchFrameLayout( Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private float mStartRawX,mStartRawY,difRawX,difRawY;
    private boolean mIsScrolling = false,mIsCallLongClick = false;// 是否返回过常按事件？
    private long mStartClickTime;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartClickTime = System.currentTimeMillis();
                mStartRawX = event.getRawX();
                mStartRawY = event.getRawY();
                mIsScrolling = false;
                mIsCallLongClick = false;
                useAnimation(0,event.getAction());
                super.dispatchTouchEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                difRawX = event.getRawX() - mStartRawX;
                difRawY = event.getRawY() - mStartRawY;
                float absX = Math.abs(difRawX);
                float absY = Math.abs(difRawY);
                if (absX > absY && absX + absY > 20){
                    mIsScrolling = true;
                }

                if (!mIsScrolling && !mIsCallLongClick && System.currentTimeMillis() - mStartClickTime > 150 && mIItemOnTouchCallBackListener != null ){
                    mIItemOnTouchCallBackListener.onClickOrLongPress(true,null);
                    mIsCallLongClick = true;
                }

                if (mIsScrolling) useAnimation(difRawX,event.getAction());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                float chaX = event.getRawX() - mStartRawX;
                float chaY = event.getRawY() - mStartRawY;
                long chaTime = System.currentTimeMillis() - mStartClickTime;
                if (!mIsScrolling){
                    if (chaTime < 150){
                        if (mIItemOnTouchCallBackListener != null) {
                            mIItemOnTouchCallBackListener.onClickOrLongPress(false,null);
                        }
                    }else{
                        if (mIItemOnTouchCallBackListener != null && !mIsCallLongClick ) {
                            mIItemOnTouchCallBackListener.onClickOrLongPress(true,null);
                        }
                    }
                }else{
                    useAnimation(difRawX,event.getAction());
                    mIsScrolling = false;
                }
                break;
        }
        Log.e(TAG, "dispatchTouchEvent: " + event.getAction() + " difRawX = " + difRawX );
        return super.dispatchTouchEvent(event);
    }

    public SlideItemAnimationAdapter.IItemOnTouchCallBackListener mIItemOnTouchCallBackListener;
    public void setIItemOnTouchCallBackListener(SlideItemAnimationAdapter.IItemOnTouchCallBackListener itemOnTouchCallBackListener){
        mIItemOnTouchCallBackListener = itemOnTouchCallBackListener;
    }

    public void useAnimation(float finalX,int action) {
        if (mIItemOnTouchCallBackListener != null) {
            mIItemOnTouchCallBackListener.onScollView(this,finalX,action,CATCH_TOUCH_FRAME_LAYOUT);
        }
    }
}
