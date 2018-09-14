package com.mengft.mengft_ui.Component;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/7/19.
 */
@Route(path = "/home/DeviceConfiguration")
public class DeviceConfiguration extends Activity implements SubmitButton.SubmitButtonOnClickListener, TopBar.TopBarClickListener {

    private static final String TAG = DeviceConfiguration.class.getSimpleName();

    private TextView textView;
    private SubmitButton submitButton;
    private TopBar topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_configuration);

        initView();
    }

    private void initView() {
        textView = findViewById(R.id.tv_configuration);
        submitButton = findViewById(R.id.sb_configuration);
        topBar = findViewById(R.id.tb_configuration);

        StringBuffer status = new StringBuffer();
        final Configuration configuration = getResources().getConfiguration();
        status.append("densityDpi" + configuration.densityDpi + "\n");
        status.append("fontCale" + configuration.fontScale + "\n");
        status.append("hardKeyboardHidden" + configuration.hardKeyboardHidden + "\n");
        status.append("keyboard" + configuration.keyboard + "\n");
        status.append("keyboardHidden" + configuration.keyboardHidden + "\n");
        status.append("locale" + configuration.locale + "\n");
        status.append("densityDpi" + configuration.mcc + "\n");
        status.append("mcc" + configuration.mnc + "\n");
        status.append("navigation" + configuration.navigation + "\n");
        status.append("navigationHidden" + configuration.navigationHidden + "\n");
        status.append("orientation" + configuration.orientation + "\n");
        status.append("screenHeightDp" + configuration.screenHeightDp + "\n");
        status.append("screenWidthDp" + configuration.screenWidthDp + "\n");
        status.append("screenLayout" + configuration.screenLayout + "\n");
        status.append("touchscreen" + configuration.touchscreen + "\n");
        textView.setText(status.toString());

        submitButton.setSubmitButtonOnClickListener(this);
        topBar.setTopbarClickListener(this);
    }

    @Override
    public void onSubmitButtonClick() {
        Toast.makeText(getBaseContext(), String.format("dasdad%s", "qq"), Toast.LENGTH_SHORT).show();
        Configuration currentConfiguration = getResources().getConfiguration();
        if (currentConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void leftClick() {
        Log.e(TAG, "finish");
        finish();
    }

    @Override
    public void rightClick() {

    }
}
