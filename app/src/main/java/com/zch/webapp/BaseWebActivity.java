/*
 * BaseWebActivity      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zch.webapp.plugin.IPlugin;
import com.zch.webapp.plugin.PluginManager;
import com.zch.webapp.plugin.PluginNotFoundException;
import com.zch.webapp.plugin.PluginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * WebActivity 基类
 *
 * @author zch
 * @since 2018-02-06
 */
public class BaseWebActivity extends Activity {

    protected FrameLayout mWebFrameLayout;
    protected BridgeWebView mWebView;
    protected LinearLayout mLoadingLayout;

    protected PluginManager mPluginManager;

    protected void init() {
        mLoadingLayout = (LinearLayout) findViewById(R.id.ll_loading);
        mWebFrameLayout = (FrameLayout) findViewById(R.id.webFrameLayout);

        mWebView = new BridgeWebView(getApplicationContext());
        mWebFrameLayout.addView(mWebView, 0);

        mWebView.setWebChromeClient(new WebServerChromeClient());
        mWebView.setWebViewClient(new WebServerClient());
    }

    /**
     * 加载本地 html
     *
     * @param htmlFileName html 文件名
     * @param dir          路径
     */
    protected void loadLocalHtml(String htmlFileName, String dir) {
        String url = null;
        if (TextUtils.isEmpty(dir)) {
            url = "file:///android_asset" + File.separator + htmlFileName;
        } else {
            url = "file:///android_asset" + File.separator + dir + File.separator + htmlFileName;
        }
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        releaseWebView();
        super.onDestroy();
    }

    private void releaseWebView() {
        if (mWebView != null) {
            try {
                if (mWebView.getParent() != null) {
                    ((ViewGroup) mWebView.getParent()).removeView(mWebView);
                }
                mWebView.destroy();
            } catch (IllegalArgumentException e) {
            }
            mWebView = null;
        }
    }

    private class WebServerChromeClient extends WebChromeClient {

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            JSONObject args = null;
            JSONObject head = null;
            try {
                head = new JSONObject(message);
                if (!TextUtils.isEmpty(defaultValue) && !"null".equals(defaultValue)) {
                    try {
                        args = new JSONObject(defaultValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String execResult = mPluginManager.exec(head.getString(IPlugin.SERVICE), head.getString(IPlugin.ACTION), args);
                result.confirm(execResult);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                result.confirm(PluginResult.getErrorJSON(e));
                return true;
            } catch (PluginNotFoundException e) {
                e.printStackTrace();
                result.confirm(PluginResult.getErrorJSON(e));
                return true;
            }
        }
    }

    private class WebServerClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mLoadingLayout.setVisibility(View.VISIBLE);
            mWebFrameLayout.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoadingLayout.setVisibility(View.GONE);
            mWebFrameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受所有网站的证书
        }
    }
}