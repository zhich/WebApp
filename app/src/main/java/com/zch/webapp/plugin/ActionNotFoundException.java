/*
 * ActionNotFoundException      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

/**
 * action 找不到异常类
 *
 * @author zch
 * @since 2018-02-06
 */
public class ActionNotFoundException extends Throwable {

    private static final long serialVersionUID = 1L;

    private String actionName;
    private String serviceName;

    public ActionNotFoundException(String serivceName, String actionName) {
        super();
        this.serviceName = serivceName;
        this.actionName = actionName;
    }

    @Override
    public String getMessage() {
        return serviceName + " 中的  " + actionName + " action 未找到，请检查 serviceName 是否支持";
    }
}