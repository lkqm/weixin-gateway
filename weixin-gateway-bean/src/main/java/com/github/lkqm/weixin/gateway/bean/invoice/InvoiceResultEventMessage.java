package com.github.lkqm.weixin.gateway.bean.invoice;

import com.github.lkqm.weixin.gateway.bean.WxBaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 发票用户授权事件消息
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InvoiceResultEventMessage extends WxBaseEvent {

    /**
     * 发票请求流水号，唯一识别发票请求的流水号
     */
    private String fpqqlsh;

    /**
     * 纳税人识别码
     */
    private String nsrsbh;

    /**
     * 开票结果：2失败 100成功
     */
    private Integer status;

}
