package com.github.lkqm.weixin.gateway;

import com.github.lkqm.weixin.gateway.util.StringUtils;
import com.github.lkqm.weixin.gateway.util.WxUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信调用入口处理器
 */
@Slf4j
@AllArgsConstructor
public class WxPortalHandler {

    private WxRouter router;

    /**
     * 服务器认证方法
     */
    public String get(WxConfig config, String signature, String timestamp, String nonce, String echostr) {
        log.info("receive wx authenticate message: [signature={}, timestamp={}, nonce={}, echostr={}]", signature, timestamp, nonce, echostr);
        String token = config.getToken();
        boolean pass = !StringUtils.isAnyEmpty(signature, timestamp, nonce, echostr)
                && WxUtils.checkSignature(token, timestamp, nonce, signature);

        return pass ? echostr : "Why? I don’t know";
    }

    /**
     * 消息处理方法
     */
    public String post(boolean dev, WxConfig config, String requestBody, String signature, String timestamp, String nonce, String openid, String encType, String msgSignature) {
        log.info("receive wx message: [openid={}, signature={}, encType={}, msgSignature={}, timestamp={}, nonce={}, requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
        String token = config.getToken();
        if (!dev && !WxUtils.checkSignature(token, timestamp, nonce, signature)) {
            throw new IllegalArgumentException("Signature verification failed");
        }

        String xml = requestBody;
        if ("AES".equalsIgnoreCase(encType)) {
            xml = WxUtils.decryptXml(config, requestBody);
        }
        Message message = Message.createFromXml(xml);
        router.route(message);
        return "";
    }

}