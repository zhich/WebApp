/*
 * App      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 应用插件
 *
 * @author zch
 * @since 2018-02-06
 */
public class App extends Plugin {

    @Override
    public PluginResult exec(String action, JSONObject args) throws ActionNotFoundException {
        switch (action) {
            case "isShowBack": // 是否显示返回箭头
                return isShowBack(args);
        }
        throw new ActionNotFoundException("App", action);
    }

    private PluginResult isShowBack(JSONObject args) {
        try {
            boolean isShow = args.getBoolean("isShow");
            context.isShowBack(isShow);
            return PluginResult.newEmptyPluginResult();
        } catch (JSONException e) {
            e.printStackTrace();
            return PluginResult.newErrorPluginResult(e.getMessage());
        }
    }
}