package com.eg.customview;


import android.content.Context;
import android.util.DisplayMetrics;

class Utils {

	public static int dip2px(Context context,float dpValue) {
		final float scale  = context.getResources().getDisplayMetrics().density;  // dp/160 * dpi= px => dpi/160 = density
		return (int)(dpValue*scale+0.5f);  //dp * density + 0.5f = px
	}

	public static int px2dip(Context context,float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return(int)(pxValue/scale+0.5f);
	}
}
