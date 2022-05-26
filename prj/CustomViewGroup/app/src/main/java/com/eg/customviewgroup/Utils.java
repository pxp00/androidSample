package com.eg.customviewgroup;

import android.content.Context;

final public class Utils {

   public static int dp2px(Context context, int dp){
      float density =  context.getResources().getDisplayMetrics().density;
      return(int) (density*dp + 0.5f);
   }
}
