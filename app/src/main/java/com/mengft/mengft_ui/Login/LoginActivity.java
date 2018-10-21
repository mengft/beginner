package com.mengft.mengft_ui.Login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mengft.mengft_ui.Component.SubmitButton;
import com.mengft.mengft_ui.R;
import com.mengft.mengft_ui.Utils.MD5Utils;
import com.mengft.mengft_ui.Utils.OKHttpCallback;
import com.mengft.mengft_ui.Utils.OkHttpUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by mengft on 2018/6/19.
 */

@Route(path = "/login/LoginActivity")
public class LoginActivity extends Activity implements SubmitButton.SubmitButtonOnClickListener {

    private static String TAG = LoginActivity.class.getName();
    private SubmitButton submitButton;
    private EditText editTextUsername, editTextPassword;
    private String username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ARouter.openLog();  // 打印日志
        ARouter.openDebug();    // 开启调试模式
        ARouter.init(getApplication()); // 推荐在Application中初始化

        bindView();
    }

    /**
     * 绑定 Button
     */
    private void bindView() {
        submitButton = findViewById(R.id.sb_login);
        submitButton.setSubmitButtonOnClickListener(this);
    }

    @Override
    public void onSubmitButtonClick() {
        editTextUsername = findViewById(R.id.login_username);
        editTextPassword = findViewById(R.id.login_password);
        username= editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            doLogin();
        }
    }

    /**
     * 提交登录信息
     */
    private void doLogin () {

        FormBody formBody = new FormBody.Builder()
                .add("client_id", "BlueVein")
                .add("grant_type", "password")
                .add("username", username)
                .add("password", MD5Utils.md5Password(password))
                .build();

        String url = "https://service1dev.healscitech.com/oidcserver/token";

        new OkHttpUtils(this).postAction(url, formBody, new OKHttpCallback() {
            @Override
            public void success(JSONObject successJson) {

                String access_token = successJson.optString("access_token", "");

                SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (editor.putString("access_token", access_token).commit()) {
                    Log.e(TAG, "登录成功");
                    ARouter.getInstance().build("/main/MainActivity").navigation();
                }
            }

            @Override
            public void failed(JSONObject failedJson) {
                Log.e(TAG, failedJson.optString("code") + " " + failedJson.optString("message"));
            }
        });
    }
}
