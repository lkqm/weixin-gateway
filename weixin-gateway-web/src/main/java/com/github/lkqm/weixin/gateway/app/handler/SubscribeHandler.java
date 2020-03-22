package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.bean.WxBaseEvent;
import com.github.lkqm.weixin.gateway.bean.event.ScanEventMessage;

/**
 * 公众号关注事件
 */
@WxController
public class SubscribeHandler {

    /**
     * 订阅, 包括扫码后关注
     */
    @WxEvent("subscribe")
    public void subscribe(ScanEventMessage message) {
    }

    /**
     * 取消订阅
     */
    @WxEvent("unsubscribe")
    public void unsubscribe(WxBaseEvent message) {
    }

    /**
     * 扫码已经关注的情况
     */
    @WxEvent("SCAN")
    public void scan(ScanEventMessage message) {
    }
}