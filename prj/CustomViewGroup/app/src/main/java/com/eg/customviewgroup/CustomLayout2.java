package com.eg.customviewgroup;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 reqs&Qs:
    1.CustomLayout(viewGroup)容纳所有子View(支持wrap_content);
    2.自定义Layout_params, layout_position: 支持左上,左下，右上，右下，居中；

 src: https://openxu.blog.csdn.net/article/details/51500304


 Qs:
 1: viewGroupWidth < viewWidth & viewWidth < onDrawRectWidth ?
 A: viewWidth is valid, onDraw canvas will refer to viewWidth for coordinates, but the display range be determined by viewGroup.

 2: viewWidth < viewGroupWidth & viewWidth < onDrawRectWidth ?
 A:  the display range be determined by viewWidth.

 3: draw View background color ?
 A:

 main_line: flow and use API(input and output, function), ignore src code (API realize)
  1. reqs;
  2. apply firstly;

 Q: onMeasure(widthMeasureSpec, heightMeasureSpec), widthMeasureSpec ?
 A: widthMeasureSpec = getChildMeasureSpec(parentMeasureSpec, padding, childLp.width)  // availableSize = parentSize - padding

    pMode  = EXACTLY, AT_MOST, UNSPECIFIED
    pSize
    chileLp.width = cSize, match_parent, wrap_content

 Q: getChildMeasureSpec ? cLp.width (developer reqs)firstly:
	1. cLp.width = cSize  => mode = EXACTLY, size = cSize
	2. p.mode = UNSPECIFIED  => mode = UNSPECIFIED, size = 0
	3. p.mode = AT_MOST || cLp.width = wrap_content  => mode = AT_MOST, size = pSize
        a. pMode = wrap_content & cLp.width = match_parent  => mode = AT_MOST, size = pSize

 */

/**
 *  output:
 *  flow & API (input & output)
 *  onMeasure(wMS, hMS){ // MSpec
 *      super.onMeasure()  AT_MOST, EXACTLY size = pSize
 *
 *      setMeasuredDimension(wMS, hMS);
 *  }


 1. container childView;  cWidth > pSize
 2. v1 ->v2 ->v3

 //define
 attrs.xml
 <declare-styleable name = "CustomerLayout2">  // add to array
    <attr name = "position">
        <enmu name = "center" value = "0"/>
        <enmu name = "left" value = "1" />
    </attr>
 </declare-styleable>

 // use
 <CustomerLayout2
    xmlns: app = "http://schemas.android.com/res-auto"
    layout_width = "wrap_content"
    layout_height = "wrap_content"
 >
    <Button
		app:position = "center"
    />
 </CustomerLayout2>

 CustomerLayout2
    1.wrap_content; match_parent / size  needn't override onMeasure AT_MOST, EXACTLY, pSize =
    onMeasure(wMS, hMS){
        measureChildren();
//        super.onMeasure();

        pMode = AT_MOST  => gLp = AT_MOST &&  pLp = match_parent / wrap_content
		gLp = AT_MOST && pLp = match_parent => AT_MOST pSize

        if(ppWidthMode == EXACTLY){
			widt
        }


  // 1. get max width of child;
 // 1. cnt = getChildCount()
 for(i = 0, i < cnt; i++){
    View child = getChildAt(i);
    int cWidth = child.getMeasuredWidth();
    int widthSize = Math.max(widthSize , cWidth);
 }

 // 2. get max height of parent



 }
    2.position;

* */
public class CustomLayout2 extends ViewGroup {
	private static final String TAG = "CustomerLayout2";
	public CustomLayout2(Context context) {
		super(context);
	}

	public CustomLayout2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLayout2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**  1.rootView CustomerLayoutParams extends LayoutParams/ MarginLayoutParams */
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

