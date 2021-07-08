package com.tst.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
/*
server:
	1. new an aidl Interface;  // define a Interface
	2. impl the interface;
	3. new the obj and return obj by onBind;

Note: service need register on manifest file only;

client:
	1. copy & paste aidl folder
	2. bindService;
	3. get the obj by conn;
	4. use obj;

fileName: XxxActivity
folderName:  ActivityXxx
* */


public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

		@Override
		public String toUpperCase(String str) throws RemoteException {
			if (str != null) {
				return str.toUpperCase();
			}
			return null;
		}

		@Override
		public int plus(int a, int b) throws RemoteException {
			return a + b;
		}
	};

}