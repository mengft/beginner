package com.mengft.mengft_ui.Course;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.easefun.polyvsdk.video.PolyvVideoView;
import com.easefun.polyvsdk.video.listener.IPolyvOnCompletionListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureClickListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureLeftDownListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureLeftUpListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureRightDownListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureRightUpListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureSwipeLeftListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnGestureSwipeRightListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnPlayPauseListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnPreparedListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnSeekCompleteListener2;
import com.easefun.polyvsdk.video.listener.IPolyvOnVideoSizeChangedListener2;
import com.mengft.mengft_ui.Component.CircleLoading;
import com.mengft.mengft_ui.Component.TopBar;
import com.mengft.mengft_ui.Course.player.PolyvPlayerMediaController;
import com.mengft.mengft_ui.Course.player.PolyvPlayerPreviewView;
import com.mengft.mengft_ui.Course.player.PolyvPlayerProgressView;
import com.mengft.mengft_ui.R;

import static com.mengft.mengft_ui.R.id.tb_course_player;

/**
 * Created by mengft on 2018/6/21.
 */

@Route(path = "/course/CoursePlayerActivity")
public class CoursePlayerActivity extends Activity {

    @Autowired
    String title, vid;                                      // 导航注入的视频信息
    private static String TAG = CoursePlayerActivity.class.getSimpleName();
    private boolean isPlaying = false;                      // 视频播放状态
    private int fastForwardPos = 0;                         // 进度
    private TopBar topBar;                                  // 顶部导航
    private RelativeLayout viewLayout;                      // 播放器的parentView
    private PolyvVideoView videoView;                       // 播放器界面
    private PolyvPlayerMediaController mediaController;     // 视频控制栏
    private PolyvPlayerProgressView progressView;           // 手势出现的进度界面
    private CircleLoading circleLoading;                    // 视频加载进度条
    private PolyvPlayerPreviewView firstStartView;          // 缩略图界面
    private int ORIENTATION = 1;                            // 屏幕方向，默认为1 竖屏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_player);
        // 自动注入，以接收参数
        ARouter.getInstance().inject(this);

