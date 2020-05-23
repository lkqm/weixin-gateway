package com.github.lkqm.weixin.gateway;

import com.github.lkqm.weixin.gateway.util.WxUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

public class WxPortalHandlerTest {

    WxRouter router;
    WxPortalHandler wxPortalHandler;

    @BeforeMethod
    public void setUp() {
        this.router = Mockito.mock(WxRouter.class);
        this.wxPortalHandler = new WxPortalHandler(this.router);
    }

    @Test(dataProvider = "wxConfig")
    public void testGet(WxConfig config) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = UUID.randomUUID().toString();
        String echostr = "JKLFOJFFUJLASJDF18FJ";
        String signature = WxUtils.signature(config.getToken(), timestamp, nonce);

        assertEquals(wxPortalHandler.get(config, signature, timestamp, nonce, echostr), echostr);
        assertNotEquals(wxPortalHandler.get(config, "", timestamp, nonce, echostr), echostr);
    }

    @Test(dataProvider = "wxConfig")
    public void testPost(final WxConfig config) {
        final String xml = "" +
                "<xml>" +
                "  <ToUserName>Mario Luo</ToUserName>" +
                "  <FromUserName>001</FromUserName>" +
                "  <MsgType>event</MsgType>" +
                "  <Event>invoice_auth</Event>" +
                "  <CreateTime>1348831860</CreateTime>" +
                "</xml>";


        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        wxPortalHandler.post(true, config, xml, "signature", "timestamp", "nonce", "openid", "encType", "msgSignature");
        Mockito.verify(router).route(messageArgumentCaptor.capture());
        assertNotNull(messageArgumentCaptor.getValue());

        Message message = messageArgumentCaptor.getValue();
        Message expectedMessage = Message.createFromXml(xml);
        assertEquals(message.getToUserName(), expectedMessage.getToUserName());
        assertEquals(message.getFromUserName(), expectedMessage.getFromUserName());
        assertEquals(message.getMsgType(), expectedMessage.getMsgType());
        assertEquals(message.getEvent(), expectedMessage.getEvent());

        // 签名错误
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                wxPortalHandler.post(false, config, xml, "signature", "timestamp", "nonce", "openid", "encType", "msgSignature");
            }
        });
    }

    @DataProvider
    public Object[][] wxConfig() {
        WxConfig config = new WxConfig();
        config.setAppId("appId");
        config.setToken("token");
        config.setAesKey("aeskey");

        Object[][] data = new Object[1][1];
        data[0][0] = config;
        return data;
    }
}

