package com.github.lkqm.springboot.weixin.gateway;

import com.github.lkqm.weixin.gateway.WxConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.github.lkqm.springboot.weixin.gateway.WxGatewayProperties.PREFIX;

/**
 * 微信网关配置
 */
@ConfigurationProperties(PREFIX)
@Data
public class WxGatewayProperties implements Serializable {

    public static final String PREFIX = "wx.gateway";
    /**
     * 是否开启, 关闭后不会自动注入WxPortalController
     */
    private boolean enabled = true;

    /**
     * 开发模式, 不会微信校验消息的准确性
     */
    private boolean dev = false;

    /**
     * 路径前缀
     */
    private String prefix = "/wx/gateway";

    /**
     * 线程配置
     */
    private ThreadPool pool = new ThreadPool();

    /**
     * 微信配置
     */
    Map<String, WxConfig> configs = new HashMap<>();

    /**
     * 线程池配置
     */
    @Data
    public static class ThreadPool implements Serializable {

        /**
         * 名称前缀
         */
        private String namePrefix = "wx-router-";

        /**
         * 核心线程大小
         */
        private Integer coreSize = 10;

        /**
         * 最大线程大小
         */
        private Integer maxSize = 100;

        /**
         * 空闲线程存活时间(ms)
         */
        private Long keepAlive = 5000L;

        /**
         * 工作队列大小
         */
        private Integer queueCapacity;

    }
}
