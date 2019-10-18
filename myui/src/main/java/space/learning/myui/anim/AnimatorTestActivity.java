package space.learning.myui.anim;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import space.learning.myui.R;
import space.learning.myui.anim.lib.MyObjectAnimator;
import space.learning.myui.anim.lib.interpolator.LinearInterpolator;

public class AnimatorTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anim_test);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);


        final MyObjectAnimator anim = MyObjectAnimator.ofFloat(textView, "ScaleX", 1f, 2f, 4f, 1f);
        anim.setDuration(3000);
        anim.setInterpolator(new LinearInterpolator());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.start();
            }
        });
    }
}
