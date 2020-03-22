package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.bean.WxBaseEvent;

/**
 * 客服消息事件处理
 */
@WxController
public class KfSessionHandler {

    /**
     * 客服会话创建事件
     */
    @WxEvent("kf_create_session")
    public void kfCreateSession(WxBaseEvent message) {
    }

    /**
     * 客服会话关闭事件
     */
    @WxEvent("kf_close_session")
    public void kfCloseSession(WxBaseEvent message) {
    }

    /**
     * 客服会话转接事件
     */
    @WxEvent("kf_switch_session")
    public void kfSwitchSession(WxBaseEvent message) {
    }
}