package com.github.lkqm.weixin.gateway.bean.invoice;

import com.github.lkqm.weixin.gateway.bean.WxBaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 发票状态变化事件
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InvoiceStatusUpdateEventMessage extends WxBaseEvent {

    /**
     * 发票code
     */
    private String code;

    /**
     * 发票id
     */
    private String cardId;

    /**
     * 发票报销状态
     */
    private String status;

}
