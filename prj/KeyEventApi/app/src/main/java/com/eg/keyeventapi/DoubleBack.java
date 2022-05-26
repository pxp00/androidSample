package com.eg.keyeventapi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;


public class DoubleBack extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_double_back);
	}

	private static  boolean exit = false;//标识是否可以退出
	static class MyHandler extends Handler{ // static Cls won't hold the ref of outerCls
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1) {
				exit = false;
			}
		}
	}
	Handler handler = new MyHandler();

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK) {
			if(!exit) {
				exit = true;
				Toast.makeText(this, "再按一次就退出应用", Toast.LENGTH_LONG).show();

				// setTimeout(()=>{exit = false }, 2*1000);
				//发消息延迟2s将exit=false
				handler.sendEmptyMessageDelayed(1, 2000);

				return true;//不退出
			}
		}
		return super.onKeyUp(keyCode, event);
	}
}