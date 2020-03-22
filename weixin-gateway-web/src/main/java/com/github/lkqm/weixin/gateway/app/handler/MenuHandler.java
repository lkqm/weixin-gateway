package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.annotation.WxParam;

/**
 * 菜单相关事件
 */
@WxController
public class MenuHandler {

    /**
     * 点击菜单拉取消息时的事件
     */
    @WxEvent("click")
    public void click(@WxParam("EventKey") String eventKey) {
    }

    /**
     * 点击菜单跳转链接时的事件
     */
    @WxEvent("view")
    public void view(@WxParam("EventKey") String eventKey, @WxParam("MenuID") String menuId) {
    }
}
