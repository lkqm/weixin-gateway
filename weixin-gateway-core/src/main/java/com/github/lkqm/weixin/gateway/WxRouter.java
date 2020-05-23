package com.github.lkqm.weixin.gateway;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 路由分发
 */
@Slf4j
public class WxRouter {

    final List<WxRouteRule> rules = new ArrayList<>();

    private ExecutorService executor;

    public WxRouter(int threadSize) {
        this.executor = Executors.newFixedThreadPool(threadSize, new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable task) {
                return new Thread(task, "wx-router-" + threadNumber.getAndIncrement());
            }
        });
    }

    public WxRouter(ExecutorService executor) {
        if (executor == null) {
            throw new IllegalArgumentException("Arguments executor must not be null.");
        }
        this.executor = executor;
    }

    public void route(final Message message) {
        WxRouteRule rule = getMatchRule(message);
        if (rule == null) {
            log.debug("No handler for message: ToUserName={}, FromUserName={}, CreateTime={}, MsgType={}, Event={}",
                    message.getToUserName(), message.getFromUserName(), message.getCreateTime(), message.getMsgType(), message.getEvent());
            return;
        }
        final WxHandler handler = rule.getHandler();
        if (handler == null) {
            log.info("Illegal WxRouteRule, handler must not be null: {}", rule);
            return;
        }

        this.executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.handle(message);
                    log.debug("End session access: fromUserName={}", message.getFromUserName());
                } catch (Exception e) {
                    log.error("Error happened when wait task finish", e);
                }
            }
        });
    }

    private WxRouteRule getMatchRule(Message message) {
        for (WxRouteRule rule : this.rules) {
            if (rule.test(message)) {
                return rule;
            }
        }
        return null;
    }

    /**
     * 开始一个新的Route规则
     */
    public WxRouteRule rule() {
        return new WxRouteRule(this);
    }

    /**
     * 添加一个规则
     */
    public void addRule(WxRouteRule rule) {
        if (rule != null) {
            this.rules.add(rule);
        }
    }
}
