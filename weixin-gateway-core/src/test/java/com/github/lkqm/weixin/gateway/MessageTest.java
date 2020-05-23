package com.github.lkqm.weixin.gateway;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class MessageTest {

    @Test(dataProvider = "xml")
    public void testCreateFromXml(String xml) {
        Message message = Message.createFromXml(xml);
        assertNotNull(message);
        assertEquals(message.getToUserName(), "Mario Luo");
        assertEquals(message.getFromUserName(), "001");
        assertEquals(message.getMsgType(), "event");
        assertEquals(message.getEvent(), "invoice_auth");
        assertEquals(message.getCreateTime(), Long.valueOf(1348831860L));

        assertEquals(message.getXml(), xml);
        assertNotNull(message.getCamelJson());
        assertNotNull(message.getXmlMap());
        assertNotNull(message.getXmlCamelMap());
    }

    @DataProvider
    public Object[][] xml() {
        String xml = "" +
                "<xml>" +
                "  <ToUserName>Mario Luo</ToUserName>" +
                "  <FromUserName>001</FromUserName>" +
                "  <MsgType>event</MsgType>" +
                "  <Event>invoice_auth</Event>" +
                "  <CreateTime>1348831860</CreateTime>" +
                "</xml>";
        Object[][] data = new Object[1][1];
        data[0][0] = xml;
        return data;
    }

}

