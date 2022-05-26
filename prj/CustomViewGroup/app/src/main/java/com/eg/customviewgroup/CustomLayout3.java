package com.eg.customviewgroup;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义布局管理器:
 * reqs:
    1. viewGroup容纳所有子View(支持wrap_content)，
    2. 自定义Layout_params layout_position,支持左上,左下，右上，右下，居中； // 1.extends LayoutParams, 2.override generateLp 3. cast to get params
    3. support layout_margin; // cView: border-box, border as reference_line // 1. override generateLp 2. cast to get params

 * src: https://openxu.blog.csdn.net/article/details/51500304
 *
 * main_line: flow and use API(input and output, function), ignore src code (API realize)
 *  1. reqs;
 *  2. apply firstly;

 Qs: <CustomView android:Margin = 30dp/>
    1. getMargin ?
        A: 1. override generateLayout; 2. cast: lp = cView.getLayoutParams();  3. lp.topMargin

    2. use Margin ?
		A: cViewTotal = cWidth + leftMargin + rightMargin
        onMeasure: totalWidth = cWidth + leftMargin + rightMargin
        onLayout: x +leftMargin -rightMargin; y +topMargin -bottomMargin;
 */
public class CustomLayout3 extends ViewGroup {
	private static final String TAG = "CustomLayout";

	public CustomLayout3(Context context) {
		super(context);
	}

	public CustomLayout3(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLayout3(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public static class CustomLayoutParams extends MarginLayoutParams {
		public static final int POSITION_MIDDLE = 0; // 中间
		public static final int POSITION_LEFT_TOP= 1; // 左上方
		public static final int POSITION_RIGHT_TOP = 2; // 右上方
		public static final int POSITION_LEFT_BOTTOM = 3; // 左下角
		public static final int POSITION_RIGHT_BOTTOM = 4; // 右下角

		public int position = POSITION_LEFT_TOP;  // 默认我们的位置就是左上角

		public CustomLayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
			TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.CustomLayout );
			//获取设置在子控件上的位置属性
			position = a.getInt(R.styleable.CustomLayout_layout_position ,position );

			a.recycle();
		}

		public CustomLayoutParams( int width, int height) {
			super(width, height);
		}

		public CustomLayoutParams(LayoutParams source) {
			super(source);
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {

		return new CustomLayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new CustomLayoutParams(p);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	@Override
	protected boolean checkLayoutParams(LayoutParams p) {
		return p instanceof CustomLayout3.CustomLayoutParams;
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
		int widthMode = MeasureSpec. getMode(widthMeasureSpec);
		int heightMode = MeasureSpec. getMode(heightMeasureSpec);
		int sizeWidth = MeasureSpec. getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec. getSize(heightMeasureSpec);
		int layoutWidth = 0;
		int layoutHeight = 0;
		int cWidth = 0;
		int cHeight = 0;
		int count = getChildCount();

		// 计算出所有的childView的宽和高
		for( int i = 0; i < count; i++){
			View child = getChildAt(i);
			measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
		}
		CustomLayoutParams params = null;
		if(widthMode == MeasureSpec. EXACTLY){
			//如果布局容器的宽度模式时确定的（具体的size或者match_parent）
			layoutWidth = sizeWidth;
		} else{
			//如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
			for ( int i = 0; i < count; i++)  {
				View child = getChildAt(i);
				cWidth = child.getMeasuredWidth();
				params = (CustomLayoutParams) child.getLayoutParams();
				//获取子控件宽度和左右边距之和，作为这个控件需要占据的宽度
				int marginWidth = cWidth+params.leftMargin+params.rightMargin ;
				layoutWidth = Math.max(marginWidth, layoutWidth);
			}
		}
		//高度很宽度处理思想一样
		if(heightMode == MeasureSpec. EXACTLY){
			layoutHeight = sizeHeight;
		} else{
			for ( int i = 0; i < count; i++)  {
				View child = getChildAt(i);
				cHeight = child.getMeasuredHeight();
				params = (CustomLayoutParams) child.getLayoutParams();
				int marginHeight = cHeight+params.topMargin+params.bottomMargin ;
				layoutHeight = Math.max(marginHeight, layoutHeight);
			}
		}

		// 测量并保存layout的宽高
		setMeasuredDimension(layoutWidth, layoutHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		/**
		    layout cView by position, margin, curWidth, cMWidth  => (left_x, right_y)ordinates (range to display)

		    margin: point(x,y)
		        x + leftMargin - rightMargin
		        y + topMargin - bottomMargin
		 * */

		int cnt = getChildCount();
		int cMWidth = 0;  // child measures width
		int cMHeight = 0;

		int curWidth = 0;  // current view width
		int curHeight = 0;

		int left = 0; // ordinates left
		int top = 0;

		int position = 0;
		for(int i = 0; i< cnt; i++){
			View cView = getChildAt(i);
			cMWidth  = cView.getMeasuredWidth();
			cMHeight = cView.getMeasuredHeight();

			curWidth = getWidth();
			curHeight = getHeight();

			CustomLayoutParams lp = (CustomLayoutParams) cView.getLayoutParams();
			position = lp.position;
			switch(position){
				case CustomLayoutParams.POSITION_MIDDLE:
					left = (curWidth - cMWidth)/2 + lp.leftMargin - lp.rightMargin;
					top = (curHeight - cMHeight)/2 + lp.topMargin - lp.bottomMargin;
					break;

				case CustomLayoutParams.POSITION_LEFT_TOP:
					left = lp.leftMargin;
					top = lp.topMargin;
					break;

				case CustomLayoutParams.POSITION_LEFT_BOTTOM:
					left = lp.leftMargin;
					top = curHeight-cMHeight-lp.bottomMargin;
					break;

				case CustomLayoutParams.POSITION_RIGHT_TOP:
					left = curWidth - cMWidth - lp.rightMargin;
					top = lp.topMargin;
					break;
				case CustomLayoutParams.POSITION_RIGHT_BOTTOM:
					left = curWidth - cMWidth -lp.rightMargin;
					top = curHeight -cMHeight -lp.bottomMargin;
					break;
			}
			cView.layout(left, top, left+ cMWidth, top + cMHeight);
		}
	}
}



