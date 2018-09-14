package com.mengft.mengft_ui.Home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mengft.mengft_ui.Component.CircleLoading;
import com.mengft.mengft_ui.Component.TopBar;
import com.mengft.mengft_ui.R;

/**
 * Created by mengft on 2018/6/20.
 */

@Route(path = "/home/ArticleReaderActivity")
public class ArticleReaderActivity extends Activity {

    private WebView webView;
    private TopBar topBar;
    private CircleLoading circleLoading;

    @Autowired
    String title, url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_article_reader);
        // 自动注入，以接收参数
        ARouter.getInstance().inject(this);

        bindTopBar();
        bindWebView();
    }

    /**
     * 绑定组件 TopBar
     */
    private void bindTopBar () {
        topBar = findViewById(R.id.tb_article_reader);
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
     * 绑定加载 CircleLoading
     */
    private void bindCircleLoading () {
        circleLoading = findViewById(R.id.cl_article_reader);
    }

    /**
     * 绑定控件 WebView
     */
    private void bindWebView() {

        webView = findViewById(R.id.wv_article_reader);

        // WebChromeClient  WebView辅助类
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(title, "获取到标题");
            }
        });

        // WebViewClient  WebView辅助类
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                setCircleLoadingVisible(View.VISIBLE);
                Log.e(title, "开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                setCircleLoadingVisible(View.INVISIBLE);
                Log.e(title, "加载完成");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                setCircleLoadingVisible(View.INVISIBLE);
                Log.e(title, "加载失败");
            }

        });

        webView.loadUrl(url);
        Log.e(title, url);
    }

    /**
     * 根据WebView 加载状态控制Loading 的显示
     * @param visible
     */
    private void setCircleLoadingVisible (int visible) {
        if (circleLoading == null) {
            bindCircleLoading();
        }
        circleLoading.setVisibility(visible);
    }
}
