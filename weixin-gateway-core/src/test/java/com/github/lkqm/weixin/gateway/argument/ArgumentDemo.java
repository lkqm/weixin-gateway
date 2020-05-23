package com.github.lkqm.weixin.gateway.argument;

import com.github.lkqm.weixin.gateway.annotation.WxBody;
import com.github.lkqm.weixin.gateway.annotation.WxParam;
import lombok.Data;

import java.io.Serializable;

public class ArgumentDemo {

    public void primitive(int a, boolean b) {
    }

    public void wxParam(@WxParam("FromUserName") String fromUserName, @WxParam("__XXX__") String mix, String none) {
    }

    public void wxBody(@WxBody String xml, @WxBody(WxBody.Type.JSON_CAMEL) String json) {
    }

    public void customResolver(User user) {
    }

    ;

    @Data
    public static class User implements Serializable {

        private String openid;

    }

}
