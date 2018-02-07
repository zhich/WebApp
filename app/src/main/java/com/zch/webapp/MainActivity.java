package com.zch.webapp;

import android.os.Bundle;

import com.zch.webapp.plugin.PluginManager;

public class MainActivity extends BaseWebActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPluginManager = new PluginManager(this);
        mPluginManager.loadPlugin();
        super.init();

        loadLocalHtml("index.html", "");
    }

}
