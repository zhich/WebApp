/*
 * Plugin      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import android.content.Context;

import org.json.JSONObject;

/**
 * 插件类
 *
 * @author zch
 * @since 2018-02-06
 */
public abstract class Plugin implements IPlugin {

    protected Context context;

    @Override
    public PluginResult execAsyn(String action, JSONObject args, String requestID) throws ActionNotFoundException {
        return null;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}