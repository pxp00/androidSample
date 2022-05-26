package com.eg.rxjavatst;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

class TstJust {
	private static final String TAG = "Tst";
	void tst(){
		//建立连接
		observable.subscribe(observer);
	}

	// new LsrX(){ onCb(varX){}} => varX ->{}

	//创建一个上游 Observable：
	Observable<String> observable =  Observable.just(1).map(new Function<Integer, String>() {
		@Override
		public String apply(Integer integer) throws Exception {
			return "" + integer;
		}
	});

	//创建一个下游 Observer
	Observer<String> observer = new Observer<String>() {
		@Override
		public void onSubscribe(Disposable d) {
			Log.d(TAG, "subscribe");
		}

		@Override
		public void onNext(String value) {
			Log.d(TAG, "" + value);
		}

		@Override
		public void onError(Throwable e) {
			Log.d(TAG, "error");
		}

		@Override
		public void onComplete() {
			Log.d(TAG, "complete");
		}
	};
}