        initView();
        bindTopBar();
        bindCoursePlayer();
    }

    /**
     * 初始化控件
     */
    private void initView () {
        viewLayout = findViewById(R.id.rl_course_player_parent);        // parentView
        progressView = findViewById(R.id.pv_course_player);             // 左右手势
        circleLoading = findViewById(R.id.cl_course_player);            // 视频加载
        firstStartView = findViewById(R.id.preview_course_player);

        mediaController = findViewById(R.id.course_player_controller);  // 播放控制器
        mediaController.initConfig(viewLayout);
    }

    /**
     * 绑定 TopBar
     */
    private void bindTopBar() {
        topBar = findViewById(tb_course_player);
        topBar.setTopbarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    /**
     * 绑定视频播放器
     */
    private void bindCoursePlayer() {
        videoView = findViewById(R.id.polyv_video_view);

        videoView.setOpenAd(true);                  // 广告
        videoView.setOpenTeaser(true);
        videoView.setOpenQuestion(true);            // 弹幕
        videoView.setOpenPreload(true, 2);          // 预加载
        videoView.setOpenMarquee(true);
        videoView.setAutoContinue(true);
        videoView.setNeedGestureDetector(true);
        videoView.setPlayerBufferingIndicator(circleLoading);
        videoView.setMediaController(mediaController);  // 播放控制器

        /**
         * 设置视频已准备好马上进入播放回调
         * @param l
         */
        videoView.setOnPreparedListener(new IPolyvOnPreparedListener2() {
            @Override
            public void onPrepared() {
                Log.e(title, "预加载完毕");
                mediaController.preparedView();
                progressView.setViewMaxValue(videoView.getDuration());

            }
        });

        /**
         * 设置视频播放/暂停/播放完成回调
         * @param l
         */
        videoView.setOnPlayPauseListener(new IPolyvOnPlayPauseListener() {
            @Override
            public void onPause() {
                Log.e(title, "播放暂停");
            }

            @Override
            public void onPlay() {
                Log.e(title, "开始播放");
            }

            @Override
            public void onCompletion() {

            }
        });

        /**
         * 设置视频播放完成回调
         * @param l
         */
        videoView.setOnCompletionListener(new IPolyvOnCompletionListener2() {
            @Override
            public void onCompletion() {
                Log.e(title, "播放完成");
            }
        });


        /**
         * 设置seek完成回调
         * @param l
         */
        videoView.setOnSeekCompleteListener(new IPolyvOnSeekCompleteListener2() {
            @Override
            public void onSeekComplete() {
                Log.e(title, "seek完成");
            }
        });

        /**
         * 设置视频尺寸改变回调
         * @param l
         */
        videoView.setOnVideoSizeChangedListener(new IPolyvOnVideoSizeChangedListener2() {
            @Override
            public void onVideoSizeChanged(int width, int height, int sarNum, int sarDen) {
                Log.e(title, "播放器尺寸发生变化");
            }
        });

        /**
         * 设置手势单击回调 (可通过单击时间差设置双击事件)
         * @param l
         */
        videoView.setOnGestureClickListener(new IPolyvOnGestureClickListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "手指单击屏幕");
                if (videoView.isInPlaybackState() && mediaController != null)
                    if (mediaController.isShowing())
                        mediaController.hide();
                    else
                        mediaController.show();

            }
        });

        /**
         * 设置手势左滑回调
         * @param l
         */
        videoView.setOnGestureSwipeLeftListener(new IPolyvOnGestureSwipeLeftListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "播放进度回放N时间");

                if (fastForwardPos == 0) fastForwardPos = videoView.getCurrentPosition();
                if (end) {
                    if (fastForwardPos == 0) fastForwardPos = videoView.getCurrentPosition();
                    videoView.seekTo(fastForwardPos);

                    if (videoView.isCompletedState()) {
                        videoView.start();
                    }
                    fastForwardPos = 0;

                } else {
                    fastForwardPos -= 2000;
                    if (fastForwardPos <= 0) {
                        fastForwardPos = -1;
                    }
                }
                // 设置进度条
                progressView.setViewProgressValue(fastForwardPos, videoView.getDuration(), end, false);
            }
        });

        /**
         * 设置手势右滑回调
         * @param l
         */
        videoView.setOnGestureSwipeRightListener(new IPolyvOnGestureSwipeRightListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "播放进度快进N时间");

                if (fastForwardPos == 0) fastForwardPos = videoView.getCurrentPosition();
                if (end) {
                    if (fastForwardPos > videoView.getDuration()) fastForwardPos = videoView.getDuration();
                    if (videoView.isCompletedState()) {
                        videoView.seekTo(fastForwardPos);
                    } else if (!videoView.isCompletedState() && fastForwardPos != videoView.getDuration()) {
                        videoView.seekTo(fastForwardPos);
                        videoView.start();
                    }
                    fastForwardPos = 0;
                } else {
                    fastForwardPos += 2000;
                    if (fastForwardPos > videoView.getDuration()) fastForwardPos = videoView.getDuration();
                    videoView.seekTo(fastForwardPos);
                }
                // 设置进度条
                progressView.setViewProgressValue(fastForwardPos, videoView.getDuration(), end, true);
            }
        });

        /**
         * 设置手势左向上回调 (提高亮度)
         * @param l
         */
        videoView.setOnGestureLeftUpListener(new IPolyvOnGestureLeftUpListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "接收到从左往上的手势，提高亮度");
                int brightness = videoView.getBrightness(CoursePlayerActivity.this) + 5;
                if (brightness > 100) brightness = 100;

                videoView.setBrightness(CoursePlayerActivity.this, brightness);
            }
        });

        /**
         * 设置手势左向下回调 (降低亮度)
         * @param l
         */
        videoView.setOnGestureLeftDownListener(new IPolyvOnGestureLeftDownListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "接收到从左往下的手势，降低亮度");

                int brightness = videoView.getBrightness(CoursePlayerActivity.this) - 5;
                if (brightness < 0) brightness = 0;

                videoView.setBrightness(CoursePlayerActivity.this, brightness);
            }
        });

        /**
         * 设置手势右向上回调 (增大音量)
         * @param l
         */
        videoView.setOnGestureRightUpListener(new IPolyvOnGestureRightUpListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "接收到右往上的手势，增大音量");

                int volume = videoView.getVolume() + 10;
                if (volume > 100) volume = 100;

                videoView.setVolume(volume);
            }
        });

        /**
         * 设置手势右向下回调 (降低音量)
         * @param l
         */
        videoView.setOnGestureRightDownListener(new IPolyvOnGestureRightDownListener() {
            @Override
            public void callback(boolean start, boolean end) {
                Log.e(title, "接收到右往下的手势， 降低音量");

                int volume = videoView.getVolume() - 10;
                if (volume < 0) volume = 0;

                videoView.setVolume(volume);
            }
        });

        // 预加载缩略图
        play(vid, 3, false, false);

    }

    /**
     * 播放视频
     * @param vid             视频id
     * @param bitrate         码率（清晰度）1.普通,2.清晰3.高清
     * @param startNow        是否现在开始播放视频
     * @param isMustFromLocal 是否必须从本地（本地缓存的视频）播放
     */
    public void play(final String vid, final int bitrate, boolean startNow, final boolean isMustFromLocal) {
        mediaController.hide();
        circleLoading.setVisibility(View.GONE);         // 视频加载
        progressView.resetMaxValue();                   // 进度条重置
        firstStartView.hide();                          // 缩略图

        if (startNow) {
            Log.e(TAG, "开始播放" + title + vid);
            videoView.setVid(vid, bitrate, isMustFromLocal);    //调用setVid方法视频会自动播放

        } else {
            firstStartView.setOnPreViewClickListener(new PolyvPlayerPreviewView.OnPreViewClickListener() {
                @Override
                public void onClick() {

                    Log.e(TAG, "开始播放" + title + vid);
                    videoView.setVid(vid, bitrate, isMustFromLocal);
                }
            });

            firstStartView.show(vid);
        }
    }

    /**
     * 屏幕横屏、竖屏切换监听
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "屏幕方向" + newConfig.orientation);
        if (newConfig.orientation != ORIENTATION) {
            // 屏幕方向为横屏
            Boolean flag = newConfig.orientation != Configuration.ORIENTATION_LANDSCAPE;
            resetLayoutVisible(flag);
            ORIENTATION = newConfig.orientation;
        }
    }

    /**
     * 根据横竖屏控制控件的显示
     * @param flag
     */
    private void resetLayoutVisible (Boolean flag) {
        int visible = flag ? View.VISIBLE : View.GONE;
        topBar.setVisibility(visible);          // TopBar
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 返回播放界面继续播放
        if (isPlaying) {
            videoView.onActivityResume();
        }
        mediaController.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaController.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 播放暂停
        isPlaying = videoView.onActivityStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroy();
        mediaController.disable();
    }
}
