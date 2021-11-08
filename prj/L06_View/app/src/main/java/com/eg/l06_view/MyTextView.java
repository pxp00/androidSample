package com.eg.l06_view;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 自定义TextView
 * @author 张晓飞
 *
 */
 // debug info, warn, erro
// from code, go straight to master
	// red, green, blue, yellow, purple

/**
sum:
 1. hot key:
    a. display mtds that could be overrated: ctrl + o
    b. display params list:  cmd + p
    c. found usages: atrl + F7
    d. structure: cmd + 7
    e. hierachy: ctrl + H

 2. myTextView
    myTextView(ctx); // use by new myTextView()
    myTextView(ctx, attrs); // use by layout file

    onFinishInflate() // layoutId only Inflate(layoutId);
    onResume()
    onAttachedToWindow()  // both new ViewX() and layout

    measure(size) // couldn't
    onMeasure

    layout(location)
    onLayout
 * */
public class MyTextView extends  TextView{
	private static final String TAG = "MyTextView";

	/** new创建对象 */
	public MyTextView(Context context) {
		super(context);
		Log.e("TAG", " MyTextView(Context)");
	}

	/** 布局文件创建对象 */
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("TAG", " MyTextView(Context, AttributeSet)");
	}
	
	/**
	 * 只有布局的方式才会调用
	 * 重写它: 用于得到子View对象
	 */
	@Override
	 protected void onFinishInflate() {
		Log.e("TAG", " onFinishInflate()");
		super.onFinishInflate();
	}

	/**
	 * 无论new还是布局都会调用此方法
	 * 重写它: 用于得到子View对象
	 */
	@Override
	protected void onAttachedToWindow() {
		Log.e("TAG", " onAttachedToWindow()");
		super.onAttachedToWindow();
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		Log.w(TAG, "onMeasure: widthMeasureSpec = " + widthMeasureSpec + ", heightMeasureSpec = " + heightMeasureSpec);
		
		int measuredHeight = getMeasuredHeight();
		int measuredWidth = getMeasuredWidth();
		Log.e("TAG", "onMeasure() measuredHeight="+measuredHeight+" measuredWidth="+measuredWidth);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public void layout(int l, int t, int r, int b) {
		Log.e("TAG","layout()");
		super.layout(l, t, r, b);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.e("TAG","onLayout() changed="+changed);
		super.onLayout(changed, left, top, right, bottom);
	}
}
