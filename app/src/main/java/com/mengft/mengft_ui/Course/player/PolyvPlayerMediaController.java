package com.mengft.mengft_ui.Course.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.easefun.polyvsdk.video.IPolyvVideoView;
import com.easefun.polyvsdk.video.PolyvBaseMediaController;
import com.easefun.polyvsdk.video.PolyvVideoView;
import com.easefun.polyvsdk.vo.PolyvVideoVO;
import com.mengft.mengft_ui.Component.TextViewIcon;
import com.mengft.mengft_ui.R;
import com.mengft.mengft_ui.Utils.DrawableUtils;
import com.mengft.mengft_ui.Utils.ScreenUtils;
import com.mengft.mengft_ui.Utils.SensorHelper;
import com.mengft.mengft_ui.Utils.TimeUtils;

/**
 * Created by mengft on 2018/6/22.
 */

public class PolyvPlayerMediaController extends PolyvBaseMediaController implements View.OnClickListener {

    private Context context;                    // 上下文
    private Activity videoActivity;             // 播放器所在Activity （当前Activity）
    private View parentView;                    // 播放器的ParentView
    private View view;                          // Controller View
    private PolyvVideoVO videoVO;               // 当前播放视频
    private PolyvVideoView videoView;           // 播放器布局

    /**
     * 竖屏的view
     */
    private RelativeLayout rl_port;             // 竖屏的控制栏
    private ImageView iv_land, iv_play;         // 竖屏的切屏按钮，竖屏的播放/暂停按钮
    private TextView tv_curtime, tv_tottime;    // 竖屏的显示播放进度控件
    private SeekBar sb_play;                    // 竖屏的进度条
    /**
     * 横屏的view
     */
    private RelativeLayout rl_land, rl_top, rl_bot;     // 横屏的控制栏，顶部布局，底部布局
    //横屏的切屏按钮，横屏的播放/暂停按钮,横屏的返回按钮，设置按钮，分享按钮，弹幕开关
    private TextViewIcon iv_finish;
    private ImageView iv_port, iv_play_land;
    // 横屏的显示播放进度控件,视频的标题,选择播放速度按钮，选择码率按钮
    private TextView tv_curtime_land, tv_tottime_land, tv_title;
    private SeekBar sb_play_land;
    /**
     * 进度条®
     */
    private boolean status_dragging;            // 进度条是否处于拖动的状态
    private boolean status_showalways;          // 控制栏是否处于一直显示的状态
    private SensorHelper sensorHelper;
    /**
     * 控制栏显示的时间
     */
    private boolean isShowing;                  //显示的状态
    private static final int longTime = 5000;
    private static final int HIDE = 12;
    private static final int SHOW_PROGRESS = 13;
    //用于处理控制栏的显示状态
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    showProgress();
                    break;
            }
        }
    };

    public PolyvPlayerMediaController(Context context) {
        this(context, null);
    }

    public PolyvPlayerMediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolyvPlayerMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        this.videoActivity = (Activity) context;
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.course_player_controller, this);

        initVerticalView();
        initHorientaticallView();
    }

    /**
     * 初始化控制栏的配置
     * @param parentView 播放器的父控件
     */
    public void initConfig(ViewGroup parentView) {
        this.parentView = parentView;
    }

    /**
     * 初始化竖屏控件
     */
    private void initVerticalView() {
        rl_port = view.findViewById(R.id.rl_port);                  // ReleativeLayout
        iv_land = view.findViewById(R.id.iv_land);                  // 切换到全屏
        iv_play = view.findViewById(R.id.iv_play);                  // 播放、暂停
        tv_curtime = view.findViewById(R.id.tv_curtime);            // 当前时间
        tv_tottime = view.findViewById(R.id.tv_tottime);            // 播放时长
        sb_play = view.findViewById(R.id.sb_play);                  // 进度条
        sb_play.setThumb(new DrawableUtils(context).getNewDrawable(videoActivity, R.drawable.polyv_btn_slider, 50, 50));

        iv_play.setOnClickListener(this);
        iv_land.setOnClickListener(this);
        sb_play.setOnSeekBarChangeListener(seekBarChangeListener);

        sensorHelper = new SensorHelper(videoActivity);
    }

    /**
     * 初始化横屏控件
     */
    private void initHorientaticallView () {
        rl_land = view.findViewById(R.id.rl_land);                  // ReleativeLayout
        rl_top = view.findViewById(R.id.rl_top);                    // Top part
        rl_bot =  view.findViewById(R.id.rl_bot);                   // Bottom part
        iv_port =  view.findViewById(R.id.iv_port);                 // 退出全屏
        iv_play_land =  view.findViewById(R.id.iv_play_land);       // 播放、暂停
        iv_finish = view.findViewById(R.id.iv_finish);              // 返回按钮
        tv_curtime_land = view.findViewById(R.id.tv_curtime_land);  // 当前时间
        tv_tottime_land = view.findViewById(R.id.tv_tottime_land);  // 播放时长
        sb_play_land = view.findViewById(R.id.sb_play_land);        // 进度条
        tv_title = view.findViewById(R.id.tv_title);
        sb_play_land.setThumb(new DrawableUtils(context).getNewDrawable(videoActivity, R.drawable.polyv_btn_slider, 50, 50));

        iv_port.setOnClickListener(this);
        iv_play_land.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        sb_play_land.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    /**
     * 根据视频信息加载控制器
     */
    public void preparedView() {
        if (videoView != null) {
            videoVO = videoView.getVideo();
            if (videoVO != null)
                tv_title.setText(videoVO.getTitle());
            int totalTime = videoView.getDuration();
            tv_tottime.setText(TimeUtils.generateTime(totalTime));
            tv_tottime_land.setText(TimeUtils.generateTime(totalTime));
        }
        // 视频准备完成后，开启随手势自动切换屏幕
        if (ScreenUtils.isLandscape(context))
            sensorHelper.toggle(true, false);
        else
            sensorHelper.toggle(true, true);
    }

    /**
     * 根据视频的播放状态去暂停或播放
     */
    private void playOrPause() {
        if (videoView != null) {
            if (videoView.isPlaying()) {
                videoView.pause();
                iv_play.setSelected(true);
//                iv_play_land.setSelected(true);
            } else {
                videoView.start();
                iv_play.setSelected(false);
//                iv_play_land.setSelected(false);
            }
        }
    }

    @Override
    public void setMediaPlayer(IPolyvVideoView player) {
        super.setMediaPlayer(player);
        videoView = (PolyvVideoView) player;
    }

    /**
     * 更新显示的播放进度，以及暂停/播放按钮
     */
    private void showProgress() {
        if (isShowing && videoView != null) {
            // 单位：毫秒
            int position = videoView.getCurrentPosition();
            int totalTime = videoView.getDuration() / 1000 * 1000;
            if (videoView.isCompletedState() || position > totalTime)
                position = totalTime;
            int bufPercent = videoView.getBufferPercentage();
            //在拖动进度条的时候，这里不更新
            if (!status_dragging) {
                tv_curtime.setText(TimeUtils.generateTime(position));
                tv_curtime_land.setText(TimeUtils.generateTime(position));
                if (totalTime > 0) {
                    sb_play.setProgress((int) (1000L * position / totalTime));
                    sb_play_land.setProgress((int) (1000L * position / totalTime));
                } else {
                    sb_play.setProgress(0);
                    sb_play_land.setProgress(0);
                }
            }
            sb_play.setSecondaryProgress(1000 * bufPercent / 100);
            sb_play_land.setSecondaryProgress(1000 * bufPercent / 100);
            if (videoView.isPlaying()) {
                iv_play.setSelected(false);
                iv_play_land.setSelected(false);
            } else {
                iv_play.setSelected(true);
                iv_play_land.setSelected(true);
            }
            handler.sendMessageDelayed(handler.obtainMessage(SHOW_PROGRESS), 1000 - (position % 1000));
        }
    }

    /**
     * SeekBar 事件触发
     */
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (!b) return;
            switch (seekBar.getId()) {
                case R.id.sb_play:
                case R.id.sb_play_land:
                    resetHideTime(longTime);
                    status_dragging = true;
                    if (videoView != null) {
                        int newPosition = (int) (videoView.getDuration() * (long) i / 1000);
                        tv_curtime.setText(TimeUtils.generateTime(newPosition));
                        tv_curtime_land.setText(TimeUtils.generateTime(newPosition));
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!seekBar.isSelected()) seekBar.setSelected(true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar.isSelected()) seekBar.setSelected(false);
            switch (seekBar.getId()) {
                case R.id.sb_play:
                case R.id.sb_play_land:
                    if (videoView != null) {
                        int seekToPosition = (int) (videoView.getDuration() * (long) seekBar.getProgress() / seekBar.getMax());
                        if (!videoView.isCompletedState()) {
                            videoView.seekTo(seekToPosition);
                        } else if (videoView.isCompletedState() && seekToPosition / 1000 * 1000 < videoView.getDuration() / 1000 * 1000) {
                            videoView.seekTo(seekToPosition);
                            videoView.start();
                        }
                    }
                    status_dragging = false;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:          //  播放、暂停
            case R.id.iv_play_land:
                playOrPause();
                break;
            case R.id.iv_land:          // 横竖屏切换（全屏与退出全屏）
            case R.id.iv_port:
                changeScape();
                break;
            case R.id.iv_finish:        // 横屏时退出播放（返回竖屏）
                changeScape();
                break;
            default:
                break;
        }
    }

    /**
     * 切换屏幕
     */
    public void changeScape () {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ScreenUtils.setPortrait(videoActivity);
            ScreenUtils.reSetStatusBar(videoActivity);
            initPortraitWH();
        } else {
            ScreenUtils.setLandscape(videoActivity);
            //初始为横屏时，状态栏需要隐藏
            ScreenUtils.hideStatusBar(videoActivity);
            //初始为横屏时，控制栏的宽高需要设置
            initLandScapeWH();
        }
    }

    /**
     * 初始化横屏控件状态
     */
    private void initLandScapeWH() {
        ViewGroup.LayoutParams vlp = parentView.getLayoutParams();
        vlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        vlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        rl_land.setVisibility(View.VISIBLE);
        rl_port.setVisibility(View.GONE);
    }

    /**
     * 初始化竖屏控件状态
     */
    private void initPortraitWH() {
        ViewGroup.LayoutParams vlp = parentView.getLayoutParams();
        vlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        vlp.height = (int) getResources().getDimension(R.dimen.playerHeight);
        rl_port.setVisibility(View.VISIBLE);
        rl_land.setVisibility(View.GONE);
    }

    /**
     * 重置设置布局的显示状态
     * @param isVisible
     */
    private void resetSetLayout(int isVisible) {
        if (isVisible == View.VISIBLE) {
            show(-1);
            resetTopBottomLayout(View.GONE);
        }
    }

    /**
     * 重置控制栏的顶部和底部布局以及进度条的显示状态
     * @param isVisible
     */
    private void resetTopBottomLayout(int isVisible) {
        resetTopBottomLayout(isVisible, false);
    }

    private void resetTopBottomLayout(int isVisible, boolean onlyHideTop) {
        rl_top.setVisibility(isVisible);
        if (!onlyHideTop) {
            rl_bot.setVisibility(isVisible);
            sb_play_land.setVisibility(isVisible);
        }
    }

    /**
     * 重置控制栏的隐藏时间
     * @param delayedTime
     */
    private void resetHideTime(int delayedTime) {
        handler.removeMessages(HIDE);
        if (delayedTime >= 0)
            handler.sendMessageDelayed(handler.obtainMessage(HIDE), delayedTime);
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void show(int timeout) {
        if (timeout < 0)
            status_showalways = true;
        else
            status_showalways = false;
        if (!isShowing) {
            resetTopBottomLayout(View.VISIBLE);
            //获取焦点
            requestFocus();
            isShowing = !isShowing;
            handler.removeMessages(SHOW_PROGRESS);
            handler.sendEmptyMessage(SHOW_PROGRESS);
            setVisibility(View.VISIBLE);
        }
        resetHideTime(timeout);
    }

    @Override
    public void show() {
        show(longTime);
    }

    @Override
    public void hide() {
        if (isShowing) {
            handler.removeMessages(HIDE);
            handler.removeMessages(SHOW_PROGRESS);
            resetSetLayout(View.GONE);
            isShowing = !isShowing;
            setVisibility(View.GONE);
        }
    }

    @Override
    public void setAnchorView(View view) {

    }

    /**
     * 退出播放器的Activity时需调用
     */
    public void disable() {
        hide();
        sensorHelper.disable();
    }

    /**
     * 关闭监听
     */
    public void pause() {
        sensorHelper.disable();
    }

    /**
     * 开启监听
     */
    public void resume() {
        sensorHelper.enable();
    }

}
