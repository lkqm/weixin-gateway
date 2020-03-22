package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.bean.invoice.InvoiceStatusUpdateEventMessage;

/**
 * 开票平台相关事件
 */
@WxController
public class InvoicePlatformHandler {

    /**
     * 电子发票状态
     */
    @WxEvent("update_invoice_status")
    public void updateInvoiceStatusEvent(InvoiceStatusUpdateEventMessage message) {
    }

}