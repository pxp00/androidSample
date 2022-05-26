package com.eg.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

class CustomViewGroup extends LinearLayout {
	private static final String TAG = "CustomViewGroup";
	public CustomViewGroup(Context context) {
		super(context);
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(TAG, "onMeasure: width = " + MeasureSpec.toString(widthMeasureSpec));
		Log.i(TAG, "onMeasure: height = " + MeasureSpec.toString(heightMeasureSpec));

		int width = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		super.onLayout(changed, 100, 100, 600, 600);

		Log.d(TAG, "onLayout: changed = " + changed + ", l = " + l + ",t = " +  t + ", r = " + r+ ", b = " + b);
		int cnt = getChildCount();
		View child = getChildAt(0);
		child.layout(10, 10, 300 , 300);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw: width = " + canvas.getWidth());
		Log.d(TAG, "onDraw: height = " + canvas.getHeight());
	}
}
