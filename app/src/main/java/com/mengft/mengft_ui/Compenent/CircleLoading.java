package com.mengft.mengft_ui.Compenent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/6/20.
 */

public class CircleLoading extends RelativeLayout {

    private ImageView imageView;

    public CircleLoading(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 设置Layout参数
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(layoutParams);

        // 添加Loading Gif
        addView(renderLoadImage(context));
    }

    /**
     * 渲染Loading Gif
     * @param context
     * @return
     */
    private ImageView renderLoadImage (Context context) {
        imageView = new ImageView(context);

        Glide.with(context).load(R.drawable.loading).into(imageView);
        imageView.setLayoutParams(new LayoutParams(60, 60));

        return imageView;
    }
}
