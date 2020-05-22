package com.github.lkqm.weixin.gateway;


import com.github.lkqm.weixin.gateway.argument.ArgumentsResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 微信消息处理器, 封装具体的消息处理方法
 */
public class WxHandler {

    private Object invoker;
    private Method method;
    private ArgumentsResolver argumentsResolver;

    public static WxHandler create(Object invoker, Method method, ArgumentsResolver argumentsResolver) {
        return new WxHandler(invoker, method, argumentsResolver);
    }

    private WxHandler(Object invoker, Method method, ArgumentsResolver argumentsResolver) {
        if (invoker == null) {
            throw new IllegalArgumentException("Argument 'invoker' must not be null");
        }
        if (method == null) {
            throw new IllegalArgumentException("Argument 'method' must not be null");
        }
        if (argumentsResolver == null) {
            throw new IllegalArgumentException("Argument 'argumentResolver' must not be null");
        }
        this.invoker = invoker;
        this.method = method;
        this.argumentsResolver = argumentsResolver;
        method.setAccessible(true);
    }

    /**
     * Execute handle method
     */
    public void handle(Message message) {
        Object[] args = argumentsResolver.resolveArguments(method, message);
        try {
            method.invoke(invoker, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Never happen", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}