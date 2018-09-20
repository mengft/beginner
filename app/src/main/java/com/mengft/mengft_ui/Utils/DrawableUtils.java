package com.mengft.mengft_ui.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by mengft on 2018/6/22.
 */

public class DrawableUtils {

    private Context context;

    public DrawableUtils(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 调用函数缩小图片
     *
     * @param context
     * @param restId
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public BitmapDrawable getNewDrawable(Activity context, int restId, int dstWidth, int dstHeight) {
        Bitmap Bmp = BitmapFactory.decodeResource(context.getResources(), restId);
        Bitmap bmp = Bmp.createScaledBitmap(Bmp, dstWidth, dstHeight, true);
        BitmapDrawable d = new BitmapDrawable(bmp);
        Bitmap bitmap = d.getBitmap();
        if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
            d.setTargetDensity(context.getResources().getDisplayMetrics());
        }
        return d;
    }

}
