package com.mengft.mengft_ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Adapter.BaseAdapterPersonalOptionsCellData;
import com.mengft.mengft_ui.Component.TextViewIcon;

/**
 * Created by mengft on 2018/5/2.
 */

@Route(path = "/personal/TabFragmentPersonalCenter")
public class TabFragmentPersonalCenter extends Fragment implements View.OnClickListener {

    private GridLayout gl_person_options;
    private BaseAdapterPersonalOptionsCellData[] options;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalcenter, container, false);

        bindHeader(view);
        bindGridView(view);
        return view;
    }

    /**
     * 绑定个人信息Header
     * @param view
     */
    private void bindHeader (View view) {
        RelativeLayout relativeLayout = view.findViewById(R.id.rl_personal_header);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/personal/UserInfoActivity").navigation();
            }
        });
    }

    /**
     * 绑定GridLayout
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void bindGridView(View view) {
        // Gridlayout
        gl_person_options = view.findViewById(R.id.gl_personal_options);

        options = new BaseAdapterPersonalOptionsCellData[]{
                new BaseAdapterPersonalOptionsCellData("我的医生", R.string.advice, getResources().getColor(R.color.colorTitle)),
                new BaseAdapterPersonalOptionsCellData("健康档案", R.string.community, getResources().getColor(R.color.colorTitle)),
                new BaseAdapterPersonalOptionsCellData("我的课程", R.string.diary, getResources().getColor(R.color.colorTitle)),
                new BaseAdapterPersonalOptionsCellData("血糖控制目标", R.string.feedback, getResources().getColor(R.color.colorTitle)),
                new BaseAdapterPersonalOptionsCellData("血糖监测方案", R.string.history, getResources().getColor(R.color.colorTitle)),
                new BaseAdapterPersonalOptionsCellData("风险智能评估", R.string.intelligent, getResources().getColor(R.color.colorTitle)),
                new BaseAdapterPersonalOptionsCellData("我的兑换", R.string.journal, getResources().getColor(R.color.colorTitle)),
        };

        for (int i = 0; i < options.length; i++) {
            final BaseAdapterPersonalOptionsCellData item = options[i];
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.personal_option_cell, null);
            // 标题
            TextView tv_option_title = linearLayout.findViewById(R.id.tv_option_title);
            tv_option_title.setText(item.getTitle());
            tv_option_title.setTextColor(item.getColor());
            // 图标
            TextViewIcon tvi_option_icon = linearLayout.findViewById(R.id.tvi_option_icon);
            tvi_option_icon.setText(getResources().getString(item.getIconName()));
            tvi_option_icon.setTextColor(item.getColor());

            // 子控件的位置和比重
            GridLayout.Spec rowSpec = GridLayout.spec(i / 3, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 3, 1f);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
            // 设置监听事件
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            ViewTag viewTag = new ViewTag();
            viewTag.key = i;
            linearLayout.setTag(viewTag);

            // 添加子控件
            gl_person_options.addView(linearLayout, layoutParams);

        }

    }

    /**
     * GridView OnClick事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        ViewTag viewTag = (ViewTag) view.getTag();
        int key = viewTag.key;
        switch (key) {
            case 0:
                Log.e("当前", String.valueOf(key));
                break;
            case 1:
                Log.e("当前", String.valueOf(key));
                break;
            case 2:
                Log.e("当前", String.valueOf(key));
                break;
            case 3:
                Log.e("当前", String.valueOf(key));
                break;
            default:
                Log.e("当前", String.valueOf(key));
                break;
        }
    }

    /**
     * 静态类，存放GridLayout item tag，在点击时获取position信息
     */
    private static class ViewTag {
        int key;
    }
}
