package com.github.lkqm.weixin.gateway.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ReflectionUtilsTest {

    @Test
    public void testGetAnnotation() {
    }

    @Test
    public void testGetTypeDefaultValue() {
        assertEquals(ReflectionUtils.getTypeDefaultValue(boolean.class), false);
        assertEquals(ReflectionUtils.getTypeDefaultValue(char.class), '\u0000');
        assertEquals(ReflectionUtils.getTypeDefaultValue(byte.class), (byte) 0);
        assertEquals(ReflectionUtils.getTypeDefaultValue(short.class), (short) 0);
        assertEquals(ReflectionUtils.getTypeDefaultValue(int.class), 0);
        assertEquals(ReflectionUtils.getTypeDefaultValue(long.class), 0L);
        assertEquals(ReflectionUtils.getTypeDefaultValue(float.class), 0f);
        assertEquals(ReflectionUtils.getTypeDefaultValue(double.class), 0D);
    }

    @Test
    public void testGetMethodFullName() throws NoSuchMethodException {
        assertEquals(ReflectionUtils.getMethodFullName(String.class.getMethod("trim")),
                "java.lang.String.trim()");
        assertEquals(ReflectionUtils.getMethodFullName(String.class.getMethod("indexOf", int.class, int.class)),
                "java.lang.String.indexOf(int,int)");
    }

    @Test
    public void testGetMethodFullSimpleName() throws NoSuchMethodException {
        assertEquals(ReflectionUtils.getMethodFullSimpleName(String.class.getMethod("trim")),
                "String.trim()");
        assertEquals(ReflectionUtils.getMethodFullSimpleName(String.class.getMethod("indexOf", int.class, int.class)),
                "String.indexOf(int,int)");
    }
}

