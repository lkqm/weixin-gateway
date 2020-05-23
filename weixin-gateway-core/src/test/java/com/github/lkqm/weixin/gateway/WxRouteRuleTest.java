package com.github.lkqm.weixin.gateway;

import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.annotation.WxMessage;
import com.github.lkqm.weixin.gateway.argument.ArgumentsResolver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WxRouteRuleTest {

    WxRouter router;
    ArgumentsResolver argumentsResolver;

    @BeforeMethod
    public void setUp() {
        this.router = mock(WxRouter.class);
        this.argumentsResolver = new ArgumentsResolver();
    }


    @Test
    public void testTest() throws Exception {
        Message message = mock(Message.class);
        HandlerDemo demo = new HandlerDemo();

        // 消息匹配
        WxRouteRule rule = new WxRouteRule(router);
        rule.msgType("text").handler(WxHandler.create(demo, HandlerDemo.class.getMethod("text"), argumentsResolver)).next();
        when(message.getMsgType()).thenReturn("text");
        assertTrue(rule.test(message));
        when(message.getMsgType()).thenReturn("__UNKNOWN__");
        assertFalse(rule.test(message));

        // 事件匹配
        WxRouteRule eventRule = new WxRouteRule(router);
        eventRule.msgType("event").event("invoice")
                .handler(WxHandler.create(demo, HandlerDemo.class.getMethod("invoice"), argumentsResolver)).next();
        when(message.getMsgType()).thenReturn("event");
        when(message.getEvent()).thenReturn("invoice");
        assertTrue(eventRule.test(message));
        when(message.getMsgType()).thenReturn("__UNKNOWN__");
        assertFalse(eventRule.test(message));

    }

    @Test
    public void testNext() throws Exception {
        WxRouteRule rule = new WxRouteRule(router);
        rule.msgType("text").handler(WxHandler.create(new HandlerDemo(),
                HandlerDemo.class.getMethod("text"),
                argumentsResolver)).next();
        rule.next();
        verify(router, times(1)).addRule(any(WxRouteRule.class));
    }

    @WxController
    public static class HandlerDemo {
        @WxMessage("text")
        public void text() {
        }

        @WxEvent("invoice")
        public void invoice() {
        }
    }
}

