/*
 * Preference      2018-02-08
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONObject;

/**
 * 键值对本地数据存储
 *
 * @author zch
 * @since 2018-02-08
 */
public class Preference extends Plugin {

    private static final String DEFAULT_WEB_PREFERENCE_FILE_NAME = "default_web_preference_file_name";

    @Override
    public PluginResult exec(String action, JSONObject args) throws ActionNotFoundException {
        switch (action) {
            case "set":
                return set(args);
            case "get":
                return get(args);
            default:
                throw new ActionNotFoundException("Preference", action);
        }
    }

    private PluginResult set(JSONObject args) {
        try {
            String key = args.optString("key");
            String value = args.optString("value");
            String fileName = args.optString("fileName");
            if (TextUtils.isEmpty(fileName)) {
                fileName = DEFAULT_WEB_PREFERENCE_FILE_NAME;
            }
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                    .edit()
                    .putString(key, value)
                    .apply();
            return new PluginResult("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new PluginResult(e.getMessage(), PluginResult.Status.ERROR);
        }
    }

    private PluginResult get(JSONObject args) {
        try {
            String key = args.optString("key");
            String defValue = args.optString("defValue");
            String fileName = args.optString("fileName");
            if (TextUtils.isEmpty(fileName)) {
                fileName = DEFAULT_WEB_PREFERENCE_FILE_NAME;
            }
            SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            String value = sp.getString(key, defValue);
            return new PluginResult(value);
        } catch (Exception e) {
            e.printStackTrace();
            return new PluginResult(e.getMessage(), PluginResult.Status.ERROR);
        }
    }
}