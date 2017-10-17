package com.yh.webviewdemo;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by level on 2016/12/30.
 */

public class ScriptUtils {

    private Activity activity;

    public ScriptUtils(Activity activity) {
        this.activity = activity;
    }
    //使用注解的方式来定义一个javascript调用的方法
    @JavascriptInterface
    public void setTitle(String title){
        Log.d("yh","title---"+title);
        activity.setTitle(title);
    }
}
