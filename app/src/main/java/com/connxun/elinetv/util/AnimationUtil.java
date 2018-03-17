package com.connxun.elinetv.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2018\3\15 0015.
 */

//相关动画
public class AnimationUtil {

    public static TranslateAnimation startAnimation(float fromXValue,float toXValue,float fromYValue,float toyValue){
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fromXValue,
                Animation.RELATIVE_TO_SELF, toXValue, Animation.RELATIVE_TO_SELF,
                fromYValue, Animation.RELATIVE_TO_SELF, toyValue);
        mShowAction.setDuration(500);
        return mShowAction;
    }

    public static  TranslateAnimation hindAnimation(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);
        return  mHiddenAction;
    }



}
