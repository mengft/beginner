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

/**
 * Created by mengft on 2018/5/22.
 */

public class BaseAdapterArticle extends BaseAdapter {

    private Context context;
    private BaseAdapterArticleCellDate[] data;
    private BaseAdapterArticleCellDate item;

    public BaseAdapterArticle(Context context, BaseAdapterArticleCellDate[] data) {
        this.context = context;
        this.data = data;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public BaseAdapterArticleCellDate[] getData() {
        return data;
    }

    public void setData(BaseAdapterArticleCellDate[] data) {
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
        ViewHolder viewHolder = null;
        // View
        if (view == null) {
            view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.article_cell, null);

            viewHolder = new ViewHolder();
            viewHolder.tv_article_title = view.findViewById(R.id.tv_article_title);
            viewHolder.iv_article_thumbnail = view.findViewById(R.id.iv_article_thumbnail);
            viewHolder.tv_article_hot = view.findViewById(R.id.tv_article_hot);

            // 将viewHolder存储至view
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // 数据源
        item = (BaseAdapterArticleCellDate) getItem(i);
        // 标题
        viewHolder.tv_article_title.setText(item.getTitle());
        // 人气
        String hot = context.getResources().getString(R.string.hot);
        viewHolder.tv_article_hot.setText(String.format(hot, item.getEnrollNumber()));
        // 缩略图
        //viewHolder.iv_article_thumbnail.setImageURI(Uri.parse(item.getThumbnail()));

        return view;
    }


    static class ViewHolder {
        TextView tv_article_title;
        ImageView iv_article_thumbnail;
        TextViewNumber tv_article_hot;
    }
}
