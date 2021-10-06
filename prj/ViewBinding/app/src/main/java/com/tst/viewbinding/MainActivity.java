package com.tst.viewbinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tst.viewbinding.databinding.ActivityMainBinding;

import java.lang.ref.WeakReference;




// MainActivity.java
public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding binding;  // activity_main.xml  => ActivityMainBinding(cls)
//	Button button;
	private Handler handler = new SHandler(this);


	static class SHandler extends Handler{
		WeakReference<MainActivity> activityWeakReference  = null;
		SHandler(MainActivity activity){
			activityWeakReference = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(@NonNull Message msg) {
			super.handleMessage(msg);

			if(null != activityWeakReference.get()){
				activityWeakReference.get().binding.name.setText("hugo great !");
			}
		}
	}


	/*
		Thread, ThreadLocal, ThreadLocalMap

		Set:
		ThreadLocal threadLocal1  = new threadLocal()
		threadLocal1.set(val);  threadLocalMap1(threadLocal.this, val);
		curThread1.threadLocals1 = threadLocalMap1;

		Get:
		threadLocal1.get();
		curThread.threadLocalMap.get(threadLocal1.this);

		sum:
			same thread, same threadLocal => get same val
				1. same  thread => thread.threadLocals same => threadLocalMap same;
				2. same  threadLocal  => map's key is same;

	* */

	/*
	T1:
		Looper.prepare1();  // nLooper1, nMsgQueue1; sThreadLocal.set(new Looper1()); -> mMsgQueue1 = new MsgQueue1();
		handler1 = new Handler(){ handleMsg(){....}} // callback mtd, sThreadLocal.get() => get mLooper1, mMsgQueue1 for handler1
		Looper.loop1();  // while(1) ->getMsg -> msg.target1.dispatchMsg(msg) -> handMsg();  // msg.target => handler1


	 T2:
	    handler1.sendMsg(new Msg()) ->handler1.enqueueMsg() -> msg.target1 = this; mQueue1.enqueueMsg()

		T1: new Handler(); => mLooper, mQueque;
	    // mQueue1 ? Looper.myLooper(); -> sThreadLocal.get() => looper1  => mQueue1

	    // Looper: static final sThreadLocal = new sThreadLocal<Looper>();
	    // Looper.prepare1(); -> sThreadLocal.set(new Looper1()) -> mQueue1 = new QueueMsg1();
	    // sThreadLocal.set(looper1) -> threadLocalMap1(sThreadLocal.this, looper1)
		// T1.threadLocals = threadLocalMap1
		// sTheadLocal().get() -> curThread.threadloacals(map) -> map.getVal(sThreadLocal.this);


	TA: Looper.prepareA(); nLooperA, nMsgQueueA; sThreadLocal.set(new LooperA()) -> mQueueA = new MsgQueueA();
		handlerA = new HandlerA(){ handleMsg(){....}} // callback mtd, get mLooperA, mMsgQueueA for handlerA

	sum:
		1. one Thread, new multiple Handlers will get same Looper and msgQueue (same thread & same threadLocal => get same val)
		2. one prepare (could prepare once only) =>  one Looper, one msgQueue();

		handler1          msg.target = handler_x         handler1.handMsg
		handler2            looper, msgQueue             handler2.handMsg
		handler3                                         handler3.handMsg
	 */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		setContentView(R.layout.activity_main);
//		button = (Button) findViewById(R.id.button);  // copy "button" -> fbc

		// 1.get bindingObj
		binding = ActivityMainBinding.inflate(getLayoutInflater()); // getObject

		// 2.rootView
		View rootView = binding.getRoot();
		setContentView(rootView);


		// 3.childView
		binding.name.setText("这是修改的");
		binding.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.e("Main","点击了按钮");
				Toast.makeText(MainActivity.this, "button be pressed", Toast.LENGTH_LONG).show();

			}
		});

		new Thread(){
			@Override
			public void run() {
				super.run();
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
}