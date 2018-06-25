package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengft.mengft_ui.Compenent.TextViewNumber;
import com.mengft.mengft_ui.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import static com.mengft.mengft_ui.R.id.tv_online_course_title;

/**
 * Created by mengft on 2018/5/27.
 */

public class BaseAdapterCourseOnline extends BaseAdapter {

    private Context context;

    private BaseAdapterCourseOnlineCellData[] data;

    public BaseAdapterCourseOnline(Context context, BaseAdapterCourseOnlineCellData[] data) {
        this.context = context;
        this.data = data;
    }

    private BaseAdapterCourseOnlineCellData item;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.course_online_cell, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_online_course_title = view.findViewById(tv_online_course_title);
            viewHolder.tv_online_course_hot = view.findViewById(R.id.tv_online_course_hot);
            viewHolder.iv_course_online_thumbnail = view.findViewById(R.id.iv_course_online_thumbnail);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 数据源
        item = (BaseAdapterCourseOnlineCellData) getItem(i);
        // 标题
        viewHolder.tv_online_course_title.setText(item.getTitle());
        // 报名人数
        String enrollNumber = getContext().getResources().getString(R.string.enrollNumber);
        viewHolder.tv_online_course_hot.setText(String.format(enrollNumber, item.getEnrollNumber()));
        // 封面
        viewHolder.iv_course_online_thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.course_online_loading)           // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.course_online_loading)         // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.course_online_loading)              // 设置图片加载/解码过程中错误时候显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)                            // 设置图片的解码类型
                .cacheInMemory(false)                                           // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)                                             // 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(100))                      // 是否图片加载好后渐入的动画时间
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)                   // 图片布局方式
                .build();                                                       // 构建完成

        ImageLoader.getInstance().displayImage(item.getThumbnail(), viewHolder.iv_course_online_thumbnail, options);

        return view;
    }

    static class ViewHolder {
        TextView tv_online_course_title;
        TextViewNumber tv_online_course_hot;
        ImageView iv_course_online_thumbnail;
    }

}
