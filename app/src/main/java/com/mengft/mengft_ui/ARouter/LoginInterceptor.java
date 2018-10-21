package com.mengft.mengft_ui.ARouter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;

@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {

    private static String TAG = LoginInterceptor.class.getName();
    private Context context;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        // 是否要求权限
        if (!postcard.getExtras().getBoolean("needAuth", false)) {
            callback.onContinue(postcard);
            return;
        }
        // 判断是否拥有权限
        String access_token =  context.getSharedPreferences("user", 0).getString("access_token", null);
        boolean isLogin = access_token == null ? false : true;
        if (isLogin) {
            callback.onContinue(postcard);
        } else {
            Log.e(TAG, "即将跳转到Login界面");
            ARouter.getInstance().build("/login/LoginActivity").navigation(context);
        }
    }

    @Override
    public void init(Context context) {
        this.context = context;
        Log.e(TAG, "LoginInterceptor拦截器完成初始化");
    }

}
