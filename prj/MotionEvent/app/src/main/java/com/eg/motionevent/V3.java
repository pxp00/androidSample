package com.eg.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

class V3 extends androidx.appcompat.widget.AppCompatTextView {
	private static final String TAG = "V3";
	public V3(Context context) {
		super(context);
	}

	public V3(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e(TAG, "dispatchTouchEvent: action = " +  ev.getAction());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e(TAG, "onTouchEvent:  action = " +  event.getAction() );
//		if(event.getAction() == 0){
//			return true;
//		}
		return super.onTouchEvent(event);
	}
}
