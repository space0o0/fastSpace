package space.learning.myui.anim.lib;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import java.security.Key;
import java.util.Arrays;
import java.util.List;

public class KeyFrameSet {

    private List<KeyFrame> keyFrames;

    //估值器
    private TypeEvaluator evaluator;

    private KeyFrameSet(KeyFrame... keyFrames) {

        this.keyFrames = Arrays.asList(keyFrames);
        evaluator = new FloatEvaluator();
    }

    public static KeyFrameSet ofFloat(Float... values) {
        //关键帧初始化
        if (values.length <= 0) {
            return null;
        }

        int length = values.length;

        /**
         * 比如有1f,2f,3f,4f
         * 1f:0%    0/(4-1)
         * 2f:33%   1/(4-1)
         * 3f:66%   2/(4-1)
         * 4f:100%  3/(4-1)
         */
        KeyFrame[] keyFrames = new KeyFrame[length];
        for (int i = 0; i < length; i++) {
            keyFrames[i] = new KeyFrame((float) i / (length - 1), values[i]);
        }

        return new KeyFrameSet(keyFrames);
    }

    /**
     * 传入动画百分比，获取动画属性值
     *
     * @param animPercent
     */
    public Object getValue(float animPercent) {
        //寻找动画百分比在哪两个相邻关键帧的位置
        //第一个关键帧
        KeyFrame firstKeyFrame = keyFrames.get(0);

        for (KeyFrame keyFrame : keyFrames) {
            KeyFrame secondKeyFrame = keyFrame;

            //动画百分比小于第二个keyFrame，就在这两个keyFrame之间
            if (animPercent < secondKeyFrame.percent) {

                //比如当前百分比位40% 需要计算出在2f～3f的值
                //1、40%在2f～3f段的百分比：p =（40%-33%）/（66%-33%）
                //2、value = 2f+p*(3f-2f)

                float p = (animPercent - firstKeyFrame.percent) / (secondKeyFrame.percent - firstKeyFrame.percent);

                if (evaluator == null) {
                    //手动计算动画百分比对应的属性值
                    return firstKeyFrame.value + p * (secondKeyFrame.value - firstKeyFrame.value);
                } else {
                    return evaluator.evaluate(animPercent, firstKeyFrame.value, secondKeyFrame.value);
                }

            } else {
                //不在两个之间，first移位，寻找下一段百分比
                firstKeyFrame = secondKeyFrame;
            }
        }

        //默认返回第一关键帧
        return keyFrames.get(keyFrames.size() - 1).value;
    }
}
