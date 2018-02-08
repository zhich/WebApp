package com.zch.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zch.webapp.AndroidCallJsActivity;
import com.zch.webapp.WebActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnJsCallAndroid, R.id.btnAndroidCallJs})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnJsCallAndroid) {
            startActivity(new Intent(this, WebActivity.class));
        } else if (id == R.id.btnAndroidCallJs) {
            startActivity(new Intent(this, AndroidCallJsActivity.class));
        }
    }
}
