package com.github.lkqm.weixin.gateway;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信配置
 */
@Data
public class WxConfig implements Serializable {

    /**
     * 微信公众号appId
     */
    private String appId;

    /**
     * 微信公众号token
     */
    private String token;

    /**
     * 微信公众号aesKey
     */
    private String aesKey;

}
