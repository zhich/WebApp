/*
 * Pay      2018-02-11
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.plugin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zch.webapp.WebActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 支付相关的插件
 *
 * @author zch
 * @since 2018-02-11
 */
public class Pay extends Plugin {

    @Override
    public PluginResult execAsyn(String action, JSONObject args, String requestID) throws ActionNotFoundException {
        switch (action) {
            case "alipay":
                return alipay(args, requestID);
            case "weChatPay":
                return weChatPay(args, requestID);
            default:
                throw new ActionNotFoundException("Pay", action);
        }
    }

    private PluginResult alipay(JSONObject args, String id) {
        final String money = args.optString("money");
        final String requestID = args.optString("requestID");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseBody = "成功支付了" + money + "元";
                Message msg = new Message();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("requestID", requestID);
                bundle.putString("responseBody", responseBody);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }).start();
        return null;
    }

    private PluginResult weChatPay(JSONObject args, String id) {
        return null;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    Bundle bundle = msg.getData();
                    String requestID = bundle.getString("requestID");
                    String responseBody = bundle.getString("responseBody");

                    JSONObject jsonObject = new JSONObject();
                    PluginResult pluginResult = null;
                    try {
                        jsonObject.put("responseBody", responseBody);
                        pluginResult = new PluginResult(jsonObject.toString());
                        ((WebActivity) context).asynLoadUrl(pluginResult.getJSONString(), requestID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}