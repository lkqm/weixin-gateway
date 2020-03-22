package com.github.lkqm.weixin.gateway.bean.message;

import com.github.lkqm.weixin.gateway.bean.WxBaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 文本消息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LinkMessage extends WxBaseMessage {

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 链接地址
     */
    private String url;
}
