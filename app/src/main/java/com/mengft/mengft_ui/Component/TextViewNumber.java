package com.mengft.mengft_ui.Component;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mengft on 2018/5/26.
 */

public class TextViewNumber extends TextView {

    public TextViewNumber(Context context) {
        super(context);
        initTypeFace(context);
    }

    public TextViewNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeFace(context);
    }

    public TextViewNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeFace(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextViewNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTypeFace(context);
    }

    private void initTypeFace (Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/DINCond-Bold.otf");
        setTypeface(typeface);
    }
}
