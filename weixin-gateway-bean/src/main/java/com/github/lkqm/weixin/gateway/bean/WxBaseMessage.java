package com.github.lkqm.weixin.gateway.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信基本消息体
 */
@Data
public class WxBaseMessage implements Serializable {

    /**
     * 开发者账号
     */
    protected String toUserName;

    /**
     * 发送方账号
     */
    protected String fromUserName;

    /**
     * 创建时间
     */
    protected Long createTime;

    /**
     * 消息类型
     */
    protected String msgType;


}
