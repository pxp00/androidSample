package com.tst.youkumenu;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/*
* Efficience:
*   1. run the prj(copy code)
*   2. split the function; // add comment
*   3. create a new app write keypoint by yourself; // framework -> keypoint
*   4. record to git;
*
* */

/**
 * reqs:
 *   1. keycode_menu, hide level 1,2,3 or show level1,2;
 *   2. press home hide level2 & level3  or show level2;
 *   3. press menu hide level3 or show level3;
 *
 *   keycode_menu:
 *   if(isLevel1Show){
 *       hideAll();
 *       isLevel1Show = false;
 *   }else{
 *      showLevel12()
 *      isLevel1Show = true;
 *   }
 *
 *  home:
 *  if(isLevel2Show){
 *      hideLevel23()
 *      isLeve2Show = false;
 *  }else{
 *      showLevel2()
 *      isLevel2Show = true;
 *  }
 *
 *  menu:
 *  if(isLevel3Show){
 *      hideLeve3()
 *      islevel3Show = false;
 *  }else{
 *      showLevel3()
 *      isLevel3Show = true
 *  }
 *
 * */


public class MainActivity extends AppCompatActivity {
    private ImageView icon_home;
    private ImageView icon_menu;
    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;

    /**
     * 是否显示第三圆环
     * true:显示
     * false隐藏
     */
    private boolean isShowLevel3 = true;

    /**
     * 是否显示第二圆环
     * true:显示
     * false隐藏
     */
    private boolean isShowLevel2 = true;


    /**
     * 是否显示第一圆环
     * true:显示
     * false隐藏
     */
    private boolean isShowLevel1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon_home = (ImageView) findViewById(R.id.icon_home);
        icon_menu = (ImageView) findViewById(R.id.icon_menu);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);

        MyOnClickListener myOnClickListener = new MyOnClickListener();
        //设置点击事件
        icon_home.setOnClickListener(myOnClickListener);
        icon_menu.setOnClickListener(myOnClickListener);

        level1.setOnClickListener(myOnClickListener);
        level2.setOnClickListener(myOnClickListener);
        level3.setOnClickListener(myOnClickListener);
    }


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.level1:
                    Toast.makeText(MainActivity.this, "level1", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.level2:
                    Toast.makeText(MainActivity.this, "level2", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.level3:
                    Toast.makeText(MainActivity.this, "level3", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.icon_home://home
                    //如果三级菜单和二级菜单是显示，都设置隐藏
                    if (isShowLevel2) {
                        //隐藏二级菜单
                        isShowLevel2 = false;
                        Tools.hideView(level2);

                        if (isShowLevel3) {
                            //隐藏三级菜单
                            isShowLevel3 = false;
                           Tools.hideView(level3, 200);
                        }
                    } else {
                        //如果都是隐藏的，二级菜单显示
                        //显示二级菜单
                        isShowLevel2 = true;
                        Tools.showView(level2);
                    }
                    break;

                case R.id.icon_menu://菜单
                    if (isShowLevel3) {
                        //隐藏
                        isShowLevel3 = false;
                        Tools.hideView(level3);
                    } else {
                        //显示
                        isShowLevel3 = true;
                        Tools.showView(level3);
                    }
                    break;
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){

            //如果一级，二级，三级菜单是显示的就全部隐藏
            if(isShowLevel1){
                isShowLevel1 = false;
                Tools.hideView(level1);
                if(isShowLevel2){
                    //隐藏二级菜单
                    isShowLevel2 = false;
                    Tools.hideView(level2,200);
                    if(isShowLevel3){
                        //隐藏三级菜单
                        isShowLevel3 = false;
                        Tools.hideView(level3,400);
                    }
                }
            }else{
                //如果一级，二级菜单隐藏，就显示
                //显示一级菜单
                isShowLevel1 = true;
               Tools.showView(level1);

                //显示二级菜单
                isShowLevel2 = true;
                Tools.showView(level2,200);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