		public CustomLayoutParams(ViewGroup.LayoutParams source) {
			super(source);
		}

	}


	/**
	 2.onLayout: lp = (CustomLayoutParams)cView.getLayoutParams() ?
	    -> cLp = root.generateLayoutParams(cAttrs) //  attrs get from cView
	    -> if(!attachToRoot) ->cView.setLayoutParams(cLp)
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new CustomLayoutParams(getContext(), attrs);
	}

	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {
		//获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int pWidthMode = MeasureSpec. getMode(widthMeasureSpec);
		int pHeighMode = MeasureSpec. getMode(heightMeasureSpec);

		int pWidthSize = MeasureSpec. getSize(widthMeasureSpec);
		int pHeightSize = MeasureSpec. getSize(heightMeasureSpec);

		int widthSize = 0;
		int heightSize = 0;

		Log.d(TAG, "onMeasure: widthMS(parent) = " + MeasureSpec.toString(widthMeasureSpec));
		Log.d(TAG, "onMeasure: heightMS(parent) = " + MeasureSpec.toString(heightMeasureSpec));

		// measureChildren 1. 计算出所有的childView的宽和高
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int cWidth = 0;
		int cHeight = 0;
		int count = getChildCount();

		// get width: pWidthSize / max cWidthSize
		if(pWidthMode == MeasureSpec.EXACTLY){
			//如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
			widthSize = pWidthSize;
			pWidthMode = MeasureSpec.EXACTLY;
		} else{
			//如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
			for ( int i = 0; i < count; i++)  {
				View child = getChildAt(i);
				cWidth = child.getMeasuredWidth();
				//获取子控件最大宽度
				widthSize = Math.max(cWidth, widthSize);
			}
			pWidthMode = MeasureSpec.AT_MOST;
		}

		//高度很宽度处理思想一样
		if(pHeighMode == MeasureSpec.EXACTLY){
			heightSize = pHeightSize;
			pHeighMode = MeasureSpec.EXACTLY;
		} else{
			for ( int i = 0; i < count; i++)  {
				View child = getChildAt(i);
				cHeight = child.getMeasuredHeight();
				heightSize = Math.max(cHeight, heightSize);
			}
			pHeighMode = MeasureSpec.AT_MOST;
		}

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, pWidthMode);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, pHeighMode);

		Log.d(TAG, "onMeasure: widthMS(curView) = " + MeasureSpec.toString(widthMeasureSpec));
		Log.d(TAG, "onMeasure: heightMS(curView) = " + MeasureSpec.toString(heightMeasureSpec));
		// 测量并保存layout的宽高
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	Qs:
		1. container (self) Width/height  ?
			getWidth()

		2. childWidth/height ?
			child.getMeasuredWidth()

		3. child.Layout(ltrb) ?
	        relative position

	    4. customParams  layout_position ?
	        a. extends viewGroup.ParamsLayout override constructor(attrSet) get attr by TypedArray
	        b. override generateParamsLayout return customLayoutParams
	        c. ViewGroup.LayoutParams Lp = (CustomLayoutParams)child.getLayoutParams()  // get => setLayoutParams
	* */

	/**
	    onLayout:
	        1. width, cWidth, lp.position;
	        2. child.layout(lt, rb) // lt, rb

	        pos = "center"

	 */
	@Override
	protected void onLayout( boolean changed, int left, int top, int right, int bottom) {

		final int count = getChildCount();
		int childMeasureWidth = 0;
		int childMeasureHeight = 0;
		CustomLayoutParams params = null;
		for ( int i = 0; i < count; i++) {
			View child = getChildAt(i);
			// 注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
			childMeasureWidth = child.getMeasuredWidth();
			childMeasureHeight = child.getMeasuredHeight();

			params = (CustomLayoutParams) child.getLayoutParams();
			switch (params.position) {
				case CustomLayoutParams.POSITION_MIDDLE:    // 中间
					left = (getWidth()-childMeasureWidth)/2;
					top = (getHeight()-childMeasureHeight)/2;
					break;
				case CustomLayoutParams.POSITION_LEFT_TOP:      // 左上方
					left = 0;
					top = 0;
					break;
				case CustomLayoutParams.POSITION_RIGHT_TOP:     // 右上方
					left = getWidth()-childMeasureWidth; //200 -300
					top = 0;
					break;
				case CustomLayoutParams.POSITION_LEFT_BOTTOM:    // 左下角
					left = 0;
					top = getHeight()-childMeasureHeight;
					break;
				case CustomLayoutParams.POSITION_RIGHT_BOTTOM:// 右下角
					left = getWidth()-childMeasureWidth;
					top = getHeight()-childMeasureHeight;
					break;
				default:
					break;
			}

			// 确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
			/** c.layout and p.layout will determine display range of view */
			child.layout(left, top, left+childMeasureWidth + 30 , top+ childMeasureHeight + 30);
			child.layout(left, top, left+childMeasureWidth + 30 , top+ childMeasureHeight + 30);
		}
	}

}



