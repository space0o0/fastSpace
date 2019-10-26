package space.learning.myui.hw2.Q1;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import space.learning.myui.R;

public class HW2Ans1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw2_q1);

        immerseStatus();

    }

    private void immerseStatus() {

        //在4.4以下无法设置状态栏，returen；
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //在5.0以上
            //1、设置状态栏透明色，为window添加flag，允许设置颜色
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            //2、设置content布局为全屏显示，内容可延伸到statusBar
            int uiVisibility = window.getDecorView().getSystemUiVisibility();
            uiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //防止内容大小发生变化
            uiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            window.getDecorView().setSystemUiVisibility(uiVisibility);
        } else {
            //4.4设置直接设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
