package com.mengft.mengft_ui.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    private static final String TAG = OkHttpUtils.class.getName();
    private Context context;

    public OkHttpUtils(Context context) {
        this.context = context;
    }

    public void getAction (String url, OKHttpCallback okHttpCallback) {

        Headers headers = new Headers.Builder()
                .add("X-Device-Info", getDeviceInfo())
                .add("authorization", getAccesToken())
                .add("Cache-Control", "no-cache")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .get()
                .build();

        executeAction(request, okHttpCallback);
    }

    /**
     * POST封装
     * @param url
     * @param formBody
     */
    public void postAction (String url, FormBody formBody, OKHttpCallback okHttpCallback) {

        Headers headers = new Headers.Builder()
                .add("X-Device-Info", getDeviceInfo())
                .add("authorization", getAccesToken())
                .add("Cache-Control", "no-cache")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(formBody)
                .build();

        executeAction(request, okHttpCallback);
    }

    /**
     * 执行方法
     * @param request
     * @return
     */
    public void executeAction (final Request request, final OKHttpCallback okHttpCallback) {
        // 开辟Http请求子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                new OkHttpClient().newCall(request).enqueue(new Callback() {

                    // {"status":{"code":"300002","message":"params unvalid or expired error","userMessage":"课程类型有误"},"data":null,"logid":"384142847703","ts":50}
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "OKHttp Action onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject responseJson = new JSONObject();
                            if (response.isSuccessful()) {
                                responseJson = new JSONObject(response.body().string());
                                Log.e(TAG, responseJson.toString());
                                // 判断status下的code是否为"000000"
                                String code = responseJson.getJSONObject("status").getString("code");

                                if (code.trim().equals("000000")) {
                                    okHttpCallback.success(responseJson);
                                } else {
                                    String message = responseJson.getJSONObject("status").getString("message");

                                    responseJson.put("code", code);
                                    responseJson.put("message", message);
                                    okHttpCallback.failed(responseJson);
                                    Toast.makeText(context, String.format("%d, %s", code, message), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                responseJson.put("code", response.code());
                                responseJson.put("message", response.message());
                                okHttpCallback.failed(responseJson);
                                Toast.makeText(context, String.format("%d, %s", response.code(), response.message()), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            response.body().close();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 获取用户TOKEN
     * @return
     */
    public String getAccesToken () {
        String access_token = context.getSharedPreferences("user", 0).getString("access_token", "");
        return "Bearer " + access_token;
    }

    /**
     * 获取设备配置信息（加入Header）
     * @return
     */
    public String getDeviceInfo () {

        JsonObject deviceObject = new JsonObject();

        deviceObject.addProperty("appVersion", "1.1.2");
        deviceObject.addProperty("devicePlatform", "android");
        deviceObject.addProperty("pushType", "XIAOMI");
        deviceObject.addProperty("jpushRegistrationId", "123456");
        deviceObject.addProperty("clientId", "BlueVein");

        return deviceObject.toString();
    }

}
