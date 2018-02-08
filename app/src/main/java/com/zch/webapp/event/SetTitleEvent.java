/*
 * SetTitleEvent      2018-02-08
 * Copyright © zch All Rights Reserved.
 *
 */
package com.zch.webapp.event;

/**
 * 设置标题
 *
 * @author zch
 * @since 2018-02-08
 */
public class SetTitleEvent {

    public String title;

    public SetTitleEvent(String title) {
        this.title = title;
    }
}