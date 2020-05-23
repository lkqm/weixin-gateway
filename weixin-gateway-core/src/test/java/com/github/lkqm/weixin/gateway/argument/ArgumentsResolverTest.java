package com.github.lkqm.weixin.gateway.argument;

import com.github.lkqm.weixin.gateway.Message;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class ArgumentsResolverTest {

    ArgumentsResolver argumentsResolver;
    HandlerMethodArgumentResolver userResolver;

    @BeforeMethod
    public void setUp() {
        this.argumentsResolver = new ArgumentsResolver();
        this.userResolver = Mockito.mock(HandlerMethodArgumentResolver.class);
        this.argumentsResolver.addResolver(userResolver);
    }

    @Test
    public void testResolveArguments() throws Exception {
        String xml = "" +
                "<xml>" +
                "  <ToUserName><![CDATA[ToUserName]]></ToUserName>" +
                "  <FromUserName><![CDATA[FromUserName]]></FromUserName>" +
                "  <CreateTime>1348831860</CreateTime>" +
                "  <MsgType><![CDATA[text]]></MsgType>" +
                "  <Content><![CDATA[给我一个红包]]></Content>" +
                "  <MsgId>1234567890123456</MsgId>" +
                "</xml>";
        Message message = Message.createFromXml(xml);
        // 原生类型
        Method primitiveMethod = ArgumentDemo.class.getMethod("primitive", int.class, boolean.class);
        Object[] primitiveResult = argumentsResolver.resolveArguments(primitiveMethod, message);
        assertEquals(primitiveResult, new Object[]{0, false});

        // @WxParam
        Method wxParamMethod = ArgumentDemo.class.getMethod("wxParam", String.class, String.class, String.class);
        Object[] wxParamResult = argumentsResolver.resolveArguments(wxParamMethod, message);
        assertEquals(wxParamResult, new Object[]{"FromUserName", null, null});

        // @WxBody
        Method wxBodyMethod = ArgumentDemo.class.getMethod("wxBody", String.class, String.class);
        Object[] wxBodyResult = argumentsResolver.resolveArguments(wxBodyMethod, message);
        assertTrue(wxBodyResult.length == 2);
        assertEquals(wxBodyResult[0], message.getXml());
        assertNotNull(wxBodyResult[1], message.getCamelJson());

        // Custom resolver
        ArgumentDemo.User user = new ArgumentDemo.User();
        when(userResolver.supportsParameter(ArgumentDemo.User.class)).thenReturn(true);
        when(userResolver.resolveArgument(any(Message.class))).thenReturn(user);
        Method customResolverMethod = ArgumentDemo.class.getMethod("customResolver", ArgumentDemo.User.class);
        Object[] customResolverResult = argumentsResolver.resolveArguments(customResolverMethod, message);
        assertTrue(customResolverResult.length == 1);
        assertEquals(customResolverResult[0], user);

    }

}

