package com.tst.customAttr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tst.customview.R;


/*
	define attr
	summary: note & code

	define a new attr
	define a exist attr
	use
* */
public class MyTextView extends View {

	private static final String TAG = MyTextView.class.getSimpleName();

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, R.attr.defStyleAttr,0); // R.styleable.MyTextView: arry of attr  int[]
		int len =  ta.length();  // attrs on styleable only
		Log.e(TAG, "MyTextView: TypedArrayLenght = " + len);
		try{
			Log.e(TAG, "MyTextView: 1");
			String text = ta.getString(R.styleable.MyTextView_text); //  R.styleable.MyTextView_text: index of array
			Log.e(TAG, "MyTextView: 2.1");

			String text_tst = ta.getString(R.styleable.MyTextView_viewInflaterClass); //  R.styleable.MyTextView_text: index of array
			Log.e(TAG, "MyTextView: 2.2");

			String text_def = ta.getString(R.styleable.MyTextView_text_def); //  R.styleable.MyTextView_text: index of array
			Log.e(TAG, "MyTextView: 2.3");

			String title = ta.getString(R.styleable.MyTextView_title); //  R.styleable.MyTextView_text: index of array
			Log.e(TAG, "MyTextView: 2.4");


			String androidText = ta.getString(R.styleable.MyTextView_android_text);
			Log.e(TAG, "MyTextView: 3");

			int textAttr = ta.getInteger(R.styleable.MyTextView_testAttr, -1);
			Log.e(TAG, "MyTextView: 4");

			int color = ta.getColor(R.styleable.MyTextView_colorPrimary, 0x00ff00); //  R.styleable.MyTextView_text: index of array
			int color_tst = ta.getColor(R.styleable.MyTextView_color_tst, 0xff0000);
			Log.e(TAG, "text = " + text + ", android_text = " + androidText + ", textAttr = " + textAttr + ", color = " + Integer.toHexString(color)  + ", color_tst = " + Integer.toHexString(color_tst)); // int  -> toHexString

			Log.e(TAG, "text_tst = " + text_tst  + ", text_def = " + text_def + ", text_title = " + title); // int  -> toHexString
		}catch (Exception e){
			Log.e(TAG, "errTrace =  " + e.getStackTrace() );
			Log.e(TAG, "error : " + e.getMessage() );
		}finally {
			ta.recycle();
		}

		int count = attrs.getAttributeCount();
		for(int i = 0; i < count; i++){
			Log.e(TAG, "AttributeSet: name = " + attrs.getAttributeName(i) + ", value = " + attrs.getAttributeValue(i) );
		}
	}
}