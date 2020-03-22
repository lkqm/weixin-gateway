package com.github.lkqm.weixin.gateway.bean.event;

import com.github.lkqm.weixin.gateway.bean.WxBaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 上报地理位置事件消息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LocationEventMessage extends WxBaseEvent {

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 精确度
     */
    private String precision;
}
