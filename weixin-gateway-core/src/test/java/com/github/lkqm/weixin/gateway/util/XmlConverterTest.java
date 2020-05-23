package com.github.lkqm.weixin.gateway.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class XmlConverterTest {

    @Test(dataProvider = "xml")
    public void testOriginalXmlMap(String xml) {
        XmlConverter converter = new XmlConverter(xml);
        converter.process();

        Map<String, Object> originalXmlMap = converter.getOriginalXmlMap();
        assertTrue(originalXmlMap.size() == 6);
        assertEquals(originalXmlMap.get("ToUserName"), "Mario Luo");
        assertEquals(originalXmlMap.get("FromUserName"), "001");
        assertEquals(originalXmlMap.get("CreateTime"), "1348831860");
        assertEquals(originalXmlMap.get("MsgType"), "text");
        assertEquals(originalXmlMap.get("MsgId"), "1234567890123456");
        Object content = originalXmlMap.get("Content");
        assertTrue(content instanceof Map);
        Map<String, Object> contentMap = (Map<String, Object>) content;
        assertTrue(contentMap.size() == 2);
        assertEquals(contentMap.get("User_Name"), "张三");
        assertEquals(contentMap.get("User-Age"), "18");

        Map<String, Object> camelXmlMap = converter.getCamelXmlMap();
        assertTrue(camelXmlMap.size() == 6);
        assertEquals(camelXmlMap.get("toUserName"), "Mario Luo");
        assertEquals(camelXmlMap.get("fromUserName"), "001");
        assertEquals(camelXmlMap.get("createTime"), "1348831860");
        assertEquals(camelXmlMap.get("msgType"), "text");
        assertEquals(camelXmlMap.get("msgId"), "1234567890123456");
        Object camelContent = camelXmlMap.get("content");
        assertTrue(camelContent instanceof Map);
        Map<String, Object> camelContentMap = (Map<String, Object>) camelContent;
        assertTrue(camelContentMap.size() == 2);
        assertEquals(camelContentMap.get("userName"), "张三");
        assertEquals(camelContentMap.get("userAge"), "18");
    }


    @DataProvider
    public Object[][] xml() {
        String xml = "" +
                "<xml>" +
                "  <ToUserName>Mario Luo</ToUserName>" +
                "  <FromUserName>001</FromUserName>" +
                "  <CreateTime>1348831860</CreateTime>" +
                "  <MsgType><![CDATA[text]]></MsgType>" +
                "  <Content>" +
                "    <User_Name>张三</User_Name>" +
                "    <User-Age>18</User-Age>" +
                "  </Content>" +
                "  <MsgId>1234567890123456</MsgId>" +
                "</xml>";
        Object[][] data = new Object[1][1];
        data[0][0] = xml;
        return data;
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme