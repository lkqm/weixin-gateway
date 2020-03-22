package com.github.lkqm.weixin.gateway.app.handler;

import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxMessage;
import com.github.lkqm.weixin.gateway.bean.message.*;

/**
 * 普通消息处理
 */
@WxController
public class MessageHandler {

    @WxMessage("text")
    public void text(TextMessage textMessage) {
    }

    @WxMessage("image")
    public void image(ImageMessage imageMessage) {
    }

    @WxMessage("voice")
    public void voice(VoiceMessage voiceMessage) {
    }

    @WxMessage("video")
    public void video(VideoMessage videoMessage) {
    }

    @WxMessage("shortvideo")
    public void shortVideo(VideoMessage videoMessage) {
    }

    @WxMessage("link")
    public void link(LinkMessage linkMessage) {
    }

    @WxMessage("location")
    public void location(LocationMessage locationMessage) {
    }
}
