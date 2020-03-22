package com.github.lkqm.weixin.gateway.bean.message;

import com.github.lkqm.weixin.gateway.bean.WxBaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 视频消息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VideoMessage extends WxBaseMessage {

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 语音媒体id
     */
    private String mediaId;

    /**
     * 缩略图媒体id
     */
    private String thumbMediaId;

}
