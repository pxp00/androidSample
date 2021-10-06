package com.tst.installapk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.BuildCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


/*
	thinking nothing, target only
* 1. before reinstall, onDestroy will be invoke ?
*
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
	private static final String TAG = "MainActivity";
	Button btInstallApk;
	Button btUnlockScreen;
	Button btLockScreen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate: 1" );
		setContentView(R.layout.activity_main);
		btInstallApk= findViewById(R.id.bt_installApk);
		btInstallApk.setOnClickListener(this);

		btLockScreen= findViewById(R.id.bt_lockScreen);
		btLockScreen.setOnClickListener(this);

		btUnlockScreen= findViewById(R.id.bt_unlockScreen);
		btUnlockScreen.setOnClickListener(this);
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.bt_installApk:
				installApk();
				break;

			case R.id.bt_lockScreen:
				startLockTask(this);
				break;

			case R.id.bt_unlockScreen:
				stopLockTask(this);
				break;

			default:
				break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart: 1 " );
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume: 1");
	}



	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause: 1" );
	}


	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop: 1" );
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy: 1");
	}

	/**
	 * 安装 apk 文件
	 *
	 * @param apkFile
	 */
	public void installApk() {

		//
		File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"bindo_mpos_1258.apk");

		Log.e(TAG, "installApk: fileExist = " + file.exists() );

		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			Log.e(TAG, "installApk: SDK_INT = " + Build.VERSION.SDK_INT );
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			Log.e(TAG, "installApk: filePath = " + file.getAbsolutePath() );
			Uri contentUri = FileProvider.getUriForFile(this,  BuildConfig.APPLICATION_ID + ".fileprovider", file);  // dir: path -> name
			Log.e(TAG, "installApk: contentUri = " + contentUri.getAuthority() + ", " + contentUri.getPath() );
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

		} else {
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}

		if (this.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
			Log.e(TAG, "installApk: startActy" );
			this.startActivity(intent);
		}
	}

	/**
	 *  1. App start pinned screen ->  MDM autoInstall App -> App start automatically ?
	 *      a. install MDM;
	 *      b. install 1258;
	 *      c. put 1261 to MDM, set time;
	 *
	 *  2. exit mPOS -> MDM download -> run mPOS
	 *     a.i
	 *  3. mPOS install -> unknown source -> app not installed
	 *
	* */

	/*
		MDM interval 15 minutes get info;
		MDM
	* */

	/**
	 * 进入LockTask模式
	 */
	public void startLockTask(Activity activity) {
		if (!this.isInLockTask(activity)) {
			if (Build.MODEL.equals("rk3288")) {
				activity.sendBroadcast(new Intent("com.android.systembar.hide")); // 隐藏菜单栏, 仅适用于rk3288
			} else if (Build.MODEL.equals("ZC-328")) {
				activity.sendBroadcast(new Intent("hide.systemui")); // 隐藏菜单栏, 仅适用于ZC-328
			} else {
				try {
					if (activity != null) {

						// packageName
						// 进入LockTask模式
						DevicePolicyManager dpm = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
						ComponentName cn = new ComponentName(activity, AppAdminReceiver.class);
						Log.e(TAG, "startLockTask: isDeviceOwnerApp = " + dpm.isDeviceOwnerApp(activity.getPackageName()));  // false
						if (dpm.isDeviceOwnerApp(activity.getPackageName())) {
							String[] packages = {activity.getPackageName()};
							dpm.setLockTaskPackages(cn, packages);
							activity.startLockTask();
						} else {
							activity.startLockTask();
						}
					}
				} catch (Exception e) {
					Log.i("111", e.toString());
				}
			}
		}
	}

	/**
	 * 退出LockTask模式
	 */
	public void stopLockTask(Activity activity) {
		if (this.isInLockTask(activity)) {
			if (Build.MODEL.equals("rk3288")) {
				activity.sendBroadcast(new Intent("com.android.systembar.show")); // 显示菜单栏, 仅适用于rk3288
			} else if (Build.MODEL.equals("ZC-328")) {
				activity.sendBroadcast(new Intent("show.systemui")); // 隐藏菜单栏, 仅适用于ZC-328
			} else {
				try {
					// 退出LockTask模式
					if (activity != null) {
						activity.stopLockTask();
					}
				} catch (Exception e) {

				}
			}
		}
	}

	public boolean isInLockTask(Activity activity) {
		if (this.getLockTaskModeState(activity) == 1 || this.getLockTaskModeState(activity) == 2) {
			return true;
		}
		return false;
	}



	public int getLockTaskModeState(Activity activity) {
		ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		int inLockTaskMode = 0;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			inLockTaskMode = am.isInLockTaskMode() ? 1 : 0;
		} else {
			inLockTaskMode = am.getLockTaskModeState();
		}
		return inLockTaskMode;
	}
}
