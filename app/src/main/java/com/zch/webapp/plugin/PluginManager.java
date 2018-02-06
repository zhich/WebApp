/*
  * PluginManager      2018-02-06
  * Copyright (c) 2018 zch. All right reserved.
  *
  */
package com.zch.webapp.plugin;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

/**
 * 插件管理类
 *
 * @author zch
 * @since 2018-02-06
 */
public class PluginManager {

    private static final String TAG = PluginManager.class.getSimpleName();

    private Context mContext;
    private HashMap<String, String> mNameClassMap = new HashMap<String, String>();
    private HashMap<String, IPlugin> mPluginMap = new HashMap<String, IPlugin>();

    public PluginManager(Context context) {
        this.mContext = context;
    }

    /**
     * 同步请求
     *
     * @param service
     * @param action
     * @param args
     * @return
     * @throws PluginNotFoundException
     */
    public String exec(String service, String action, JSONObject args) throws PluginNotFoundException {
        IPlugin plugin = getPlugin(service);
        try {
            PluginResult result = plugin.exec(action, args);
            if (result == null) {
                return null;
            }
            return result.getJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return PluginResult.getErrorJSON(e);
        } catch (ActionNotFoundException e) {
            e.printStackTrace();
            return PluginResult.getErrorJSON(e);
        }
    }

    private IPlugin getPlugin(String pluginName) throws PluginNotFoundException {
        String className = mNameClassMap.get(pluginName);
        if (className == null) {
            throw new PluginNotFoundException(pluginName);
        }
        if (mPluginMap.containsKey(className)) {
            return mPluginMap.get(className);
        } else {
            return createPlugin(className);
        }
    }

    private IPlugin createPlugin(String className) {
        IPlugin plugin = null;
        try {
            Class c = Class.forName(className);
            plugin = (IPlugin) c.newInstance();
            plugin.setContext(mContext);
            mPluginMap.put(className, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plugin;
    }

    /**
     * 加载 plugins.xml 里面的插件
     */
    public void loadPlugin() {
        int identifier = mContext.getResources().getIdentifier("plugins", "xml", mContext.getPackageName());
        if (identifier == 0) {
            Log.e(TAG, "ERROR : mPlugins.xml is missing.  Add res/xml/mPlugins.xml to your project .");
        }
        XmlResourceParser xml = mContext.getResources().getXml(identifier);
        try {
            int eventType = -1;
            while ((eventType = xml.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String name = xml.getName();
                    if ("plugin".equals(name)) {
                        mNameClassMap.put(xml.getAttributeValue(null, "name"), xml.getAttributeValue(null, "class"));
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}