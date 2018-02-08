/*
 * LoginActivity      2018-02-08
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.module.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zch.webapp.R;
import com.zch.webapp.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 *
 * @author zch
 * @since 2018-02-08
 */
public class LoginActivity extends Activity {

    @BindView(R2.id.tvTitle)
    TextView mTvTitle;
    @BindView(R2.id.tvTip)
    TextView mTvTip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mTvTitle.setText("登录");
        mTvTip.setText(getIntent().getStringExtra("loginTip"));
    }

    @OnClick({R2.id.tvBack})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvBack) {
            finish();
        }
    }
}