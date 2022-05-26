package com.tst.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/** flow:

server:
	 1. declare IAidlX File // generate IAidlX.Stub Interface
	 2. impl IAidlX.Stub
	 2. extends Service -> IBinder onBind() return IAidl.Stub

Note: service need register on manifest file only, needn't launcher screen as a driver


client:
	1. copy & paste IAidlX folder
	2. bindService(intent, conn, BIND_CREATE_AUTO);
	3. onServiceConnected(IBinder service) -> IAildX objX= IAidlX.Stub.asInterface(IBinder service)
	4. use objX

fileName: XActivity
folderName:  ActivityX
* */


/**
	reqs: server plus and  toUpperCase;
 */

public class MyService extends Service {
	private static final String TAG = "MyService";
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

		@Override
		public String toUpperCase(String str) throws RemoteException {
			Log.d(TAG, "toUpperCase: input str = " + str);
			if (str != null) {
				return str.toUpperCase();
			}
			return null;
		}

		@Override
		public int plus(int a, int b) throws RemoteException {
			Log.d(TAG, "plus: " + a + "+" + b + "= " + (a + b));
			return a + b;
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate: server");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy: server");
	}
}