package com.github.lkqm.weixin.gateway.argument;

import com.github.lkqm.weixin.gateway.Message;

/**
 * 处理方法参数解析
 */
public interface HandlerMethodArgumentResolver {

    /**
     * 是否支持该类型
     */
    boolean supportsParameter(Class<?> type);

    /**
     * 解析参数
     */
    Object resolveArgument(Message message);

}