package com.github.lkqm.weixin.gateway.bean.message;

import com.github.lkqm.weixin.gateway.bean.WxBaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 位置信息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LocationMessage extends WxBaseMessage {

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 地理位置维度
     */
    private String locationX;

    /**
     * 地理位置经度
     */
    private String locationY;

    /**
     * 地图缩放倍数
     */
    private Integer scale;

    /**
     * 位置描述
     */
    private String label;
}
