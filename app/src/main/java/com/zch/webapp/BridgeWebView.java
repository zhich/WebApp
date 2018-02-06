/*
  * BridgeWebView      2018-02-06
  * Copyright (c) 2018 zch. All right reserved.
  *
  */
package com.zch.webapp;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 自定义 WebView
 *
 * @author zch
 * @since 2018-02-06
 */
public class BridgeWebView extends WebView {

    public BridgeWebView(Context context) {
        this(context, null);
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        this.setVerticalScrollBarEnabled(false);
        this.requestFocusFromTouch();
    }
}