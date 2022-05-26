package com.eg.rxjavatst;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

class TstInterval {
	private static final String TAG = "TstTimer";
	void tst(){
		Log.d(TAG, "tst: inteval start " + new Date());
		Observable.interval(5, TimeUnit.SECONDS).subscribe(aLong ->{
			Log.d(TAG, "tst: interval " + new Date());
		});
	}
}
