package com.mengft.mengft_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mengft.mengft_ui.Adapter.BaseAdapterCommunity;
import com.mengft.mengft_ui.Compenent.ListViewForScrollView;

/**
 * Created by mengft on 2018/5/2.
 */

public class TabFragmentCommunity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        bindView(view);
        return view;
    }

    // 绑定控件
    private void bindView (View view) {

        ListViewForScrollView lv_community_messagelist = view.findViewById(R.id.lv_community_messagelist);
        lv_community_messagelist.setAdapter(new BaseAdapterCommunity(getContext()));
    }
}
