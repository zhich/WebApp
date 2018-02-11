/*
 * AndroidCallJsActivity      2018-02-08
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zch.webapp.base.BaseWebActivity;
import com.zch.webapp.plugin.PluginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Android 调用 Js 界面
 *
 * @author zch
 * @since 2018-02-08
 */
public class AndroidCallJsActivity extends BaseWebActivity {

    @BindView(R2.id.tvBack)
    TextView mTvBack;
    @BindView(R2.id.tvTitle)
    TextView mTvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_call_js);
        mContext = this;
        ButterKnife.bind(this);

        mTvTitle.setText("Android 调用 Js");

        mPluginManager = new PluginManager(this);
        mPluginManager.loadPlugin();
        super.init();

        loadLocalHtml("android_call_js.html", "");
    }

    @OnClick({R2.id.tvBack, R2.id.btnGetMoney, R2.id.btnSetName})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvBack) {
            finish();
        } else if (id == R.id.btnGetMoney) {
            androidCallJs("getMoney");
        } else if (id == R.id.btnSetName) {
            androidCallJs("setName", "马老板");
        }
    }
}