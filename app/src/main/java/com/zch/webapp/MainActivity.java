package com.zch.webapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zch.webapp.event.SetTitleEvent;
import com.zch.webapp.event.ShowBackEvent;
import com.zch.webapp.plugin.PluginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseWebActivity {

    @BindView(R2.id.tvBack)
    TextView mTvBack;
    @BindView(R2.id.tvTitle)
    TextView mTvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(ShowBackEvent event) {
        mTvBack.setVisibility(event.isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(SetTitleEvent event) {
        mTvTitle.setText(event.title);
    }
}
