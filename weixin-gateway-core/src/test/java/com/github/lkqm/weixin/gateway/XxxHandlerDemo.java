package com.github.lkqm.weixin.gateway;

import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.annotation.WxMessage;

public class XxxHandlerDemo {

    @WxController
    public static class XxxHandlerDemoNormal {
        @WxMessage("text")
        public void text() {
        }

        @WxEvent("invoice")
        public void invoice() {
        }
    }

    @WxController
    public static class XxxHandlerDemoDuplicate {
        @WxMessage("text")
        public void text1() {
        }

        @WxMessage("text")
        public void text2() {
        }
    }


}
