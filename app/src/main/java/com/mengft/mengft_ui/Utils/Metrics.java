package com.mengft.mengft_ui.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by mengft on 2018/6/15.
 * 尺寸计算工具类
 */

public class Metrics {

    private Context context;

    public Metrics(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public int screenWidth() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public int screenHeight() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
