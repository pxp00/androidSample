package com.eg.rxjavatst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/*
	Qs:
		1. create
		2. timer // TimeCounter
		3. interval

		1. map
		2. download all param together  -> update UI  // zip
		3. register ->login -> update UI  // flatMap

		1. schedule threadX;  // subscribeOn, observeOn
		2. Map
		3. just

	observable.subscribe(observer)
* */

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new TstCreate().tst();
	}
}