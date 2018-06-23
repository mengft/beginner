package com.mengft.mengft_ui.Login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Compenent.SubmitButton;
import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/6/19.
 */

@Route(path = "/login/LoginActivity")
public class LoginActivity extends Activity {

    private SubmitButton submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

//        ARouter.openLog();  // 打印日志
//        ARouter.openDebug();    // 开启调试模式
//        ARouter.init(getApplication()); // 推荐在Application中初始化

        bindView();
    }

    /**
     * 绑定 Button
     */
    private void bindView() {
        submitButton = findViewById(R.id.sb_login);
        submitButton.setSubmitButtonOnClickListener(new SubmitButton.SubmitButtonOnClickListener() {
            @Override
            public void onSubmitButtonClick() {
                Toast.makeText(getBaseContext(), String.format("%s%s", "登录", "成功"), Toast.LENGTH_SHORT).show();
                ARouter.getInstance().build("/main/MainActivity").navigation();
            }
        });
    }
}
