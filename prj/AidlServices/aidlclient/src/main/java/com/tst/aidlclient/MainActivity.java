package com.tst.aidlclient;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tst.aidlserver.IMyAidlInterface;

/*
client exist aidl file exist mtd but server without this mtd ?

insert into studs (name, age) values ("hugo", 19)
select name, age from studs

delete from studs where name = "hugo"

update studs set name = "proto" where name = "hugo"


INSERT INTO Websites (name, url, alexa, country)
VALUES ('百度','https://www.baidu.com/','4','CN');

DELETE FROM Websites
WHERE name='Facebook' AND country='USA';

UPDATE Websites
SET alexa='5000', country='USA'
WHERE name='菜鸟教程';

SELECT name,country FROM Websites;
* */

/*
record:
	1. polymorphism; IAidlX.Stub extends Binder impl IAidlX;
	2. hide icon ?
	3. aidl
		a. in,out tag ?
		b. mtd, dataStruct ? parcel
* */


/*
t0, Iget ? ai ? once only, curTaskOnly(asapS), openBrave
out/retrieve: apply(rqs & flow)

rqs:
	client -> 3 =  server.plus(1,2)  // i/o, mtdName

flow/steps:
	server:
		1. aidlClsX [as .h file, declare API]a: pkgX.IAidlX.mtdX  // interface IAidlX{plus()}  // android interface define language(i/o, mtdName);
		2. server extends service onBinder(){return new IAidlX.Stub(){ plus}};
		3. register on Manifest serverAction


	client:
		1. aidlClsX copy from server[as .h file, interface]: pkgX.IAidlX.mtdX
		2. ret = bindService(serverIntent, ServiceConn, bind_auto_create);
			a.serverIntent = new Intent(serverAction);
			b.ServiceConn = new ServiceConnection(){
				onServiceConnected(cmp, service){
					IAidlX server = IAidlX.Stub.asInterface(service);  // IBinder => service: p => c // IAidlX.Stub extend Binder impl IAidlX
				}
			}
* */

/*
tzu, Iget? hS&abS

eg&onion:
	1. apply firstly, proficient on flow & steps; knowledge can;
	2. kp
	3. analysis

	rqs&Q: client -> server.plus(a, b);

	flow&steps:
		server:  1.declare 2.define/impl 3.register
			1. interfaceX.aidl // declare
				int plus(int a, int b);

			2. server impl service  // define, impl
				.onBind() return impl interfaceX.stub[IBind, interfaceX]

			3. manifest server  // register, action

		client: 1.declare; 2.bindService; 3.interfaceXObj;
			1. serverI.aidl  // pkg + cls
			2. bindService(intent, serviceConn(service))
			3. interfaceX.stub.asInterface(service)  // objX

		interface.stub[Binder]
		interfaceX.stub.asInterface

kp1:
interfaceX.stub [IBinder, interfaceX]
	a.IBinder onBind()
	b.onServiceConnected(IBinder service)

	1. mBinder =  new interfaceX.stub(){}
	2. interfaceX x =  interfaceX.stub.asInterface(mBinder);

kp2:
	unbind check

kp3:
	bindService by mtd


* */

public class MainActivity extends Activity implements View.OnClickListener{
	private static final String TAG = "MainActivity";
	private Button unbindServices ;
	private Button runAidlMtds;
	private boolean isBind =  false;

	private IMyAidlInterface myAIDLService;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected: ");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myAIDLService = IMyAidlInterface.Stub.asInterface(service);
			Log.d(TAG, "onServiceConnected: cmpName = "+ name); //{com.tst.aidlserver/com.tst.aidlserver.MyService}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bindService = findViewById(R.id.bind_service);
		bindService.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: bindServices");
				Intent intent = new Intent("com.example.servicetest.MyAIDLService"); // server.action
				intent.setPackage("com.tst.aidlserver");  // pkg
				isBind = bindService(intent, serviceConnection, BIND_AUTO_CREATE);
				Log.d(TAG, "onClick: isBind = " + isBind);
			}
		});

		/* findViewById must be invoke after setContentView */
		unbindServices = findViewById(R.id.unbind_service);
		runAidlMtds = findViewById(R.id.run_aidl_mtds);

		unbindServices.setOnClickListener(this);
		runAidlMtds.setOnClickListener(this);
	}

	// onion

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.unbind_service){
			if(isBind){
				unbindService(serviceConnection);
				isBind = false;
				Log.d(TAG, "onClick: unbindSucc");
			}
		}else if(id == R.id.run_aidl_mtds){
			int result = 0;
			try {
				result = myAIDLService.plus(1, 2);
				String upperStr = myAIDLService.toUpperCase("comes from ClientTest");
				Log.d(TAG, "result = " + result);
				Log.d(TAG, "upperStr = " + upperStr);

				/*
					0.invoke a method exist on client only ?
						a. client without exception, could return normally;
						b. server without exception;
				* */
//				Log.e(TAG, "onClick: bf invoke clientExistOnly()");
//				String ret =  myAIDLService.clientExistedOnly();
//				Log.e(TAG, "onClick: aft  clientExistOnly() ret = " + ret);  // clientExistOnlyRet = null

				Log.e(TAG, "onClick: bf myAIDLService.isMatched");
				boolean isMatched = myAIDLService.isMatched("clientData");
				Log.e(TAG, "onClick: aft myAIDLService.isMatched  = " + isMatched);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}