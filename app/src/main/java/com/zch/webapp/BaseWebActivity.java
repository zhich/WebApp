/*
 * BaseWebActivity      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zch.webapp.plugin.IPlugin;
import com.zch.webapp.plugin.PluginManager;
import com.zch.webapp.plugin.PluginNotFoundException;
import com.zch.webapp.plugin.PluginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * WebActivity 基类
 *
 * @author zch
 * @since 2018-02-06
 */
public class BaseWebActivity extends Activity {

    protected PluginManager mPluginManager;
    protected BridgeWebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView.setWebChromeClient(new WebServerChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
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

    private static class WebServerClient extends WebViewClient {

    }
}