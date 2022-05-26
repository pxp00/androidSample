package scut.carson_ho.valueanimator_ofobject;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    MyView2 myView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
            reqs: circle  blue => red
            anim = ObjectAnimator.ofObject(view, "propX", propXEvaluator, objStart, objEnd)
            anim.start();
         */

        /**
             0. ofObject(viewX, "propX", evaluator, objStrat, objEnd)
             1.evalute(frac, objStart, objEnd) => objRet
             2.->viewX.setPropX(objRet) ->mPaint, invalidate() -> onDraw()
         */

        myView2 = (MyView2) findViewById(R.id.MyView2);
        ObjectAnimator anim = ObjectAnimator.ofObject(myView2, "propX", new ColorEvaluator(), "#0000FF", "#FF0000");
        anim.setDuration(8000);
        anim.start();
    }
}
