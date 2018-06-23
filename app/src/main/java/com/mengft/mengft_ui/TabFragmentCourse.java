package com.mengft.mengft_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Adapter.BaseAdapterCourseOffline;
import com.mengft.mengft_ui.Adapter.BaseAdapterCourseOfflineCellData;
import com.mengft.mengft_ui.Adapter.BaseAdapterCourseOnline;
import com.mengft.mengft_ui.Adapter.BaseAdapterCourseOnlineCellData;
import com.mengft.mengft_ui.Compenent.ListViewForScrollView;

import java.util.Date;

/**
 * Created by mengft on 2018/5/2.
 */

public class TabFragmentCourse extends Fragment {

    private ListViewForScrollView lv_course_online, lv_course_offline;
    private BaseAdapterCourseOnlineCellData[] onlineCellDatas;
    private BaseAdapterCourseOfflineCellData[] offlineCellDatas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);


        bindOnlineListView(view);
        bindOfflineListView(view);
        return view;
    }

    /**
     * ListView 线上课程
     * @param view
     */
    private void bindOnlineListView (View view) {
        lv_course_online = view.findViewById(R.id.lv_course_online);
        onlineCellDatas = new BaseAdapterCourseOnlineCellData[] {
                new BaseAdapterCourseOnlineCellData("7aefd1a7c9c01badfe1dfc80a0017d6d_7", "在线课程一", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", 20),
                new BaseAdapterCourseOnlineCellData("7aefd1a7c9c4e1101fbc77afdff156de_7","在线课程二", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", 20),
                new BaseAdapterCourseOnlineCellData("7aefd1a7c9f898dc2f1459f55edc47f9_7","在线课程三", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", 20),
                new BaseAdapterCourseOnlineCellData("7aefd1a7c9e436d1543711b72446a3fa_7","在线课程四", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", 20),
        };
        lv_course_online.setAdapter(new BaseAdapterCourseOnline(getContext(), onlineCellDatas));

        lv_course_online.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BaseAdapterCourseOnlineCellData item = onlineCellDatas[i];

                Bundle bundle = new Bundle();
                bundle.putString("title", item.getTitle());
                bundle.putString("vid", item.getVid());
                ARouter.getInstance().build("/course/CoursePlayerActivity").with(bundle).navigation();
            }
        });
    }

    /**
     * ListView 线下活动
     * @param view
     */
    private void bindOfflineListView(View view) {

        lv_course_offline = view.findViewById(R.id.lv_course_offline);
        offlineCellDatas = new BaseAdapterCourseOfflineCellData[] {
                new BaseAdapterCourseOfflineCellData("线下活动一", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "北京和兴健康医院", new Date().getTime(), 21),
                new BaseAdapterCourseOfflineCellData("线下活动二", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "北京和兴健康医院", new Date().getTime(), 21),
                new BaseAdapterCourseOfflineCellData("线下活动三", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "北京和兴健康医院", new Date().getTime(), 21),
                new BaseAdapterCourseOfflineCellData("线下活动四", "https://img30.360buyimg.com/mobilecms/s140x140_jfs/t19846/363/164253688/131240/a77a7cf1/5ae9613bN6867cc98.jpg!q90", "北京和兴健康医院", new Date().getTime(), 21),
        };
        lv_course_offline.setAdapter(new BaseAdapterCourseOffline(getContext(), offlineCellDatas));
    }
}
