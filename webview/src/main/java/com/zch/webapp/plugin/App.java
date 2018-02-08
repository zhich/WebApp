/*
 * App      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import android.app.Activity;
import android.content.Intent;

import com.zch.webapp.event.SetTitleEvent;
import com.zch.webapp.event.ShowBackEvent;
import com.zch.webapp.module.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
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
            case "isShowBack":
                return isShowBack(args);
            case "setTitle":
                return setTitle(args);
            case "finish":
                return finish();
            case "jumpToNativePage":
                return jumpToNativePage(args);
            default:
                throw new ActionNotFoundException("App", action);
        }
    }

    /**
     * 是否显示返回箭头
     *
     * @param args
     * @return
     */
    private PluginResult isShowBack(JSONObject args) {
        boolean isShow = args.optBoolean("isShow");
        EventBus.getDefault().post(new ShowBackEvent(isShow));
        return PluginResult.newEmptyPluginResult();
    }

    /**
     * 设置标题文字内容
     *
     * @param args
     * @return
     */
    private PluginResult setTitle(JSONObject args) {
        String title = args.optString("title");
        EventBus.getDefault().post(new SetTitleEvent(title));
        return PluginResult.newEmptyPluginResult();
    }

    /**
     * 退出当前Activity
     *
     * @return
     */
    private PluginResult finish() {
        ((Activity) context).finish();
        return PluginResult.newEmptyPluginResult();
    }

    /**
     * 跳转到原生页面
     *
     * @param args
     * @return
     */
    private PluginResult jumpToNativePage(JSONObject args) {
        String viewName = args.optString("viewName");
        String loginTip = args.optString("loginTip");
        switch (viewName) {
            case "LoginActivity":
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("loginTip", loginTip);
                context.startActivity(intent);
                break;
            default:
                break;
        }
        return PluginResult.newEmptyPluginResult();
    }
}