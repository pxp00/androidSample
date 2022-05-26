package scut.carson_ho.valueanimator_ofobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Carson_Ho on 17/4/18.
 */
public class MyView2 extends View {
    // 设置需要用到的变量
    public static final float RADIUS = 500f;// 圆的半径 = 100
    private Paint mPaint;// 绘图画笔
    private String color;
    // 设置背景颜色属性


    // 设置背景颜色的get() & set()方法   // propX -> getPropX
    public String getPropX() {
        return color;
    }
    public void setPropX(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        // 将画笔的颜色设置成方法参数传入的颜色
        invalidate();  //  invoke onDraw
        // 调用了invalidate()方法,即画笔颜色每次改变都会刷新视图，然后调用onDraw()方法重新绘制圆
        // 而因为每次调用onDraw()方法时画笔的颜色都会改变,所以圆的颜色也会改变
    }


    // 构造方法(初始化画笔)
    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    // 复写onDraw()从而实现绘制逻辑
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(500, 500, RADIUS, mPaint);
    }
}