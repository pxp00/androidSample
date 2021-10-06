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

/**
 * contomerAttr
 *  1. new attr.xml ->
 *        <declare-styleable name="CustomeView_x">
 *         <attr name="font" format="dimension"/>
 *         <attr name="color" format="color"/>
 *         <attr name="text" format="string"/>
 *         </declare-styleable>
 *
 *  2. use by CustomerView_x
 *      a. TA.obtain attrs
 *      b. onDraw use the attrs
 *
 *  3. layout file use the  attrs xmlns: app
 * */

public class CustomView extends View {
	private static final String TAG = "CustomView";
	private Context context;
	//文字内容
	private String customText;
	//文字的颜色
	private int customColor;
	//文字的字体大小
	private int fontSize;
	//画笔
	private TextPaint textPaint;

	public CustomView(Context context) {
		this(context, null);
	}

	/**
	 * use by layout file
	 */
	public CustomView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initCustomAttrs(context, attrs);
	}
	/**
	 获取自定义属性
	 */
	private void initCustomAttrs(Context context, AttributeSet attrs) {
		//获取自定义属性。
		// TypedArray 来获取 XML layout 中的属性值
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomAttr);

		//打印出LayoutFile中所有的属性
		int count = attrs.getAttributeCount();
		for (int i = 0; i < count; i++) {
			Log.d(TAG, "attributeName = " + attrs.getAttributeName(i) + ", attributeValue =  " + attrs.getAttributeValue(i));
		}

		//获取字体大小,默认大小是16dp
		fontSize = (int) ta.getDimension(R.styleable.CustomAttr_font_1, 16);
		//获取文字内容
		customText = ta.getString(R.styleable.CustomAttr_text);
		//获取文字颜色，默认颜色是BLUE
		customColor = ta.getColor(R.styleable.CustomAttr_color_1, Color.BLUE);

		ta.recycle();

		textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(customColor);
		textPaint.setTextSize(fontSize);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e(TAG, "onDraw: custom text=" + customText + ", height(px) = " + DensityUtil.dip2px(context, 50));
		canvas.drawText(customText, 2, customText.length(), 0,  DensityUtil.dip2px(context, 50), textPaint);
	}

	public void setCustomText(String customText) {
		this.customText = customText;
	}

	public void setCustomColor(int customColor) {
		this.customColor = customColor;
		textPaint.setColor(customColor);
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		textPaint.setTextSize(fontSize);
	}
}

