package com.eg.customviewgroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 reqs: 容器可以包裹子view，一个从左往右水平排列（排满换行）的效果
 src: https://openxu.blog.csdn.net/article/details/51500304

 main_line: ignore details, kp: main_flow(steps) and use API(input and output, function)
    1. reqs;
    2. apply firstly; // flow & steps & API => details

 Qs:
    1. final display size ?
        A: c.layout(lt, rb), l.layout(lt, rb)

    2.setMeasuredDimensioned ?
        A: reference size for onLayout.

 Qs:
    0: backgound of button on MaterialTheme ?
    A: backgroundTint

    1: border of container ?
    A: shape

    2: onLayout(l, t, r, b)  // lt, rb
    A: absolute position

    3: child.layout(lt, rb)
    A: relative position

 */
public class CustomLayout extends ViewGroup {
	private static final String TAG = "CustomLayout";

	public CustomLayout(Context context) {
		super(context);
	}

	public CustomLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return super.generateLayoutParams(attrs);
	}

	/**
	 * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
	 */
	@SuppressLint("NewApi") @Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {  // calculate reference dimension for onLayout

		Log.d(TAG, "onMeasure: width = " + MeasureSpec.toString(widthMeasureSpec));
		Log.d(TAG, "onMeasure: height = " + MeasureSpec.toString(heightMeasureSpec));

		// 计算出所有的childView的宽和高
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		
		// 测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
		// 稍后会重新写这个方法，能达到wrap_content的效果
		// setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
		int usedWidth  = 0;
		int maxHeight = 0;
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int cnt = getChildCount();
		int containerHeight = 0;
		int containerWidth = 0;

		for(int i = 0; i < cnt; i ++){
			View child  = getChildAt(i);
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			usedWidth += childWidth;

			if(usedWidth <= parentWidth){  // space enough

			}else{  // wrap
				containerWidth += (usedWidth - childWidth);

				usedWidth = 0;
				usedWidth += childWidth;


				containerHeight += maxHeight;
				Log.d(TAG, "onMeasure: containerHeight = " + containerHeight);
			}

			maxHeight = Math.max(maxHeight, childHeight);
		}

		containerHeight += maxHeight;
		containerWidth  += usedWidth;

		Log.d(TAG, "onMeasure: finalontainerHeight = " + containerHeight);

		if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(containerHeight, MeasureSpec.AT_MOST);

		if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST)
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(containerWidth, MeasureSpec.AT_MOST);

		Log.d(TAG, "onMeasure: width size = " + MeasureSpec.getSize(widthMeasureSpec));

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  // except wrap content
	}

	/**
	 * 为所有的子控件摆放位置.
	 */
	@Override
	protected void onLayout( boolean changed, int left, int top, int right, int bottom) {
		Log.d(TAG, "onLayout: left = " + left + ", top = " + top + ", right = " + right + ", bottom = " + bottom);
		int usedWidth  = 0;
		int maxHeight = 0;
		int parentWidth = getWidth();
		int childL = 0;
		int childT = 0;
		int cnt = getChildCount();

		for(int i = 0; i < cnt; i ++){
			View child  = getChildAt(i);
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			usedWidth += childWidth;

			if(usedWidth <= parentWidth){  // space enough

			}else{  // wrap
				usedWidth = 0;
				usedWidth += childWidth;

				childT += maxHeight;
				childL = 0;
			}

			maxHeight = Math.max(maxHeight, childHeight);

			Log.d(TAG, "onLayout: left = " + childL + ", top = " + childT + ", right = " + (childL + childWidth) + ", bottom = " + (childT + childHeight));

			child.layout(childL, childT, childL + childWidth, childT + childHeight);  // relative position

			childL += childWidth;
		}
	}
}


