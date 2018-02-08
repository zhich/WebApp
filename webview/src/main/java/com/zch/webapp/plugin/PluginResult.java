/*
 * PluginResult      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 插件处理结果类
 *
 * @author zch
 * @since 2018-02-06
 */
public class PluginResult {

    public String message;
    private Status status = Status.OK;

    public PluginResult(String message, Status status) {
        this.message = message;
        this.status = status;
        status.ordinal();
    }

    public PluginResult(String message) {
        super();
        this.message = message;
    }

    public String getJSONString() {
        JSONObject jsonObject = new JSONObject();
        String json = "{}";
        try {
            JSONObject msgObject = new JSONObject(message);

            jsonObject.put("message", msgObject);
            jsonObject.put("status", status.ordinal());
            json = jsonObject.toString();
        } catch (JSONException e) {
            try {
                jsonObject.put("message", message);
                jsonObject.put("status", status.ordinal());
                json = jsonObject.toString();
            } catch (JSONException je) {
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * 获取异常JSON串
     *
     * @param e
     * @return
     */
    public static String getErrorJSON(Throwable e) {
        String msg = e.getMessage();
        return new PluginResult(msg == null ? "" : msg, Status.ERROR).getJSONString();
    }

    public static PluginResult newEmptyPluginResult() {
        return new PluginResult("");
    }

    public static PluginResult newErrorPluginResult(String message) {
        return new PluginResult(message, Status.ERROR);
    }

    public static PluginResult newErrorPluginResult(Exception e) {
        return PluginResult.newErrorPluginResult(e.getMessage());
    }

    public static enum Status {

        OK(0), ERROR(1), ILLIGAL(2);

        Status(int i) {

        }
    }
}