package com.eg.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

class G2 extends RelativeLayout {
	private static final String TAG = "G2";
	public G2(Context context) {
		super(context);
	}

	public G2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i(TAG, "dispatchTouchEvent:  action = " +  ev.getAction());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i(TAG, "onInterceptTouchEvent:  action = " +  ev.getAction());

		if(ev.getAction() == 2){
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i(TAG, "onTouchEvent: action = " +  event.getAction());
		return super.onTouchEvent(event);
	}
}
