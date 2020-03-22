package com.github.lkqm.weixin.gateway.app.handler;

import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxMessage;
import com.github.lkqm.weixin.gateway.annotation.WxParam;

/**
 * 模版消息消息事件处理
 */
@WxController
public class TemplateMessageHandler {

    /**
     * 模版消息发送后响应事件
     */
    @WxMessage("TEMPLATESENDJOBFINISH")
    public void templateSendJobFinish(@WxParam("Status") String status) {
    }
}
