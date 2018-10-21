package com.mengft.mengft_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mengft.mengft_ui.Adapter.BaseAdapterCourseOffline;
import com.mengft.mengft_ui.Adapter.BaseAdapterCourseOnline;
import com.mengft.mengft_ui.Component.ListViewForScrollView;
import com.mengft.mengft_ui.Utils.OKHttpCallback;
import com.mengft.mengft_ui.Utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mengft on 2018/5/2.
 */

public class TabFragmentCourse extends Fragment {

    private static final String TAG = TabFragmentCourse.class.getSimpleName();
    private ListViewForScrollView lv_course_online, lv_course_offline;

    private JSONArray onlineCellDatas;
    private JSONArray offlineCellDatas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        init(view);
        return view;
    }

    /**
     * 初始化
     * @param view
     */
    private void init(View view) {

        bindOnlineListView(view);
        bindOfflineListView(view);

        getOnlineCourseList();
        getOfflineCourseList();
    }

    /**
     * 获取线上课程列表
     */
    private void getOnlineCourseList () {
        // 获取推荐阅读
        String url = "https://hmdev.healscitech.com/hmp/api/app/courses/list?type=" + 2 + "&offset=" + 0 + "&limit=" + 10;
        new OkHttpUtils(this.getContext()).getAction(url, new OKHttpCallback() {
            @Override
            public void success(JSONObject successJson) {
                Log.e(TAG, successJson.toString());
                JSONObject data = successJson.optJSONObject("data");

                Integer total = data.optInt("total");
                onlineCellDatas = data.optJSONArray("list");
                if (!onlineCellDatas.equals(null)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           lv_course_online.setAdapter(new BaseAdapterCourseOnline(getContext(), onlineCellDatas));
                        }
                    });
                }
            }

            @Override
            public void failed(JSONObject failedJson) {
                Log.e(TAG, failedJson.optString("code") + " " + failedJson.optString("message"));
            }
        });
    }

    /**
     * 获取线下课程列表
     */
    private void getOfflineCourseList () {
        // 获取推荐阅读
        String url = "https://hmdev.healscitech.com/hmp/api/app/courses/list?type=" + 1 + "&offset=" + 0 + "&limit=" + 10;
        new OkHttpUtils(this.getContext()).getAction(url, new OKHttpCallback() {
            @Override
            public void success(JSONObject successJson) {
                Log.e(TAG, successJson.toString());
                JSONObject data = successJson.optJSONObject("data");

                Integer total = data.optInt("total");
                offlineCellDatas = data.optJSONArray("list");
                if (!offlineCellDatas.equals(null)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv_course_offline.setAdapter(new BaseAdapterCourseOffline(getContext(), offlineCellDatas));
                        }
                    });
                }
            }

            @Override
            public void failed(JSONObject failedJson) {
                Log.e(TAG, failedJson.optString("code") + " " + failedJson.optString("message"));
            }
        });
    }

    /**
     * ListView 线上课程
     * @param view
     */
    private void bindOnlineListView (View view) {
        lv_course_online = view.findViewById(R.id.lv_course_online);
    }

    /**
     * ListView 线下活动
     * @param view
     */
    private void bindOfflineListView(View view) {
        lv_course_offline = view.findViewById(R.id.lv_course_offline);
    }

}
