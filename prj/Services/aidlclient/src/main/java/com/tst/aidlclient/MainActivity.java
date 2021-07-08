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

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private IMyAidlInterface myAIDLService;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myAIDLService = IMyAidlInterface.Stub.asInterface(service);
			Log.d(TAG, "onServiceConnected: onServiceConnected");
			try {
				int result = myAIDLService.plus(50, 50);
				String upperStr = myAIDLService.toUpperCase("comes from ClientTest");
				Log.d("TAG", "result is " + result);
				Log.d("TAG", "upperStr is " + upperStr);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bindService = (Button) findViewById(R.id.bind_service);
		bindService.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: bindServices");
				Intent intent = new Intent("com.example.servicetest.MyAIDLService");
				bindService(intent, connection, BIND_AUTO_CREATE);
			}
		});
	}

}