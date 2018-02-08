/*
 * ShowBackEvent      2018-02-08
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.event;

/**
 * 是否显示返回键
 *
 * @author zch
 * @since 2018-02-08
 */
public class ShowBackEvent {

    public boolean isShow;

    public ShowBackEvent(boolean isShow) {
        this.isShow = isShow;
    }
}