package com.github.lkqm.weixin.gateway.bean.event;

import com.github.lkqm.weixin.gateway.bean.WxBaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 公众号扫码事件消息(包括订阅)
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ScanEventMessage extends WxBaseEvent {

    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    private String eventKey;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String ticket;
}
