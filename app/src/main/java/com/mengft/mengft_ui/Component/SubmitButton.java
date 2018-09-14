package com.mengft.mengft_ui.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mengft.mengft_ui.R;
import com.mengft.mengft_ui.Utils.Metrics;

/**
 * Created by mengft on 2018/6/19.
 */

public class SubmitButton extends LinearLayout {

    private Button button;
    private String title = "提交";

    public interface SubmitButtonOnClickListener {
        void onSubmitButtonClick();
    }

    public SubmitButtonOnClickListener submitButtonOnClickListener;

    public SubmitButtonOnClickListener getSubmitButtonOnClickListener() {
        return submitButtonOnClickListener;
    }

    public void setSubmitButtonOnClickListener(SubmitButtonOnClickListener submitButtonOnClickListener) {
        this.submitButtonOnClickListener = submitButtonOnClickListener;
    }

    public SubmitButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        // LayoutParams
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setPadding(0, 0, 0, 20);
        // 阴影
        setBackground(getResources().getDrawable(R.drawable.layout_button_submit));
        addView(bindButton(context, attrs));
    }

    /**
     * 绑定 Button
     * @param context
     * @param attrs
     * @return
     */
    private Button bindButton (Context context, AttributeSet attrs) {
        // 文字
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubmitButton);
        title = typedArray.getString(R.styleable.SubmitButton_buttonText);

        button = new Button(context);
        // BackGround
        button.setBackground(getResources().getDrawable(R.drawable.button_submit));
        button.setLayoutParams(new LayoutParams((int) (new Metrics(context).screenWidth() * 0.75), LayoutParams.WRAP_CONTENT));
        button.setText(title);
        button.setTextSize(16);
        button.setTextColor(getResources().getColor(R.color.colorWhite));
        // 去除自定义阴影效果
        button.setStateListAnimator(null);
        // 点击事件
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButtonOnClickListener.onSubmitButtonClick();
            }
        });
        // 回收
        typedArray.recycle();

        return button;
    }
}
