package com.github.lkqm.weixin.gateway;

import com.alibaba.fastjson.JSONObject;
import com.github.lkqm.weixin.gateway.util.XmlConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 路由消息处理内部使用, 事实不可变对象
 */
@Data
public class Message implements Serializable {

    /**
     * 开发者账号
     */
    private String toUserName;

    /**
     * 发送方账号
     */
    private String fromUserName;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 事件类型
     */
    private String event;

    //------------------原始信息------------------------//

    /**
     * 原始xml数据
     */
    private transient String xml;

    /**
     * 由xml按照特定规则数据转为的json
     */
    private transient String camelJson;

    /**
     * 原始xml数据map
     */
    private transient Map<String, Object> xmlMap;

    /**
     * 原始xml数据map
     */
    private transient Map<String, Object> xmlCamelMap;

    public static Message createFromXml(String xml) {
        XmlConverter converter = new XmlConverter(xml);
        converter.process();
        Map<String, Object> mapData = converter.getOriginalXmlMap();
        Map<String, Object> camelMapData = converter.getCamelXmlMap();

        Message message = new Message();
        Object toUserName = mapData.get("ToUserName");
        message.setToUserName(toUserName != null ? toUserName.toString() : "");
        Object fromUserName = mapData.get("FromUserName");
        message.setFromUserName(fromUserName != null ? fromUserName.toString() : "");
        Object createTime = mapData.get("CreateTime");
        if (createTime != null) {
            try {
                Long createTimeValue = Long.valueOf(createTime.toString());
                message.setCreateTime(createTimeValue);
            } catch (NumberFormatException e) {
            }
        }
        Object msgType = mapData.get("MsgType");
        message.setMsgType(msgType != null ? msgType.toString() : "");
        Object event = mapData.get("Event");
        message.setEvent(event != null ? event.toString() : "");
        message.setXml(xml);
        message.setXmlMap(mapData);
        message.setXmlCamelMap(camelMapData);

        String json = JSONObject.toJSONString(camelMapData);
        message.setCamelJson(json);
        return message;
    }
}
