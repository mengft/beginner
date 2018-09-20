package com.mengft.mengft_ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.easefun.polyvsdk.PolyvDevMountInfo;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.mengft.mengft_ui.Component.ViewPagerNoScroll;
import com.mengft.mengft_ui.Utils.ImageLoaderUtils;

import java.io.File;

@Route(path = "/main/MainActivity")
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    private static final String MiPushAppID = "2882303761517824774";
    private static final String MiPushAppKey = "5951782455774";
    // UI Pbjects
    private RadioGroup rg_tab_bar;
    private RadioButton rb_home;
    private RadioButton rb_course;
    private RadioButton rb_community;
    private RadioButton rb_personalCenter;
    private ViewPagerNoScroll viewPager;
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;

    // 代表界面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    private Bundle bundle;                  // 存放Intent 中携带的Bundle信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.openLog();                                          // 打印日志
        ARouter.openDebug();                                        // 开启调试模式
        ARouter.init(getApplication());                             // 推荐在Application中初始化
        initPolyvSDKClient();                                       // 初始化保利卫视
        new ImageLoaderUtils(this).initImageLoader();               // 初始化ImageLoading

        setContentView(R.layout.activity_main);
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        bindView();
    }

    private void bindView() {
        // Bind
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_course = (RadioButton) findViewById(R.id.rb_course);
        rb_community = (RadioButton) findViewById(R.id.rb_community);
        rb_personalCenter = (RadioButton) findViewById(R.id.rb_personalCenter);
        viewPager = (ViewPagerNoScroll) findViewById(R.id.vpager);

        // 控制底部导航图片
        Drawable drawableHome = getResources().getDrawable(R.drawable.tab_menu_home);
        drawableHome.setBounds(0, 0, 44, 44);
        rb_home.setCompoundDrawables(null, drawableHome, null, null);

        Drawable drawableCourse = getResources().getDrawable(R.drawable.tab_menu_course);
        drawableCourse.setBounds(0, 0, 44, 42);
        rb_course.setCompoundDrawables(null, drawableCourse, null, null);

        Drawable drawableComminity = getResources().getDrawable(R.drawable.tab_menu_community);
        drawableComminity.setBounds(0, 0, 46, 46);
        rb_community.setCompoundDrawables(null, drawableComminity, null, null);

        Drawable drawablePersonalCenter = getResources().getDrawable(R.drawable.tab_menu_personalcenter);
        drawablePersonalCenter.setBounds(0, 0, 46, 46);
        rb_personalCenter.setCompoundDrawables(null, drawablePersonalCenter, null, null);

        // Adapter
        viewPager.setAdapter(tabFragmentPagerAdapter);
        viewPager.setNoScroll(true);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);

        // 默认选中第一个
        rb_home.setChecked(true);

        //底部导航事件
        rg_tab_bar.setOnCheckedChangeListener(this);
    }

    // RadioGroup
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.rb_home:
                viewPager.setCurrentItem(PAGE_ONE, false);
                break;
            case R.id.rb_course:
                viewPager.setCurrentItem(PAGE_TWO, false);
                break;
            case R.id.rb_community:
                viewPager.setCurrentItem(PAGE_THREE, false);
                break;
            case R.id.rb_personalCenter:
                viewPager.setCurrentItem(PAGE_FOUR, false);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    // ViewPager
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // state 0 什么都没做、 1 正在滑动、 2 滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_home.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_course.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_community.setChecked(true);
                    break;
                case PAGE_FOUR:
                    rb_personalCenter.setChecked(true);
                    break;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销保利卫视
    }

    /**
     * 初始化保利卫视
     */
    private void initPolyvSDKClient() {
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        // 设置SDK加密串
        String SDKEncryptionString = "SsOBqhLKfJOoQ4cS8+6RN+Z+asRlUcZqv7Vrt7WdkykyE1CUvWaMz4t8+9eXWUJ3Pjw0mYKCHeyvb8T5+QdRYyBdX7NGWC5tIv8G2WKzYykvXPe9YHtkthfuupSCI8yrYgZ9Uyrh0TxxW+kSBefZ6w==";
        String encryptionKey = "VXtlHmwfS2oYm0CZ", encryptedVector = "2u9gDPKdX6GyQJKU";
        client.setConfig(SDKEncryptionString, encryptionKey, encryptedVector, this);

        // 初始化SDK设置
        client.initSetting(this);

        // 下载目录设置 (获取SD卡信息)
        PolyvDevMountInfo.getInstance().init(this, new PolyvDevMountInfo.OnLoadCallback() {
            @Override
            public void callback() {
                //是否有可移除的存储介质（例如 SD 卡）或内部（不可移除）存储可供使用。
                if (!PolyvDevMountInfo.getInstance().isSDCardAvaiable()) {
                    // TODO 没有可用的存储设备,后续不能使用视频缓存功能
                    Log.e(TAG, "没有可用的存储设备,后续不能使用视频缓存功能");
                    return;
                }

                //可移除的存储介质（例如 SD 卡），需要写入特定目录/storage/sdcard1/Android/data/包名/。
                String externalSDCardPath = PolyvDevMountInfo.getInstance().getExternalSDCardPath();
                if (!TextUtils.isEmpty(externalSDCardPath)) {
                    StringBuilder dirPath = new StringBuilder();
                    dirPath.append(externalSDCardPath).append(File.separator).append("Android").append(File.separator).append("data")
                            .append(File.separator).append(getPackageName()).append(File.separator).append("polyvdownload");
                    File saveDir = new File(dirPath.toString());
                    if (!saveDir.exists()) {
                        getExternalFilesDir(null); // 生成包名目录
                        saveDir.mkdirs();//创建下载目录
                    }

                    //设置下载存储目录
                    PolyvSDKClient.getInstance().setDownloadDir(saveDir);
                    return;
                }

                //如果没有可移除的存储介质（例如 SD 卡），那么一定有内部（不可移除）存储介质可用，都不可用的情况在前面判断过了。
                File saveDir = new File(PolyvDevMountInfo.getInstance().getInternalSDCardPath() + File.separator + "polyvdownload");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();//创建下载目录
                }

                //设置下载存储目录
                PolyvSDKClient.getInstance().setDownloadDir(saveDir);
            }
        });

        // 设置下载队列总数，多少个视频能同时下载。(默认是1，设置负数和0是没有限制)
        PolyvDownloaderManager.setDownloadQueueCount(3);

        //启动Bugly
        client.initCrashReport(this);
    }

}
