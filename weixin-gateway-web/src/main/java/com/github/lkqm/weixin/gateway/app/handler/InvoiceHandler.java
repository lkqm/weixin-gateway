package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.bean.invoice.InvoiceAuthEventMessage;
import com.github.lkqm.weixin.gateway.bean.invoice.InvoiceResultEventMessage;

/**
 * 商户电子发票相关事件
 */
@WxController
public class InvoiceHandler {

    /**
     * 收取授权完成事件
     */
    @WxEvent("user_authorize_invoice")
    public void authorEvent(InvoiceAuthEventMessage message) {
    }

    /**
     * 统一开票接口-异步通知开票结果
     */
    @WxEvent("cloud_invoice_invoiceresult_event")
    public void invoiceResultEvent(InvoiceResultEventMessage message) {
    }

}