/*
 * AsynParams      2018-02-11
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import org.json.JSONObject;

import java.util.Hashtable;

/**
 * 存储异步参数插件
 *
 * @author zch
 * @since 2018-02-11
 */
public class AsynParams extends Plugin {

    private static final Hashtable<String, String> SERVICE_MAP = new Hashtable<>();
    private static final Hashtable<String, String> ACTION_MAP = new Hashtable<>();
    private static final Hashtable<String, String> ARGS_MAP = new Hashtable<>();

    @Override
    public PluginResult exec(String action, JSONObject args) throws ActionNotFoundException {
        switch (action) {
            case "setParams":
                String id = args.optString("id");
                SERVICE_MAP.put(id, args.optString("service"));
                ACTION_MAP.put(id, args.optString("action"));
                ARGS_MAP.put(id, args.optString("args"));
                return PluginResult.newEmptyPluginResult();
            default:
                throw new ActionNotFoundException("AsynParams", action);
        }
    }

    /**
     * 根据 id 获取 service 后，删除 service
     *
     * @param id
     * @return
     */
    public static String getServiceThenRemove(String id) {
        String service = SERVICE_MAP.get(id);
        SERVICE_MAP.remove(id);
        return service;
    }

    /**
     * 根据 id 获取 action 后，删除 action
     *
     * @param id
     * @return
     */
    public static String getActionThenRemove(String id) {
        String action = ACTION_MAP.get(id);
        ACTION_MAP.remove(id);
        return action;
    }

    /**
     * 根据 id 获取 args 后，删除 args
     *
     * @param id
     * @return
     */
    public static String getArgsThenRemove(String id) {
        String args = ARGS_MAP.get(id);
        ARGS_MAP.remove(id);
        return args;
    }
}