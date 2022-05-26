package com.tst.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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
	client: main-line
		1. copy Aidl of server to client;  //Aidl -> generate Interface
		2. bindService(intent, serviceConnection, BIND_AUTO_CREATE);

	intent  = new Intent(action)
	serviceConnection(){
		onServiceConnected(compName, IBinder service){
			IAildX x = IAidlX.Stub.asInterface(service)
		}
	}
*
* */

public class MainActivity extends Activity implements View.OnClickListener{
	private static final String TAG = "MainActivity";
	private Button unbindServices ;
	private Button runAidlMtds;
	private boolean isBind =  false;

	private IMyAidlInterface myAIDLService;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected: ");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myAIDLService = IMyAidlInterface.Stub.asInterface(service);
			Log.d(TAG, "onServiceConnected: onServiceConnected");
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
				Intent intent = new Intent("com.example.servicetest.MyAIDLService");
				isBind = bindService(intent, connection, BIND_AUTO_CREATE);
				Log.d(TAG, "onClick: isBind = " + isBind);
			}
		});

		/* findViewById must be invoke after setContentView */
		unbindServices = findViewById(R.id.unbind_service);
		runAidlMtds = findViewById(R.id.run_aidl_mtds);

		unbindServices.setOnClickListener(this);
		runAidlMtds.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.unbind_service){
			if(isBind){
				unbindService(connection);
				isBind = false;
				Log.d(TAG, "onClick: unbindSucc");
			}
		}else if(id == R.id.run_aidl_mtds){
			int result = 0;
			try {
				result = myAIDLService.plus(1, 2);
				String upperStr = myAIDLService.toUpperCase("comes from ClientTest");
				Log.d(TAG, "result is " + result);
				Log.d(TAG, "upperStr is " + upperStr);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}