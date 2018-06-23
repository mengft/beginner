package com.mengft.mengft_ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Adapter.BaseAdapterArticle;
import com.mengft.mengft_ui.Adapter.BaseAdapterArticleCellDate;
import com.mengft.mengft_ui.Compenent.ListViewForScrollView;

/**
 * Created by mengft on 2018/5/2.
 */
@Route(path = "/home/TabFragmentHome")
public class TabFragmentHome extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RelativeLayout rl_data_left, rl_data_right;
    private ListViewForScrollView lv_home;
    private TextView tv_home_operating, tv_home_project1, tv_home_project2, tv_home_project3;

    private BaseAdapterArticleCellDate[] newsList;

    public TabFragmentHome() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bindView(view);
        return view;
    }

    public void bindView (View view) {
        // Card
        rl_data_left = view.findViewById(R.id.rl_data_left);
        rl_data_right = view.findViewById(R.id.rl_data_right);
        // ListView
        lv_home = view.findViewById(R.id.lv_home);
        // TextView
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/iconfont.ttf");
        tv_home_operating = view.findViewById(R.id.tv_home_operating);
        tv_home_project1 = view.findViewById(R.id.tv_home_project1);
        tv_home_project2 = view.findViewById(R.id.tv_home_project2);
        tv_home_project3 = view.findViewById(R.id.tv_home_project3);

        tv_home_operating.setTypeface(typeface);
        tv_home_project1.setTypeface(typeface);
        tv_home_project2.setTypeface(typeface);
        tv_home_project3.setTypeface(typeface);

        rl_data_left.setOnClickListener(this);

        newsList = new BaseAdapterArticleCellDate[] {
                new BaseAdapterArticleCellDate("资讯标题一", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "http://www.runoob.com/w3cnote/android-tutorial-webview.html", 20),
                new BaseAdapterArticleCellDate("资讯标题二", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "http://www.runoob.com/w3cnote/android-tutorial-listview-item.html", 20),
                new BaseAdapterArticleCellDate("资讯标题三", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "http://www.runoob.com/w3cnote/android-tutorial-handler-message.html", 20),
                new BaseAdapterArticleCellDate("资讯标题四", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "http://www.runoob.com/w3cnote/android-tutorial-relativelayout.html", 20),
        };
        lv_home.setAdapter(new BaseAdapterArticle(getContext(), newsList));
        lv_home.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_data_left:
                ARouter.getInstance().build("/home/UiExperienceActivity").navigation();
                break;
            default:
                break;
        }
    }

    /**
     * 推荐阅读 ListView item 点击事件
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BaseAdapterArticleCellDate item = newsList[i];
        // 数据传递 标题title、网址url
        Bundle bundle = new Bundle();
        bundle.putString("title", item.getTitle());
        bundle.putString("url", item.getUrl());

        ARouter.getInstance().build("/home/ArticleReaderActivity").with(bundle).navigation();
    }
}
