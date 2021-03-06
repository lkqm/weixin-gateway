package com.github.lkqm.weixin.gateway;

import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.annotation.WxMessage;
import com.github.lkqm.weixin.gateway.argument.ArgumentsResolver;
import com.github.lkqm.weixin.gateway.util.ReflectionUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 路由注册
 */
@Slf4j
public class WxRegister {

    private WxRouter router;
    private ArgumentsResolver argumentsResolver;

    Map<String, Method> registeredMessages = new HashMap<>();
    Map<String, Method> registeredEvents = new HashMap<>();

    private WxRegister(@NonNull WxRouter router, @NonNull ArgumentsResolver argumentsResolver) {
        this.router = router;
        this.argumentsResolver = argumentsResolver;
    }

    public static WxRegister create(WxRouter router, ArgumentsResolver argumentsResolver) {
        return new WxRegister(router, argumentsResolver);
    }

    /**
     * Register to wx router
     */
    public void register(Collection<Object> invokers) {
        log.info("wx router register starting...");
        Iterator<Object> it = invokers.iterator();
        while (it.hasNext()) {
            Object invoker = it.next();
            if (invoker != null) {
                registerInvoker(invoker);
            }
        }
    }

    public void registerInvoker(Object invoker) {
        Class<?> clazz = invoker.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            WxEvent wxEvent = method.getAnnotation(WxEvent.class);
            WxMessage wxMessage = method.getAnnotation(WxMessage.class);

            if (wxEvent != null && wxMessage != null) {
                String methodName = ReflectionUtils.getMethodFullSimpleName(method);
                throw new IllegalArgumentException("wx router register failed, duplicated annotation @WxEvent, @WxMessage: " + methodName);
            } else if (wxEvent != null) {
                registerEventHandler(invoker, method, wxEvent);
            } else if (wxMessage != null) {
                registryMessageHandler(invoker, method, wxMessage);
            }
        }
    }

    /**
     * Register wx router handle method with @WxEvent
     */
    private void registerEventHandler(Object invoker, Method method, WxEvent wxEvent) {
        String methodName = ReflectionUtils.getMethodFullSimpleName(method);
        String event = wxEvent.value();
        Method eventMethod = registeredEvents.get(event);
        if (eventMethod != null) {
            throw new IllegalArgumentException("wx router register failed, duplicated event=[" + event + "]: " + methodName + " ," + ReflectionUtils.getMethodFullSimpleName(eventMethod));
        }

        WxHandler handler = WxHandler.create(invoker, method, argumentsResolver);
        router.rule().msgType("event").event(event).handler(handler).next();
        registeredEvents.put(event, method);
        log.info("wx router register event handler [event={}]: {}", event, methodName);
    }

    /**
     * Register wx router handle method with @WxMessage
     */
    private void registryMessageHandler(Object invoker, Method method, WxMessage wxMessage) {
        String msgType = wxMessage.value();
        String methodName = ReflectionUtils.getMethodFullSimpleName(method);
        if ("event".equals(msgType)) {
            throw new IllegalArgumentException("wx router register failed，@WxMessage value must not be 'event': " + methodName);
        }

        Method orgMessageMethod = registeredMessages.get(msgType);
        if (orgMessageMethod != null) {
            throw new IllegalArgumentException("wx router register failed, duplicated message type=[" + msgType + "]: " + methodName + " ," + ReflectionUtils.getMethodFullSimpleName(orgMessageMethod));
        }

        WxHandler handler = WxHandler.create(invoker, method, argumentsResolver);
        router.rule().msgType(msgType).handler(handler).next();
        registeredMessages.put(msgType, method);
        log.info("wx router register message handler [msgType={}]: {}", msgType, methodName);
    }

}
