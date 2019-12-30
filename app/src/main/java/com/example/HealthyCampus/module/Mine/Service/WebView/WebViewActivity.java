package com.example.HealthyCampus.module.Mine.Service.WebView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.VISIBLE;


public class WebViewActivity extends BaseActivity<WebViewContract.View, WebViewContract.Presenter> implements WebViewContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;

    @BindView(R.id.webView)
    WebView webView;

    private boolean isNetError = false;
    private AnimationDrawable loadAnimation;

    private String title;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_scenery);
    }

    @Override
    protected WebViewContract.Presenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected void initView() {
    }


    public static void actionShow(Context context, String title, String url, Map<String, String> extraHeaders) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("extraHeaders", (Serializable) extraHeaders);
        context.startActivity(intent);
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();

        String url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        Map<String, String> extraHeaders = (Map<String, String>) getIntent().getSerializableExtra("extraHeaders");
        tvTitle.setText(title);

        initWebViewSettings();
        if (extraHeaders == null || extraHeaders.size() == 0) {
            webView.loadUrl(url);
        } else {
            webView.loadUrl(url, extraHeaders);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        webView.setVerticalScrollBarEnabled(false);     //垂直滚动条不显示
        webView.setHorizontalScrollBarEnabled(false);   //水平不显示
        WebSettings webSettings = webView.getSettings();
        webSettings.setDisplayZoomControls(false);  //隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);       //屏幕适配:设置webview推荐使用的窗口，设置为true
        webSettings.setLoadWithOverviewMode(true);  //设置webview加载的页面的模式，也设置为true
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);           //是否支持缩放
        webSettings.setBuiltInZoomControls(true);   //添加对js功能的支持
        webSettings.setDomStorageEnabled(true);     // 存储(storage)、 开启DOM storage API 功能
        webSettings.setAppCacheEnabled(true);       // 开启Application Cache功能
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //webview cache默认不使用缓存
        webSettings.setAppCacheEnabled(false);//H5 CACHE

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            //访问地址错误回调
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isNetError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    Log.e("WebViewActivity" + "123456", "onPageFinished");
                    loadingData(false);
                    emptyLayout.setVisibility(!isNetError ? View.GONE : View.VISIBLE);
                    isNetError = false;
                    if (getString(R.string.mine_service_level_4and6_title).equals(title)) {
                        //去除广告
                        String js = getClearAdDivJs();
                        webView.loadUrl(js);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.contains("网页无法打开") || title.contains("404") || title.contains("500") || title.contains("Error"))
                    isNetError = true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("WebViewActivity" + "123456", "onProgressChanged");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.clearCache(true);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
        synCookies();
    }

    /**
     * 清除webview中的cookie
     */
    public void synCookies() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
    }

    private void loadingData(boolean val) {
        if (val) {
            loadAnimation.start();
            ivLoading.setVisibility(View.VISIBLE);
            NetworkLayout.setVisibility(View.GONE);
        } else {
            loadAnimation.stop();
            ivLoading.setVisibility(View.GONE);
            NetworkLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.NetworkLayout)      //重试
    public void NetworkLayout(View view) {
        loadingData(true);
        webView.reload();
    }

    private String getClearAdDivJs() {
        //getElementById()	返回对拥有指定 id 的第一个对象的引用。
        //getElementsByName()	返回带有指定名称的对象集合。
        //getElementsByTagName()	返回带有指定标签名的对象集合。
        //getElementsByClassName   返回指定class的对象的集合。
        String js = "javascript:" + "(function(){ ";
        js += "var adDiv" + "= document.getElementsByClassName('" + "wx-cet-ad" + "')[0];";
        js += "if(adDiv!=null) adDiv.style.display=\"none\";";
//        js += "if(adDiv!=null) adDiv.parentNode.removeChild(adDiv);";
        js += "})()";
        return js;
    }

}
