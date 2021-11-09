package com.eg.motioneg;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
retrieve: bindingView; butterKnife;
reqs: move a icon on parentView, couldn't over the parentView;
flow:
	1. imageView(customer) lsr.onTouch return true;
	2. action = down
        a. get lastX, lastY (compare to parentVeiw);
		b. get initial imageView Left, Top, Right, Bottom;
	        lastL  = imageView.getLeft();
	        lastT = imageView.getTop();
	        lastR = imageView.getRight();
	        lastB = imageView.getBottom();
        c. get pareView maxR, maxB;
	3. action = move
        a. get evX, evY;
	    b. dX = evX - lastX;  dY = evY - lastY;  V1 + dV = V2
        c.
		   lastL = lastL + dX;   if(lastL < 0) {lastR = lastR + (0 - lastL); lastL = lastL + (0 -lastL); }  // V1 - V0 = dV  V1(final state) = V0 + dV
		   lastT = lastT + dY;   if(lastT < 0) ...

		   lastR = lastR + dX;   if(lastR > pR){ lastL = LastL + (pR -lastR); lastR = lastR + (pR - lastR);} // pR (final state) =
		   lastB = lastB + dY;
 coding:

 * */

public class MainActivity extends Activity implements OnTouchListener {

	private ImageView iv_main;
	private RelativeLayout parentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv_main = (ImageView) findViewById(R.id.iv_main);
		
		parentView = (RelativeLayout) iv_main.getParent();

		/**
		 after onResume -> onAttachToWindow, will do onMeasure onLayout, so onCreate couldn't get the size(height, width) of View

			int right = parentView.getRight(); //0
			int bottom = parentView.getBottom();   //0
			Toast.makeText(this, right+"---"+bottom, 1).show();
		*/

		//设置touch监听 
		iv_main.setOnTouchListener(this);
	}
	
	private int lastX;
	private int lastY;
	private int maxRight;
	private int maxBottom;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//得到事件的坐标
		int eventX = (int) event.getRawX();
		int eventY = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			//得到父视图的right/bottom
			if(maxRight==0) {//保证只赋一次值
				maxRight = parentView.getRight();
				maxBottom = parentView.getBottom();
			}

			//第一次记录lastX/lastY
			lastX =eventX;
			lastY = eventY;
			break;
		case MotionEvent.ACTION_MOVE:
			//计算事件的偏移
			int dx = eventX-lastX;
			int dy = eventY-lastY;
			//根据事件的偏移来移动imageView
			int left = iv_main.getLeft()+dx;
			int top = iv_main.getTop()+dy;
			int right = iv_main.getRight()+dx;
			int bottom = iv_main.getBottom()+dy;
			
			//限制left  >=0
			if(left<0) {
				right += (0 -left); // V1 = V0 + dV;
				left = 0;
			}
			//限制top
			if(top<0) {
				bottom += -top;
				top = 0;
			}
			//限制right <=maxRight
			if(right>maxRight) {
				left -= right-maxRight;
				right = maxRight;
			}
			//限制bottom <=maxBottom
			if(bottom>maxBottom) {
				top -= bottom-maxBottom;
				bottom = maxBottom;
			}
			
			iv_main.layout(left, top, right, bottom);
			//再次记录lastX/lastY
			lastX = eventX;
			lastY = eventY;
			break;

		default:
			break;
		}
		return true;//所有的motionEvent都交给imageView处理
	}
}
