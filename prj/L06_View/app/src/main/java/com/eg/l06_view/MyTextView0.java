package com.eg.l06_view;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义TextView
 * @author 张晓飞
 *
 */
 // debug info, warn, erro
// from code, go straight to master
	// red, green, blue, yellow, purple

/**

 MyTextView
    constrc
    MyTextView(ctx);
    MyTextView(ctx, attrs);

    // finish inflate
    onFinishInflate() // layoutId only Inflate(layoutId);
    onAttachedToWindow() //

    // measure size
    measure
    onMeasure

    // layout location
    layout
    onLayout

    // remesure -> layout
    requestLayout
 * */

/**
 final: couldn't be overrated; save to static area
 private: invisible
 */

/**
	thread
	network
	java_base

    Rxjava apply -> Qs -> key point source code;
*/

class MyTextView0 extends AppCompatTextView {
	public MyTextView0(Context context) {
		super(context);
	}

	public MyTextView0(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {  // curCls(private), curPackageCls(default), childClss(protected), allClss(public)
		super.onFinishInflate();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	public void layout(int l, int t, int r, int b) {
		super.layout(l, t, r, b);
	}
}



