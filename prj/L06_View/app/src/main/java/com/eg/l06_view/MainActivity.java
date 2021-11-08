
package com.eg.l06_view;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
*   setContentView(layoutId); // contentView is a FrameLayout
 *  setContentView(view)
 *
 *  view = View.inflate(this, layoutId, null);
* */


public class MainActivity extends Activity {

	private  MyTextView myTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
			setContentView(R.layout.activity_main);  // layout
		*/

		/*
			MyTextView textView = new MyTextView(this);
			textView.setText("www.atguigu.com");
			setContentView(textView);  // view
		*/


		LinearLayout layout = (LinearLayout) View.inflate(this, R.layout.activity_main, null);
		//根据下标得到子View
		myTextView = (MyTextView) layout.getChildAt(0);
		setContentView(layout); // view
	}
	
	@Override
	protected void onResume() {
		Log.i("TAG", "onResume()");
		super.onResume();
	}
	
	//强制重新布局
	public void forceLayout(View v) {
		myTextView.requestLayout();
		Toast.makeText(this, "强制重新布局", Toast.LENGTH_LONG).show();
	}
}
