package com.mengft.mengft_ui.Compenent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/5/3.
 */

public class TopBar extends RelativeLayout {

    // 声明需要的组件
    private View leftView, rightView, titleView;

    private float leftTextSize;
    private int leftTextColor;
    private String leftType, leftText;

    private float rightTextSize;
    private int rightTextColor;
    private String rightType, rightText;

    private String title;
    private int titleTextColor;
    private float titleTextSize;

    private Drawable background;

    // 定义布局属性
    private LayoutParams leftParams, rightParams, titleParams;

    // 定义一个接口、两个方法
    public interface TopBarClickListener {
        void leftClick();
        void rightClick();
    }

    // 定义点击事件Listener
    private TopBarClickListener topBarClickListener;

    // 暴露一个方法用来设置点击事件，并映射用户传过来的listener
    public void setTopbarClickListener(TopBarClickListener listener) {
        topBarClickListener = listener;
    }

    public TopBarClickListener getTopBarClickListener() {
        return topBarClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 全局布局layout
        post(new Runnable() {
            @Override
            public void run() {
                setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.headerHeight)));
            }
        });
        setPadding(30, 0, 30, 0);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackground(getResources().getDrawable(R.drawable.topbar_border));

        // Step 1 通过 context.obtainStyledAttributes 得到 TypedArray对象，并拿到属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        titleView = getTitleView(context, typedArray);
        leftView = getLeftView(context, typedArray);
        rightView = getRightView(context, typedArray);
        // 回收，防止内存问题
        typedArray.recycle();

        // Step 2 new出控件

        // Step 3 把自定义属性赋给控件

        // Step 4 new出LayoutParams 设置宽高
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Step 5 设置规则，可以看出LayoutParams布局参数
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        // Step 6 添加到布局中
        addView(titleView, titleParams);

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(leftView, leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(rightView, rightParams);

        // Step 7 设置点击事件
        leftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topBarClickListener != null) {
                    topBarClickListener.leftClick();
                }
            }
        });

        rightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topBarClickListener != null) {
                    getTopBarClickListener().rightClick();
                }
            }
        });

    }

    /**
     * 获取TopBar左侧View
     * @param context
     * @param typedArray
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public View getLeftView(Context context, TypedArray typedArray) {

        leftType = typedArray.getString(R.styleable.TopBar_leftType);
        leftText = typedArray.getString(R.styleable.TopBar_leftText);
        leftTextSize = typedArray.getDimension(R.styleable.TopBar_leftTextSize, getResources().getDimensionPixelSize(R.dimen.headerIconSize));
        leftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor, getResources().getColor(R.color.colorHeader));

        TextView leftTextView;
        if (leftType == "text") {
            Log.i("类型", "text");
            leftTextView = new TextView(context);
        } else {
            Log.i("类型", "text");
            leftTextView = new TextViewIcon(context);
        }

        leftTextView.setText(leftText);
        leftTextView.setTextSize(leftTextSize);
        leftTextView.setGravity(Gravity.CENTER);
        leftTextView.setTextColor(leftTextColor);

        return leftTextView;
    }

    /**
     * 获取TopBar右侧View
     * @param context
     * @param typedArray
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public View getRightView(Context context, TypedArray typedArray) {

        rightType = typedArray.getString(R.styleable.TopBar_rightType);
        rightText = typedArray.getString(R.styleable.TopBar_rightText);
        rightTextSize = typedArray.getDimension(R.styleable.TopBar_rightTextSize, getResources().getDimensionPixelSize(R.dimen.headerTextSize));
        rightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor, getResources().getColor(R.color.colorHeader));

        TextView rightTextView;
        if (rightType == "text") {
            rightTextView = new TextView(context);
        } else {
            rightTextView = new TextViewIcon(context);
        }

        rightTextView.setText(rightText);
        rightTextView.setTextColor(rightTextColor);
        rightTextView.setTextSize(rightTextSize);
        rightTextView.setGravity(Gravity.CENTER);
        rightTextView.setBackground(getResources().getDrawable(R.drawable.button_withoutfeedback));

        return rightTextView;
    }

    /**
     * 获取Topbar标题部分
     * @param context
     * @param typedArray
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public View getTitleView(Context context, TypedArray typedArray) {

        title = typedArray.getString(R.styleable.TopBar_title);
        titleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, getResources().getDimension(R.dimen.headerTitleSize));
        titleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColor, getResources().getColor(R.color.colorHeader));

        TextView tv_title = new TextView(context);

        tv_title.setText(title);
        tv_title.setTextColor(titleTextColor);
        tv_title.setTextSize(titleTextSize);
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setBackground(background);

        return tv_title;
    }
}
