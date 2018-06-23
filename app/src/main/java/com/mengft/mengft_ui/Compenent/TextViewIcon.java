package com.mengft.mengft_ui.Compenent;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mengft on 2018/5/29.
 */

public class TextViewIcon extends TextView {
    public TextViewIcon(Context context) {
        super(context);
        initTypeFace(context);
    }

    public TextViewIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeFace(context);

    }

    public TextViewIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeFace(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextViewIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTypeFace(context);
    }

    // 初始化字体文件
    private void initTypeFace (Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
        setTypeface(typeface);
    }
}
