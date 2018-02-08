/*
 * WebActivity      2018-02-06
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zch.webapp.base.BaseWebActivity;
import com.zch.webapp.event.SetTitleEvent;
import com.zch.webapp.event.ShowBackEvent;
import com.zch.webapp.plugin.PluginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 装载 web 的 Activity
 *
 * @author zch
 * @since 2018-02-06
 */
public class WebActivity extends BaseWebActivity {

    @BindView(R2.id.tvBack)
    TextView mTvBack;
    @BindView(R2.id.tvTitle)
    TextView mTvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mContext = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        mTvTitle.setText("Js 调用 Android");

        mPluginManager = new PluginManager(this);
        mPluginManager.loadPlugin();
        super.init();

        loadLocalHtml("index.html", "");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R2.id.tvBack})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvBack) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(ShowBackEvent event) {
        mTvBack.setVisibility(event.isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(SetTitleEvent event) {
        mTvTitle.setText(event.title);
    }
}
