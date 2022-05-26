package com.eg.customviewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

class CustomTextView extends View {
   private static final String TAG = "CustomTextView";
   /**
    * 需要绘制的文字
    */
   private String mText;
   /**
    * 文本的颜色
    */
   private int mTextColor;
   /**
    * 文本的大小
    */
   private int mTextSize;
   /**
    * 绘制时控制文本绘制的范围
    */
   private Rect mBound;
   private Paint mPaint;

   private Context mCtx;


   public CustomTextView(Context context) {
      this(context, null);
   }

   public CustomTextView(Context context, @Nullable AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      mCtx = context;

      //初始化
      mText = "Udf32f,123,456";
      mTextColor = Color.BLACK;
      mTextSize = 100;

      mPaint = new Paint();
      mPaint.setTextSize(mTextSize);
      mPaint.setColor(mTextColor);

      //获得绘制文本的宽和高
      mBound = new Rect();
      mPaint.getTextBounds(mText, 0, mText.length(), mBound);
   }


   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      Log.d(TAG, "onMeasure: widthMS = " + MeasureSpec.toString(widthMeasureSpec));
      Log.d(TAG, "onMeasure: heightMS = " + MeasureSpec.toString(heightMeasureSpec));
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//      int width = Utils.dp2px(mCtx, 300);
//      widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
//      Log.d(TAG, "onMeasure: widthMS(converd) = " + MeasureSpec.toString(widthMeasureSpec));
//      setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
   }

   @Override
   protected void onDraw(Canvas canvas) {
      Log.d(TAG, "onDraw: canvas, width = " + canvas.getWidth() + ", height = " + canvas.getHeight());
      
      //绘制文字
      Log.d(TAG, "onDraw: mBound, height = " + mBound.height() + ", width = " + mBound.width());
      canvas.drawText(mText, 0, mBound.height(), mPaint);
   }
}
