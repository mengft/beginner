package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mengft.mengft_ui.Component.TextViewIcon;
import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/6/10.
 */

public class BaseAdapterUiExperience extends BaseAdapter {

    private Context context;
    private String[] data;

    public BaseAdapterUiExperience(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
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
        String item = data[i];
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listinfo_item_cell, null);
            view.setPadding(30, 30, 20, 30);

            viewHolder = new ViewHolder();
            viewHolder.tv_listinfo_key = view.findViewById(R.id.tv_listinfo_key);
            viewHolder.tvi_listinfo_icon = view.findViewById(R.id.tvi_listinfo_icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // Api 名称
        viewHolder.tv_listinfo_key.setText(item);
        viewHolder.tvi_listinfo_icon.setTextSize(12);
        return view;
    }

    public class ViewHolder {
        private TextView tv_listinfo_key;
        private TextViewIcon tvi_listinfo_icon;
    }
}
