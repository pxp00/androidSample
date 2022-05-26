package com.eg.rxjavatst;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

class TstTimer {
	private static final String TAG = "TstTimer";
	void tst(){
		Log.d(TAG, "tst: timer start " + new Date());
		Observable.timer(5, TimeUnit.SECONDS).subscribe(aLong ->{
			Log.d(TAG, "tst: timer.stop " + new Date());
		});
	}
}
