package com.github.lkqm.weixin.gateway.bean.message;

import com.github.lkqm.weixin.gateway.bean.WxBaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 语音消息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VoiceMessage extends WxBaseMessage {

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 语音媒体id
     */
    private String mediaId;

    /**
     * 语音格式
     */
    private String format;

    /**
     * 识别文字
     */
    private String recognition;

}
