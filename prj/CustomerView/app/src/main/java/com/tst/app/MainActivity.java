package com.tst.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
* Q1 -> Q2
* framework -> details
* apply -> run
*
* */


public class MainActivity extends Activity {

	private int[] ids = new int[] { R.drawable.channel1, R.drawable.channel2, R.drawable.channel3, R.drawable.channel4, R.drawable.channel5 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ImageView imageView = (ImageView) findViewById(R.id.imgView);

		/*BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.channel1, opts);
		opts.inSampleSize = computeSampleSize(opts, -1, 500 * 500);
		opts.inJustDecodeBounds = false;

		LevelListDrawable levelListDrawable = new LevelListDrawable();//定义一个LevelDrawable
		try {
			for (int i = 0; i < ids.length; i++) {//for循环，加载5个drawable资源
				Bitmap bmp = BitmapFactory.decodeResource(getResources(),ids[i], opts);
				BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bmp);
				levelListDrawable.addLevel(i, i+1, bitmapDrawable);//添加到LevelListDrawable
			}
			imageView.setImageDrawable(levelListDrawable);//设置
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
*/
		imageView.setImageLevel(1);//默认的level为0，将到设置为1

		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int i = imageView.getDrawable().getLevel();
				if (i >=5)
					i = 0;
//                imageView.setImageLevel(++i);//改变level
				imageView.getDrawable().setLevel(++i); //能达到同样的效果
			}
		});


	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h
				/ maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}


	/*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	void tst (){
		String pathName = "/storage/emulated/0/test1.jpg";
		//width: 263,ImageView实际比较小
		int width = mIv.getWidth();
		int height = mIv.getHeight();

		Log.d(TAG, "imageView width: "+width);

		//不采样时图片的大小:9216000
		Bitmap bitmap = BitmapFactory.decodeFile(pathName);
		Log.d(TAG, "不二次采样bitmap大小: "+bitmap.getAllocationByteCount());

		BitmapFactory.Options options = new BitmapFactory.Options();
		//设置为true,加载图片时不会获取到bitmap对象,但是可以拿到图片的宽高
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName,options);

		//计算采样率,对图片进行相应的缩放
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		Log.d(TAG, "outWidth: "+outWidth+",outHeight:"+outHeight);

		float widthRatio = outWidth*1.0f/width;
		float heightRatio = outHeight*1.0f/height;
		Log.d(TAG, "widthRatio: "+widthRatio+",heightRatio:"+heightRatio);

		float max = Math.max(widthRatio, heightRatio);

		//向上舍入
		int inSampleSize = (int) Math.ceil(max);
		Log.d(TAG, "inSampleSize: "+inSampleSize);

		//改为false,因为要获取采样后的图片了
		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;
		Bitmap bitmap1 = BitmapFactory.decodeFile(pathName, options);
		//采样后图片大小:144000,是采样前图片的inSampleSize*inSampleSize分之一(1/64)
		Log.d(TAG, "二次采样bitmap大小: "+bitmap1.getAllocationByteCount());
		mIv.setImageBitmap(bitmap1);
	}*/


}