package com.eg.customviewgroup;

import android.content.Context;

/*
   160dp = 1 inch
   320px = 1 inch
* */
final public class Utils {

   public static int dp2px(Context context, int dp){
      float density =  context.getResources().getDisplayMetrics().density;
      return(int) (density*dp + 0.5f);
   }
}
