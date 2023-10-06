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
 *  acty, G1, G2, V3  Dispatch
 *  1. activity & view without onInterceptTouchEvent(ViewGroup only);
 *  2. G1 onTouchEvent return true;
 *      a. down: determine the end of component; don't invoke the super onTouchEvent;
 *      b. move & up: dispatch to the end component one by one then invoke the onTouchEvent of the end component;
 *
 *
 *  1. onTouch / onTouchEvent(return true) & down action: determine the end of component;
 *  2. move & up: dispatch to the end of component only;
 *  3. intercept: return true, will invoke curComp's onTouch -> onTouchEvent;
 * */

/*
struct:
	activity -> G1 -> G2 -> V3

* */

/*
Q: without view/viewGroup consume event [default: activity consume event]

D/MainActivity: dispatchTouchEvent: action =0

W/G1: dispatchTouchEvent: action = 0
W/G1: onInterceptTouchEvent: action = 0

I/G2: dispatchTouchEvent:  action = 0
I/G2: onInterceptTouchEvent:  action = 0

E/V3: dispatchTouchEvent: action = 0
E/V3: onTouch: action = 0
E/V3: onTouchEvent:  action = 0

I/G2: onTouch: action = 0
I/G2: onTouchEvent: action = 0

W/G1: onTouch: action = 0
W/G1: onTouchEvent:  action = 0

D/MainActivity: onTouchEvent:  action = 0

D/MainActivity: dispatchTouchEvent: action = 2
D/MainActivity: onTouchEvent:  action = 2
* */

/*
Q2. V3.onTouchEvent return true, consume event:
	D/MainActivity: dispatchTouchEvent: action = 0

	W/G1: dispatchTouchEvent: action = 0
	W/G1: onInterceptTouchEvent: action = 0

	I/G2: dispatchTouchEvent:  action = 0
	I/G2: onInterceptTouchEvent:  action = 0

	E/V3: dispatchTouchEvent: action = 0
	E/V3: onTouch: action = 0
	E/V3: onTouchEvent:  action = 0

	D/MainActivity: dispatchTouchEvent: action = 2
	W/G1: dispatchTouchEvent: action = 2
	W/G1: onInterceptTouchEvent: action = 2
	I/G2: dispatchTouchEvent:  action = 2
	I/G2: onInterceptTouchEvent:  action = 2
	E/V3: dispatchTouchEvent: action = 2
	E/V3: onTouch: action = 2
	E/V3: onTouchEvent:  action = 2
* */

/*
Q3: G2.intercept return true -> don't dispatch to v3

D/MainActivity: dispatchTouchEvent: action = 0
W/G1: dispatchTouchEvent: action = 0
W/G1: onInterceptTouchEvent: action = 0
I/G2: dispatchTouchEvent:  action = 0
I/G2: onInterceptTouchEvent:  action = 0
I/G2: onTouch: action = 0
I/G2: onTouchEvent: action = 0
W/G1: onTouch: action = 0
W/G1: onTouchEvent:  action = 0
D/MainActivity: onTouchEvent:  action = 0

D/MainActivity: dispatchTouchEvent: action = 2
D/MainActivity: onTouchEvent:  action = 2
* */

public class MainActivity extends AppCompatActivity  {
	private static final String TAG = "MainActivity";
	RelativeLayout rlg2;
	RelativeLayout rlg1;
	V3 v3;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rlg1 = findViewById(R.id.vgp1);
		rlg2 = findViewById(R.id.vgp2);
		v3 = findViewById(R.id.v3);

		rlg1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.w("G1", "onTouch: action = " +  event.getAction());
				return false;
			}
		});
		
		rlg2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("G2", "onTouch: action = " +  event.getAction());
				return false;
			}
		});

		v3.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e("V3", "onTouch: action = " +  event.getAction());
				return false;
			}
		});
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
}