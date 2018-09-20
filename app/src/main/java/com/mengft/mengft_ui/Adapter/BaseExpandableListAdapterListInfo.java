package com.mengft.mengft_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mengft.mengft_ui.R;

import java.util.ArrayList;

/**
 * Created by mengft on 2018/5/31.
 */

public class BaseExpandableListAdapterListInfo extends BaseExpandableListAdapter {

    private ArrayList<BaseExpandableListAdapterListInfoGroupCellData> groupData;
    private ArrayList<ArrayList<BaseExpandableListAdapterListInfoItemCellData>> itemData;
    private Context context;

    public BaseExpandableListAdapterListInfo(Context context, ArrayList<BaseExpandableListAdapterListInfoGroupCellData> groupData, ArrayList<ArrayList<BaseExpandableListAdapterListInfoItemCellData>> itemData) {
        this.context = context;
        this.groupData = groupData;
        this.itemData = itemData;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return itemData.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return itemData.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        ViewHolderGroup viewHolderGroup = null;

        if (view == null) {
            viewHolderGroup = new ViewHolderGroup();

            view = LayoutInflater.from(getContext()).inflate(R.layout.listinfo_group_cell, null);
            viewHolderGroup.title = view.findViewById(R.id.tv_listinfo_title);

            view.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) view.getTag();

        }
        // 数据源
        BaseExpandableListAdapterListInfoGroupCellData item = groupData.get(i);
        // 标题
        viewHolderGroup.title.setText(item.getTitle());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        ViewHolderItem viewHolderItem = null;

        if (view == null) {

            viewHolderItem = new ViewHolderItem();

            view = LayoutInflater.from(getContext()).inflate(R.layout.listinfo_item_cell, null);
            viewHolderItem.key = view.findViewById(R.id.tv_listinfo_key);
            viewHolderItem.value = view.findViewById(R.id.tv_listinfo_value);

            view.setTag(viewHolderItem);

        } else {

            viewHolderItem = (ViewHolderItem) view.getTag();
        }
        // 数据源
        BaseExpandableListAdapterListInfoItemCellData item = itemData.get(i).get(i1);
        viewHolderItem.key.setText(item.getKey());
        viewHolderItem.value.setText(item.getValue());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private static class ViewHolderGroup {
        TextView title;
    }

    private static class ViewHolderItem {
        TextView key, value;
    }
}
