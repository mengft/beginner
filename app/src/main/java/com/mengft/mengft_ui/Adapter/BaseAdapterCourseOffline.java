package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mengft.mengft_ui.Component.TextViewNumber;
import com.mengft.mengft_ui.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mengft.mengft_ui.R.id.iv_course_offline_thumbnail;
import static com.mengft.mengft_ui.R.id.tv_offline_course_title;

/**
 * Created by mengft on 2018/5/27.
 */

public class BaseAdapterCourseOffline extends BaseAdapter {

    private Context context;

    private JSONArray data;
    private JSONObject item;

    public BaseAdapterCourseOffline(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public JSONObject getItem(int i) {
        return data.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        if (view == null) {
            view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.course_offline_cell, null);
            viewHolder = new ViewHolder();

            viewHolder.tv_offline_course_title = view.findViewById(tv_offline_course_title);
            viewHolder.tv_offline_course_address = view.findViewById(R.id.tv_offline_course_address);
            viewHolder.tv_offline_course_starttime = view.findViewById(R.id.tv_offline_course_starttime);
            viewHolder.tv_offline_course_enrollnumber = view.findViewById(R.id.tv_offline_course_enrollnumber);
            viewHolder.iv_course_offline_thumbnail = view.findViewById(iv_course_offline_thumbnail);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        item = getItem(i);

        // 标题
        viewHolder.tv_offline_course_title.setText(item.optString("title"));
        // 举办地点
        viewHolder.tv_offline_course_address.setText(item.optString("location"));
        // 开始时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月DD日");
        viewHolder.tv_offline_course_starttime.setText(simpleDateFormat.format(new Date(item.optLong("startTime"))));
        // 报名人数
        String enrollNumber = getContext().getResources().getString(R.string.enrollNumber);
        viewHolder.tv_offline_course_enrollnumber.setText(String.format(enrollNumber, item.optString("enrollNum")));
        // 课程封面
        viewHolder.iv_course_offline_thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.course_offline_loading)          // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.course_offline_loading)        // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.course_offline_loading)             // 设置图片加载/解码过程中错误时候显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)                            // 设置图片的解码类型
                .cacheInMemory(false)                                           // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)                                             // 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(100))                      // 是否图片加载好后渐入的动画时间
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)                   // 图片布局方式
                .build();                                                       // 构建完成

        ImageLoader.getInstance().displayImage(item.optString("thumbnail"), viewHolder.iv_course_offline_thumbnail, options);

        return view;
    }

    static class ViewHolder {
        TextView tv_offline_course_title, tv_offline_course_address, tv_offline_course_starttime;
        TextViewNumber tv_offline_course_enrollnumber;
        ImageView iv_course_offline_thumbnail;
    }
}
