package com.github.lkqm.weixin.gateway.util;

import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;

@UtilityClass
public class StringUtils {

    public static boolean isAnyEmpty(String... texts) {
        for (String text : texts) {
            if (text == null || text.length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String join(String[] elements, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i != elements.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static boolean equalsIgnoreCase(String text1, String text2) {
        if (text1 != null) {
            return text1.equalsIgnoreCase(text2);
        }
        return text2 == null;
    }

    static String convertToCamelCase(String text) {
        StringBuilder name = new StringBuilder();
        char[] chars = text.toCharArray();
        boolean isNeedUpper = false;
        boolean isFirstChar = true;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '-' || c == '_' || c == '$' || c == '.') {
                isNeedUpper = true;
                continue;
            }
            if (isFirstChar) {
                name.append(Character.toLowerCase(c));
                isFirstChar = false;
                isNeedUpper = false;
                continue;
            }
            name.append(isNeedUpper ? Character.toUpperCase(c) : c);
            isNeedUpper = false;
        }
        return name.toString();
    }

    public static byte[] getBytesUTF8(String source) {
        try {
            return source.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Never happen", e);
        }
    }
}
