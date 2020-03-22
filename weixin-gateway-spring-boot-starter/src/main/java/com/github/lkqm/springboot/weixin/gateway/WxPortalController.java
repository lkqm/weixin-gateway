package com.github.lkqm.springboot.weixin.gateway;

import com.github.lkqm.weixin.gateway.WxConfig;
import com.github.lkqm.weixin.gateway.WxPortalHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 微信回掉入口控制器
 */
@RestController
@RequestMapping({"${wx.gateway.prefix:/wx/gateway}", "${wx.gateway.prefix:/wx/gateway}/{key}"})
@AllArgsConstructor
@Slf4j
public class WxPortalController {

    private WxPortalHandler handler;
    private WxGatewayProperties properties;

    /**
     * 服务器认证方法
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String get(@PathVariable(required = false) String key, String signature, String timestamp, String nonce, String echostr) {
        WxConfig config = getWxMpConfig(key);
        return handler.get(config, signature, timestamp, nonce, echostr);
    }

    /**
     * 消息处理方法
     */
    @PostMapping(produces = "application/xml; charset=utf-8")
    public String post(@PathVariable(required = false) String key,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestBody String requestBody) {
        WxConfig config = getWxMpConfig(key);
        return handler.post(properties.isDev(), config, requestBody, signature, timestamp, nonce, openid, encType, msgSignature);
    }

    private WxConfig getWxMpConfig(String key) {
        if (key == null) {
            key = "default";
        }
        Map<String, WxConfig> configs = properties.getConfigs();
        WxConfig config = configs.get(key);
        if (config == null) {
            throw new RuntimeException("未找到对应微信配置key=" + key);
        }
        return config;
    }
}