package com.eg.motionevent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


/**
 *  dispatch, intercept(ViewGroup), lsr.onTouch, onTouchEvent()
 *  G1, G2, V3  Dispatch
 *  1. activity & view witout onInterceptTouchEvent(ViewGroup only);
 *  2. G1 onTouchEvent return true;
 *      a. down: determine the end of component; don't invoke the super onTouchEvent;
 *      b. move & up: dispatch  to the end component one by one then invoke the onTouchEvent of the end component;
 *
 *
 *  1. onTouch / onTouchEvent(return true) & down action: determine the end of component;
 *  2. move & up: dispatch to the end of component only;
 *  3. intercept: return true, will invoke curComp's onTouch -> onTouchEvent;
 *
 *
 * */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
	private static final String TAG = "MainActivity";
	RelativeLayout rl;
	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rl = findViewById(R.id.g2);
		rl.setOnTouchListener(this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent: action = " +  ev.getAction());
		return super.dispatchTouchEvent(ev);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent:  action = " +  event.getAction() );
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.i("G2:", "onTouch: action = " + event.getAction());
		return false;
	}
}