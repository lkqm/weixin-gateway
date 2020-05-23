package com.github.lkqm.weixin.gateway.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StringUtilsTest {

    @Test
    public void testIsAnyEmpty() {
        assertFalse(StringUtils.isAnyEmpty("texts"));
        assertFalse(StringUtils.isAnyEmpty("texts", " "));
        assertTrue(StringUtils.isAnyEmpty("texts", ""));
        assertTrue(StringUtils.isAnyEmpty("texts", null));
    }

    @Test
    public void testJoin() {
        assertEquals(StringUtils.join(new String[]{"a", "b", null}, ","), "a,b,null");
        assertEquals(StringUtils.join(new String[]{}, ","), "");
    }

    @Test
    public void testConvertToCamelCase() {
        assertEquals(StringUtils.convertToCamelCase("User_Name"), "userName");
        assertEquals(StringUtils.convertToCamelCase("User-Name"), "userName");
        assertEquals(StringUtils.convertToCamelCase("User$Name"), "userName");
        assertEquals(StringUtils.convertToCamelCase("_User-name_"), "userName");
    }

    @Test
    public void testGetBytesUTF8() {
        assertEquals(StringUtils.getBytesUTF8("新年快乐!123").length, 16);
    }
}
