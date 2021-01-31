package com.sk.skvideo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Create by smark
 * Time:2021/1/31
 * email:smarkwzp@163.com
 */
public class RecordButton extends AppCompatTextView {

    private OnRecordListener mListener;
    private ScaleAnimation mScaleAnimation;

    public RecordButton(@NonNull Context context) {
        super(context);
    }

    public RecordButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mListener == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mListener.onRecordStart();
                startAnimation();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mListener.onRecordStop();
                stopAnimation();
                break;
        }
        return true;
    }

    private void startAnimation() {
        mScaleAnimation = new ScaleAnimation(1, 0.7f, 1, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        mScaleAnimation.setDuration(800);
        mScaleAnimation.setFillAfter(true);
        mScaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);
        mScaleAnimation.setRepeatCount(10000);
        startAnimation(mScaleAnimation);
    }

    private void stopAnimation() {
        if (mScaleAnimation != null) {
            clearAnimation();
            mScaleAnimation = null;
        }
    }

    /**
     * 设置监听
     *
     * @param listener 监听回调
     */
    public void setOnRecordListener(OnRecordListener listener) {
        mListener = listener;
    }

    /**
     * 录制监听
     */
    public interface OnRecordListener {
        void onRecordStart();

        void onRecordStop();
    }

}
