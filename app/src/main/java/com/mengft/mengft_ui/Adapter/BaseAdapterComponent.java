package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/8/14.
 */

public class BaseAdapterComponent extends BaseAdapter {
    private Context context;
    private BaseAdapterComponentCellData[] data;

    public BaseAdapterComponent(Context context, BaseAdapterComponentCellData[] data) {
        this.context = context;
        this.data = data;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public BaseAdapterComponentCellData[] getData() {
        return data;
    }

    public void setData(BaseAdapterComponentCellData[] data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return view;
    }
}
