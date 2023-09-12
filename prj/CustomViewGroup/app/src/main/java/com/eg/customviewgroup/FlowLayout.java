package com.eg.customviewgroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/*
 reqs: 容器可以包裹子view，一个从左往右水平排列（排满换行）的效果
 src: https://openxu.blog.csdn.net/article/details/51500304

 main_line: ignore details, kp: main_flow(steps) and use API(input and output, function)
    1. reqs;
    2. apply firstly; // flow & steps & API => details

 Qs:
    1. final display size ?
        A: c.layout(lt, rb), p.layout(lt, rb)

    2.setMeasuredDimension ?
        A: reference size for onLayout.

 Qs:
    0: background of button on MaterialTheme ?
    A: backgroundTint

    1: border of container ?
    A: shape

    2: onLayout(l, t, r, b)  // lt, rb
    A: absolute position

    3: child.layout(lt, rb)
    A: relative position

 */

/*
	Q. cLp.wrap_content & p.mode = AT_MOST/EXACTLY, c.size = ?
		=> c.size = p.size

	1. p.AT_MOST & cLp.match_parent ?
		=> c.mode = AT_MOST

	2. cView final size ? p.layout(lt, rb) ?  c.onMeasure(msW, msH) ?
		=> c.setMeasuredDimension(wMS, hMS);

	3. MeasureSpec: AT_MOST, EXACTLY, UNSPECIFIED ?

*/

/*
	Q. layout0 include view1 + layout2;  view1.Lp.w = match_parent, layout2.Lp.w = match_parent; ?
		=> view1.size = p.size, layout2.size = p.size; disp range refer to layout0;
* */


/*
	p.size  // display range
	c.size  // refer by canvas
	canvas.size  // useless

	canvas.size <= c.size <= p.size
* */

class TstLayout extends ViewGroup{

	public TstLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}
}

public class FlowLayout extends ViewGroup {
	private static final String TAG = "CustomLayout";

	public FlowLayout(Context context) {
		super(context);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return super.generateLayoutParams(attrs);
	}

	/*
		measureChildren -> cMS = getChildMS(pMS, padding, cLP.mode)  => p.size/ cLp.size  match_parent & wrap_content, clp.size

		c.canvas
		c.lp
		p.size

		t0, Iget ? better? jdoitnAbg; braveAi;

		button
	*/

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);  // measure children
		super.onMeasure(widthMeasureSpec, heightMeasureSpec); // c.size =  p.size/cLp.size
	}

//	/**
//	 * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
//	 */
//	@SuppressLint("NewApi") @Override
//	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {  // calculate reference dimension for onLayout
//
//		Log.d(TAG, "onMeasure: width = " + MeasureSpec.toString(widthMeasureSpec));
//		Log.d(TAG, "onMeasure: height = " + MeasureSpec.toString(heightMeasureSpec));
//
//		// 计算出所有的childView的宽和高
//		measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//		// 测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
//		// 稍后会重新写这个方法，能达到wrap_content的效果
//		// setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
//		int usedWidth  = 0;
//		int maxHeight = 0;
//		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
//		int cnt = getChildCount();
//		int containerHeight = 0;
//		int containerWidth = 0;
//
//		for(int i = 0; i < cnt; i ++){
//			View child  = getChildAt(i);
//			int childWidth = child.getMeasuredWidth();
//			int childHeight = child.getMeasuredHeight();
//
//			usedWidth += childWidth;
//
//			if(usedWidth <= parentWidth){  // space enough
//
//			}else{  // wrap
//				containerHeight += maxHeight;  // add last lineHeight
//
//				containerWidth += (usedWidth - childWidth);
//
//				usedWidth = 0;
//				usedWidth += childWidth;
//
//				Log.d(TAG, "onMeasure: containerHeight = " + containerHeight);
//			}
//
//			maxHeight = Math.max(maxHeight, childHeight);
//		}
//
//		containerHeight += maxHeight;
//		containerWidth  += usedWidth;
//
//		Log.d(TAG, "onMeasure: finalContainerHeight = " + containerHeight);
//		Log.d(TAG, "onMeasure: finalContainerWidth = " + containerWidth);
//
//		if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)  // AT_MOST, EXACTLY,
//			heightMeasureSpec = MeasureSpec.makeMeasureSpec(containerHeight, MeasureSpec.AT_MOST);
//
//		if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST)
//			widthMeasureSpec = MeasureSpec.makeMeasureSpec(containerWidth, MeasureSpec.AT_MOST);
//
//		Log.d(TAG, "onMeasure: width size = " + MeasureSpec.getSize(widthMeasureSpec));
//
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  // except wrap content
//	}

	/**
	 * 为所有的子控件摆放位置.
	 *
	 */


	/*
	lt, rb
	onLayout(l, t, r, b);
	p.size, cLp.size

	parentWidth = getWidth()
	child.layout(l, t, r, b);


	p.size  // display range
	c.size // refer by canvas
	canvas.size

	* */
	@Override
	protected void onLayout( boolean changed, int left, int top, int right, int bottom) {
		Log.d(TAG, "onLayout: left = " + left + ", top = " + top + ", right = " + right + ", bottom = " + bottom); // onLayout: left = 530, top = 264, right = 1080, bottom = 2122  pixel
		int curLineMaxHeight = 0;
		int pHeight = getWidth();  // mRight - mLeft  // after invoke layout(), getWidth() valid.
		int cLeft = 0;
		int cTop = 0;
		int cnt = getChildCount();

		/*
			for(i=0; i< cnt; i++){
				c.layout(l, t, l + cWidth, t + cHeight);
			}
		* */

		for(int i = 0; i < cnt; i ++){
			View child  = getChildAt(i);
			int cWidth = child.getMeasuredWidth();  // after invoke measure, getMeasuredWidth() valid.
			int cHeight = child.getMeasuredHeight();

			if(cLeft + cWidth <= pHeight){  // space enough

			}else{  // wrap
				cTop += curLineMaxHeight;  // cTop += lastLineMaxHeight
				curLineMaxHeight = 0;  //curLine

				cLeft = 0;
			}

			curLineMaxHeight = Math.max(curLineMaxHeight, cHeight);

			Log.d(TAG, "onLayout: left = " + cLeft + ", top = " + cTop + ", right = " + (cLeft + cWidth) + ", bottom = " + (cTop + cHeight));

			child.layout(cLeft, cTop, cLeft + cWidth, cTop + cHeight);  // relative position (pixel)

			cLeft += cWidth;  // after layout cLeft += cWidth
		}
	}
}


