package com.mengft.mengft_ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Adapter.BaseAdapterArticle;
import com.mengft.mengft_ui.Component.ListViewForScrollView;
import com.mengft.mengft_ui.Utils.OKHttpCallback;
import com.mengft.mengft_ui.Utils.OkHttpUtils;
import com.mengft.mengft_ui.Utils.SQLiteUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mengft on 2018/5/2.
 */
@Route(path = "/home/TabFragmentHome")
public class TabFragmentHome extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = TabFragmentHome.class.getName();
    private ScrollView sv_home;
    private RelativeLayout rl_data_left, rl_data_right;
    private ListViewForScrollView lv_home;
    private TextView tv_home_operating, tv_home_project1, tv_home_project2, tv_home_project3;

    private JSONArray newsList;

    public TabFragmentHome() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        bindView(view);
        return view;
    }

    /**
     * 初始化
     */
    private void init () {
        // 获取推荐阅读
        new SQLiteUtils(this.getContext()).clearAction("news");
        String url = "https://service1dev.healscitech.com/authorization/api/app/news?offset=" + 0 + "&limit=" + 10;
        new OkHttpUtils(this.getContext()).getAction(url, new OKHttpCallback() {
            @Override
            public void success(JSONObject successJson) {

                JSONObject data = successJson.optJSONObject("data");

                Integer total = data.optInt("total");
                newsList = data.optJSONArray("newsList");

                if (newsList != null && newsList.length() > 0 && lv_home != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv_home.setAdapter(new BaseAdapterArticle(getContext(), newsList));
                        }
                    });

                    // 将newsList插入SQLite
                    insertArticleList();
                }
            }

            @Override
            public void failed(JSONObject failedJson) {
                Log.e(TAG, failedJson.optString("code") + " " + failedJson.optString("message"));
            }
        });
    }

    public void bindView (View view) {
        sv_home = view.findViewById(R.id.sv_home);
        rl_data_left = view.findViewById(R.id.rl_data_left);        // Card
        rl_data_right = view.findViewById(R.id.rl_data_right);
        lv_home = view.findViewById(R.id.lv_home);                  // ListView
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

        if (newsList != null && newsList.length() > 0)  lv_home.setAdapter(new BaseAdapterArticle(getContext(), newsList));

        rl_data_left.setOnClickListener(this);
        rl_data_right.setOnClickListener(this);
        lv_home.setOnItemClickListener(this);

        sv_home.scrollTo(0, 0);
    }

    /**
     * 插入文章查询数据
     */
    private void insertArticleList () {
        String[] insertKeys = {"id", "head", "iconPath", "linkPage"};
        new SQLiteUtils(this.getContext()).insertAction("news", newsList, insertKeys);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_data_left:
                ARouter.getInstance().build("/home/UiExperienceActivity").withBoolean("needAuth", true).navigation();
                break;
            case R.id.rl_data_right:
                ARouter.getInstance().build("/home/ApiExperienceActivity").navigation();
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
        JSONObject item = newsList.optJSONObject(i);
        // 数据传递 标题title、网址url
        Bundle bundle = new Bundle();
        bundle.putString("title", item.optString("head"));
        bundle.putString("url", item.optString("linkPage"));

        ARouter.getInstance().build("/home/ArticleReaderActivity").with(bundle).navigation();
    }
}
