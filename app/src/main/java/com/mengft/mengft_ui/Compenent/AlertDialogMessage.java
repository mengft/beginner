package com.mengft.mengft_ui.Compenent;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mengft.mengft_ui.R;
import com.mengft.mengft_ui.Utils.Metrics;

/**
 * Created by mengft on 2018/6/8.
 */

public class AlertDialogMessage extends AlertDialog implements View.OnClickListener {

    // 控件
    private TextView tv_alertdialog_message_title, tv_alertdialog_message_message;
    private Button btnPositive, btnNegative;
    //  传递参数
    private String title, message;
    private String positiveText = "确定", negativeText = "取消";
    // 定义确定、取消事件
    public interface AlertDialogMessageListener {
        void positiveClick();
        void negativeClick();
    }
    // 定义一个接口对象，供调用者实例化
    private AlertDialogMessageListener alertListener;

    public AlertDialogMessageListener getAlertListener() {
        return alertListener;
    }

    public void setAlertListener(AlertDialogMessageListener alertListener) {
        this.alertListener = alertListener;
    }

    // 传递Header参数
    public AlertDialogMessage(Context context, String title, String message) {
        super(context);
        this.title = title;
        this.message = message;
    }

    // 传递Header、Button参数
    public AlertDialogMessage(Context context, String title, String message, String positiveText, String negativeText) {
        super(context);
        this.title = title;
        this.message = message;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    /**
     * 配置自定义 View
     */
    public void setAlertView() {
        // 布局
        View view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_message, null);
        // 标题
        tv_alertdialog_message_title = view.findViewById(R.id.tv_alertdialog_message_title);
        tv_alertdialog_message_title.setText(title);
        // 信息
        tv_alertdialog_message_message = view.findViewById(R.id.tv_alertdialog_message_message);
        tv_alertdialog_message_message.setText(message);
        // Positive 按钮
        btnPositive = view.findViewById(R.id.bt_alertdialog_message_positive);
        btnPositive.setText(positiveText);
        btnPositive.setOnClickListener(this);
        // Negative 按钮
        btnNegative = view.findViewById(R.id.bt_alertdialog_message_negative);
        btnNegative.setText(negativeText);
        btnNegative.setOnClickListener(this);
        // 透明背景
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 配置自定义View（必须在onCreate之前，否则加载失败）
        setAlertView();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        // 在Show方法中直接调用
        setCancelable(false);
        create();
        super.show();

        // 设置 AlertDialog的 宽高和位置
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        layoutParams.width = (int) (new Metrics(getContext()).screenWidth() * 0.75);
        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        dialogWindow.setAttributes(layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_alertdialog_message_positive:
                if (alertListener != null) {
                    getAlertListener().positiveClick();
                }
                dismiss();
                break;
            case R.id.bt_alertdialog_message_negative:
                if (alertListener != null) {
                    getAlertListener().negativeClick();
                }
                dismiss();
                break;
            default:
                break;
        }
    }
}
