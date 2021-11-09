package com.eg.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

class G1 extends RelativeLayout {
	private static final String TAG = "G1";
	public G1(Context context) {
		super(context);
	}

	public G1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.w(TAG, "dispatchTouchEvent: action = " +  ev.getAction());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.w(TAG, "onInterceptTouchEvent: action = " +  ev.getAction());

		return super.onInterceptTouchEvent(ev);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.w(TAG, "onTouchEvent:  action = " +  event.getAction() );
//		if(event.getAction() == 0){
//			Log.w(TAG, "onTouchEvent: return true" );
//			return true;
//		}
//		else{
//			Log.w(TAG, "onTouchEvent: invoke super"  );
			return super.onTouchEvent(event);
//		}
	}
}
