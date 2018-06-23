package com.mengft.mengft_ui.PersonalCenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mengft.mengft_ui.Adapter.BaseExpandableListAdapterListInfo;
import com.mengft.mengft_ui.Adapter.BaseExpandableListAdapterListInfoGroupCellData;
import com.mengft.mengft_ui.Adapter.BaseExpandableListAdapterListInfoItemCellData;
import com.mengft.mengft_ui.Compenent.TopBar;
import com.mengft.mengft_ui.R;

import java.util.ArrayList;

/**
 * Created by mengft on 2018/5/30.
 */

@Route(path = "/personal/UserInfoActivity")
public class UserInfoActivity extends Activity {

    private ExpandableListView expandableListView;
    private ArrayList<BaseExpandableListAdapterListInfoGroupCellData> groupData = null;
    private ArrayList<BaseExpandableListAdapterListInfoItemCellData> lData = null;
    private ArrayList<ArrayList<BaseExpandableListAdapterListInfoItemCellData>> itemData = null;

    private TopBar topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_userinfo);

        bindTopBar();
        bindExpandableListView();
    }

    /**
     * 绑定TopBar
     */
    private void bindTopBar () {
        topBar = findViewById(R.id.topbar);

        topBar.setTopbarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void leftClick() {
                Log.e(getPackageName(), "finish");
                finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    /**
     * 绑定 ExpandableListView
     */
    private void bindExpandableListView () {

        expandableListView = findViewById(R.id.el_personal_userinfo);

        // 组装Group数据
        groupData = new ArrayList<BaseExpandableListAdapterListInfoGroupCellData>();
        groupData.add(new BaseExpandableListAdapterListInfoGroupCellData("个人信息"));
        groupData.add(new BaseExpandableListAdapterListInfoGroupCellData("健康档案"));
        groupData.add(new BaseExpandableListAdapterListInfoGroupCellData("工作经历"));

        // 组装Item数据
        itemData = new ArrayList<ArrayList<BaseExpandableListAdapterListInfoItemCellData>>();
        lData = new ArrayList<BaseExpandableListAdapterListInfoItemCellData>();

        lData.add(new BaseExpandableListAdapterListInfoItemCellData("姓名", "孟凡涛"));
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("民族", "汉族"));
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("籍贯", "河南省巩义市"));
        itemData.add(lData);

        lData = new ArrayList<BaseExpandableListAdapterListInfoItemCellData>();
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("血糖", "5.6"));
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("身高", "178CM"));
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("体重", "70Kg"));
        itemData.add(lData);

        lData = new ArrayList<BaseExpandableListAdapterListInfoItemCellData>();
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("和众", "PHP Developer"));
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("和兴", "SoftWare Engineer"));
        lData.add(new BaseExpandableListAdapterListInfoItemCellData("司马", "CTO"));
        itemData.add(lData);

        expandableListView.setAdapter(new BaseExpandableListAdapterListInfo(this, groupData, itemData));

        // 默认展开列表
        for (int i = 0; i < groupData.size(); i++) {
            expandableListView.expandGroup(i, false);
        }

        // 重写收起事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                // return false;
                return true;
            }
        });
    }

}
