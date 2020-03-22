package com.github.lkqm.weixin.gateway.util;

import lombok.Getter;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Xml转Json字符串工具
 */
public class XmlConverter {

    private String xml;
    private boolean processed = false;

    @Getter
    private Map<String, Object> originalXmlMap = new HashMap<>();

    @Getter
    private Map<String, Object> camelXmlMap = new HashMap<>();

    public XmlConverter(String xml) {
        this.xml = xml;
    }

    @SneakyThrows
    public void process() {
        if (!this.processed) {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new StringReader(xml));
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            readElementToMap(elements, originalXmlMap, camelXmlMap);

            this.processed = true;
        }
    }

    private void readElementToMap(List<Element> elements, Map<String, Object> originalXmlMap, Map<String, Object> camelXmlMap) {
        if (elements == null || elements.size() == 0) return;

        for (Element ele : elements) {
            String key = ele.getName();
            String camelKey = StringUtils.convertToCamelCase(key);
            List<Element> children = ele.elements();
            Object originalValue = null;
            Object camelValue = null;
            if (children != null && children.size() > 0) {
                originalValue = new HashMap<>(elements.size());
                camelValue = new HashMap<>(elements.size());
                readElementToMap(ele.elements(), (Map<String, Object>) originalValue, (Map<String, Object>) camelValue);
            }
            if (originalValue == null) {
                String text = ele.getTextTrim();
                originalValue = text;
                camelValue = text;
            }
            originalXmlMap.put(key, originalValue);
            camelXmlMap.put(camelKey, camelValue);
        }
    }

}
