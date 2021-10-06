package com.tst.customattr;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tst.customattr.Utils.DensityUtil;

public class CustomViewZero extends View {
	// private(cur_cls), default(cur_package), protected(cur_cls & child_cls), public(all_cls)
	private static final String TAG =  CustomViewZero.class.getSimpleName();
	private int color;
	private float fontSize;
	private String text;
	TextPaint textPaint;
	private Context context;

	public CustomViewZero(Context context) {
		this(context, null);
	}

	// XML Layout file will invoke this API delivery attrSet
	public CustomViewZero(Context context,  AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomViewZero(Context context,  AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs){
		int cnt = attrs.getAttributeCount();
		for(int i = 0; i <cnt; i++){
			Log.d(TAG, "attr_" + i + ": " + attrs.getAttributeName(i) + " = " + attrs.getAttributeValue(i));
		}

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewZero);
		color = typedArray.getColor(R.styleable.CustomViewZero_color, Color.BLUE);
		fontSize = typedArray.getDimension(R.styleable.CustomViewZero_fontsize, 16);
		text = typedArray.getString(R.styleable.CustomViewZero_text);
		typedArray.recycle();

		textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(color);
		textPaint.setTextSize(fontSize);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// TODO: 6/18/21  text display center 
		canvas.drawText(text, 0, text.length(), DensityUtil.dip2px(context, 0),  DensityUtil.dip2px(context, 0), textPaint);
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setFont(float fontSize) {
		this.fontSize = fontSize;
	}

	public void setText(String text) {
		this.text = text;
	}
}

