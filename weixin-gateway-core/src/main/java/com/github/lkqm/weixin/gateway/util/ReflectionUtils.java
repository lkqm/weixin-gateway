package com.github.lkqm.weixin.gateway.util;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * ReflectionUtils
 */
@UtilityClass
public class ReflectionUtils {

    /**
     * 获取指定类型注解
     */
    public static <T> T getAnnotation(Annotation[] annotations, Class<T> targetClass) {
        for (Annotation one : annotations) {
            if (targetClass.isAssignableFrom(one.annotationType())) {
                return (T) one;
            }
        }
        return null;
    }

    /**
     * 获得类型的默认值
     */
    public static Object getTypeDefaultValue(Class type) {
        if (!type.isPrimitive()) {
            return null;
        }
        if (byte.class == type) {
            return (byte) 0;
        } else if (char.class == type) {
            return '\u0000';
        } else if (short.class == type) {
            return (short) 0;
        } else if (int.class == type) {
            return 0;
        } else if (long.class == type) {
            return 0L;
        } else if (double.class == type) {
            return 0D;
        } else if (float.class == type) {
            return 0F;
        } else if (boolean.class == type) {
            return true;
        } else {
            return null;
        }
    }

    /**
     * 获取方法全名包括参数
     */
    public static String getMethodFullName(Method method) {
        String className = method.getDeclaringClass().getCanonicalName();
        String methodName = method.getName();
        Class<?>[] types = method.getParameterTypes();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].getSimpleName();
        }
        String fullMethodName = new StringBuilder()
                .append(className)
                .append(".").append(methodName)
                .append("(").append(StringUtils.join(typeNames, ","))
                .append(")")
                .toString();
        return fullMethodName;
    }
}
