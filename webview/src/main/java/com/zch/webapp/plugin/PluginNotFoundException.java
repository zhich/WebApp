/*
  * PluginNotFoundException      2018-02-06
  * Copyright (c) 2018 zch. All right reserved.
  *
  */
package com.zch.webapp.plugin;

/**
 * 插件未找到异常，插件配置 plugins.xml 没有配置插件导致的错误
 *
 * @author zch
 * @since 2018-02-06
 */
public class PluginNotFoundException extends Throwable {

    private static final long serialVersionUID = 1L;

    private String pluginName;

    public PluginNotFoundException(String pluginName) {
        super();
        this.pluginName = pluginName;
    }

    @Override
    public String getMessage() {
        return "Plugin " + pluginName + " 未找到，请检查 plugins.xml 是否配置";
    }
}