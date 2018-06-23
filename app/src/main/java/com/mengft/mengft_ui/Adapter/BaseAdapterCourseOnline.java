package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mengft.mengft_ui.Compenent.TextViewNumber;
import com.mengft.mengft_ui.R;

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
            view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.course_online_cell, null);
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
        //viewHolder.iv_course_online_thumbnail.setImageURI(Uri.parse(item.getThumbnail()));

        return view;
    }

    static class ViewHolder {
        TextView tv_online_course_title;
        TextViewNumber tv_online_course_hot;
        ImageView iv_course_online_thumbnail;
    }
}
