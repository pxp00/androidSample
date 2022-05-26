package com.eg.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

class CustomTextView extends View {
	private static final String TAG = "CustomTextView";
	String mText;
	Paint mPaint;
	Rect mTextBounds;
	Context mContext;

	public CustomTextView(Context context) {
		super(context, null);
	}

	public CustomTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs, 0);
		mContext = context;
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.customTextView);
		mText = ta.getString(R.styleable.customTextView_android_text);
		Log.e(TAG, "text = " + mText);
		ta.recycle();

		mPaint = new Paint();
		mTextBounds = new Rect();
		mPaint.setTextSize(50);
//		mPaint.setColor(0xff0000);
		mPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);  // getTextBounds

		Log.d(TAG, "CustomTextView: dip2px(300) = " + Utils.dip2px(mContext, 300));
	}

	public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getMode(heightMeasureSpec);

		Log.d(TAG, "onMeasure: width = " + MeasureSpec.toString(widthMeasureSpec));
//		Log.i(TAG, "onMeasure: height = " + MeasureSpec.toString(heightMeasureSpec));

		int px = Utils.dip2px(mContext, 320);
		setMeasuredDimension(px, px);  // childView
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

		Log.d(TAG, "onLayout: changed = " + changed + ", l = " + left + ",t = " +  top + ", r = " + right+ ", b = " + bottom);

		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "onDraw: width = " + canvas.getWidth());  // 640
		Log.d(TAG, "onDraw: height = " + canvas.getHeight()); // 640

		canvas.drawText(mText,0, mTextBounds.height(), mPaint);
	}

}
