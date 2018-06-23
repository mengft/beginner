package com.mengft.mengft_ui.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mengft.mengft_ui.Adapter.BaseAdapterUiExperience;
import com.mengft.mengft_ui.Compenent.AlertDialogMessage;
import com.mengft.mengft_ui.Compenent.TopBar;
import com.mengft.mengft_ui.R;

import static com.mengft.mengft_ui.R.id.lv_uiexperience;

/**
 * Created by mengft on 2018/6/8.
 */

@Route(path = "/home/UiExperienceActivity")
public class UiExperienceActivity extends Activity implements AdapterView.OnItemClickListener, AlertDialogMessage.AlertDialogMessageListener {

    private static String TAG = "UiExperienceActivity";
    private AlertDialogMessage alertDialogMessage;
    private ListView listView;
    private String[] ui_arr;

    private TopBar topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_experience);

        bindTopBar();
        bindListView();
    }

    /**
     * 绑定TopBar
     */
    private void bindTopBar() {
        topBar = findViewById(R.id.tb_ui_experience);
        topBar.setTopbarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void leftClick() {
                Log.e(TAG, "finish");
                finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    /**
     * 绑定ListView
     */
    private void bindListView() {
        ui_arr = new String[]{"AlertDialigMessage", "AlertDaaligMessage", "TextViewNumber", "TextViewIcon"};
        listView = findViewById(lv_uiexperience);
        listView.setAdapter(new BaseAdapterUiExperience(this, ui_arr));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String item = ui_arr[i];
        switch (item) {
            case "AlertDialigMessage":
                //  alertDialogMessage = new AlertDialogMessage(this, "这是Component 组件", "名字为：AlertDialogMessage");
                alertDialogMessage = new AlertDialogMessage(this, "这是Component 组件", "名字为：AlertDialogMessage", "走了哦", "留下吧");
                alertDialogMessage.setAlertListener(this);
                alertDialogMessage.show();
                break;
            case "AlertDaaligMessage":
                break;
            default:
                break;
        }
    }

    /**
     * AlertDialogMessage 左侧事件
     */
    @Override
    public void positiveClick() {

    }

    /**
     * AlertDialogMessage 右侧事件
     */
    @Override
    public void negativeClick() {

    }
}
