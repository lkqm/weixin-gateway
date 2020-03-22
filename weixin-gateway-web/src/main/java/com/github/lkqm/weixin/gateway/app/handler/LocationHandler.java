package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.bean.event.LocationEventMessage;

/**
 * 地理消息上报事件
 */
@WxController
public class LocationHandler {

    /**
     * 定位信息上报事件
     */
    @WxEvent("location")
    public void location(LocationEventMessage message) {
    }

}