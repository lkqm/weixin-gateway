package com.github.lkqm.weixin.gateway.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数注入: 注入消息体
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface WxBody {

    /**
     * 消息数据体类型
     */
    Type value() default Type.XML;

    enum Type {
        /**
         * 非加密的原始xml数据
         */
        XML,
        /**
         * 转换后驼峰命名格式的json
         */
        JSON_CAMEL
    }
}
