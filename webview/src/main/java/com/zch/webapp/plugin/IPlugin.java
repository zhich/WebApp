/*
 * IPlugin      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

/**
 * 插件接口
 *
 * @author zch
 * @since 2018-02-06
 */
public interface IPlugin {

    public static final String SERVICE = "service";
    public static final String ACTION = "action";
    public static final String ARGS = "args";

    /**
     * 同步请求
     *
     * @param action 功能
     * @param args   参数
     * @return
     * @throws ActionNotFoundException
     */
    public PluginResult exec(String action, JSONObject args) throws ActionNotFoundException;

    /**
     * 异步请求
     *
     * @param action    功能
     * @param args      参数
     * @param requestID 请求 id
     * @return
     * @throws ActionNotFoundException
     */
    public PluginResult execAsyn(String action, JSONObject args, String requestID) throws ActionNotFoundException;

    public void setContext(Context context);

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onCreate(Bundle savedInstanceState);

    public void onPause();

    public void onResume();

    public void onStart();

    public void onRestart();

    public void onStop();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public void onRestoreInstanceState(Bundle savedInstanceState);
}