package com.tst.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	/*
	 score board:
	      1. display score;
	      2. button add score;
	      3. clear score
	* */

	private MutableLiveData<String> mLiveData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//liveData基本使用
		mLiveData = new MutableLiveData<>();
		mLiveData.observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				Log.i(TAG, "onChanged: "+s);
			}
		});
		Log.i(TAG, "onCreate: ");
		mLiveData.setValue("onCreate");//activity是非活跃状态，不会回调onChanged。变为活跃时，value被onStart中的value覆盖
	}


	// 1. container
	// 2. container.obserber(onChange())

	// 3. constainer.setValue(); // -> onChange() // callback

}