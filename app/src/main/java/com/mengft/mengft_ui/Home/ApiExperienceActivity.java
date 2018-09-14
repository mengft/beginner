package com.mengft.mengft_ui.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Adapter.BaseAdapterComponentCellData;
import com.mengft.mengft_ui.Adapter.BaseAdapterUiExperience;
import com.mengft.mengft_ui.Component.TopBar;
import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/7/19.
 */
@Route(path = "/home/ApiExperienceActivity")
public class ApiExperienceActivity extends Activity implements AdapterView.OnItemClickListener, TopBar.TopBarClickListener {

    private static final String TAG = ApiExperienceActivity.class.getSimpleName();
    private TopBar topBar;
    private ListView listView;

    private BaseAdapterComponentCellData[] apiArr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_api_experience);

        initView();
    }

    private void initView() {
        topBar = findViewById(R.id.tb_api_experience);
        listView = findViewById(R.id.lv_api_experience);

        topBar.setTopbarClickListener(this);
//        apiArr = new String[] {"响应系统设置的事件(Configuration类)", "Handler消息传递机制浅析"};
        apiArr = new BaseAdapterComponentCellData[] {
                new BaseAdapterComponentCellData("DeviceConfiguration", "响应系统设置的事件(Configuration类)", "ss"),
                new BaseAdapterComponentCellData("Handler", "Handler消息传递机制浅析", "ss"),
        };
        listView.setAdapter(new BaseAdapterUiExperience(this, apiArr));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = apiArr[position];
        Log.e(TAG, item);
        switch (item) {
            case "DeviceConfiguration":
                ARouter.getInstance().build("/home/DeviceConfiguration").navigation();
                break;
            case "Handle":
                break;
            default:
                break;
        }
    }

    @Override
    public void leftClick() {
        Log.e(TAG, "finish");
        finish();
    }

    @Override
    public void rightClick() {

    }
}
