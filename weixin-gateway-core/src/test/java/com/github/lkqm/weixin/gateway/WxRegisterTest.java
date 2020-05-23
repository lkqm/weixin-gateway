package com.github.lkqm.weixin.gateway;

import com.github.lkqm.weixin.gateway.argument.ArgumentsResolver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WxRegisterTest {

    WxRouter router;
    WxRegister register;

    @BeforeMethod
    public void setUp() {
        this.router = new WxRouter(1);
        this.register = WxRegister.create(router, new ArgumentsResolver());
    }

    @Test
    public void testRegisterInvoker() throws NoSuchMethodException {
        XxxHandlerDemo.XxxHandlerDemoNormal handler = new XxxHandlerDemo.XxxHandlerDemoNormal();
        register.registerInvoker(handler);

        WxRouteRule textRule = getWxRouteRule(router.rules, "text", null);
        Assert.assertNotNull(textRule);
        Assert.assertNotNull(textRule.getHandler());
        Assert.assertEquals(textRule.getHandler().getInvoker(), handler);
        Assert.assertEquals(textRule.getHandler().getMethod(), XxxHandlerDemo.XxxHandlerDemoNormal.class.getMethod("text"));

        WxRouteRule invoiceRule = getWxRouteRule(router.rules, "event", "invoice");
        Assert.assertNotNull(invoiceRule);
        Assert.assertNotNull(invoiceRule.getHandler());
        Assert.assertEquals(invoiceRule.getHandler().getInvoker(), handler);
        Assert.assertEquals(invoiceRule.getHandler().getMethod(), XxxHandlerDemo.XxxHandlerDemoNormal.class.getMethod("invoice"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterInvokerDuplicated() {
        XxxHandlerDemo.XxxHandlerDemoDuplicate handler = new XxxHandlerDemo.XxxHandlerDemoDuplicate();
        register.registerInvoker(handler);
    }

    private WxRouteRule getWxRouteRule(List<WxRouteRule> rules, String msgType, String event) {
        for (WxRouteRule rule : rules) {
            if (!rule.getMsgType().equals(msgType)) continue;
            if ("event".equals(msgType) && !rule.getEvent().equals(event)) continue;
            return rule;
        }
        return null;
    }
}

