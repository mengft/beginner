package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengft.mengft_ui.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mengft on 2018/5/28.
 */

public class BaseAdapterCommunity extends BaseAdapter {

    private Context context;

    private BaseAdapterCommunityCellData[] data = new BaseAdapterCommunityCellData[] {
            new BaseAdapterCommunityCellData("HealSci医疗助手一", "入组成功", "", new Date().getTime()),
            new BaseAdapterCommunityCellData("HealSci医疗助手二", "入组成功", "", new Date().getTime()),
            new BaseAdapterCommunityCellData("HealSci医疗助手三", "入组成功", "", new Date().getTime()),
            new BaseAdapterCommunityCellData("HealSci医疗助手四", "入组成功", "", new Date().getTime()),
    };

    private BaseAdapterCommunityCellData item;

    public BaseAdapterCommunity(Context context) {
        this.context = context;
    }

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
            view = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.community_cell, null);
            viewHolder = new ViewHolder();

            viewHolder.tv_message_title = view.findViewById(R.id.tv_message_title);
            viewHolder.tv_message_lastmessage = view.findViewById(R.id.tv_message_lastmessage);
            viewHolder.tv_message_time = view.findViewById(R.id.tv_message_time);
            viewHolder.iv_message_icon = view.findViewById(R.id.iv_message_icon);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 数据源
        item = data[i];

        // 项目名称
        viewHolder.tv_message_title.setText(item.getTitle());

        // 最近消息
        viewHolder.tv_message_lastmessage.setText(item.getLastMessage());

        // 时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        viewHolder.tv_message_time.setText(simpleDateFormat.format(new Date(item.getTime())));

        //图标
        return view;
    }

    static class ViewHolder {
        TextView tv_message_title, tv_message_lastmessage, tv_message_time;
        ImageView iv_message_icon;
    }
}
