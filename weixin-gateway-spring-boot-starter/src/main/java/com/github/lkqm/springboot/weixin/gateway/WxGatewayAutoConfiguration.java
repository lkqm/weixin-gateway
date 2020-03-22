package com.github.lkqm.springboot.weixin.gateway;

import com.github.lkqm.weixin.gateway.WxPortalHandler;
import com.github.lkqm.weixin.gateway.WxRegister;
import com.github.lkqm.weixin.gateway.WxRouter;
import com.github.lkqm.weixin.gateway.annotation.WxController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableConfigurationProperties(WxGatewayProperties.class)
@Slf4j
public class WxGatewayAutoConfiguration {

    @Resource
    private ApplicationContext ctx;

    @Bean
    @ConditionalOnMissingBean
    public WxRouter wxMessageRouter(@Qualifier("wxRouterExecutor") ExecutorService executor) {
        WxRouter router = new WxRouter(executor);
        WxRegister.create(router).register(ctx.getBeansWithAnnotation(WxController.class).values());
        return router;
    }

    @Bean("wxRouterExecutor")
    @ConditionalOnMissingBean
    public ExecutorService wxRouterExecutor(WxGatewayProperties properties) {
        WxGatewayProperties.ThreadPool pool = properties.getPool();

        final String prefix = pool.getNamePrefix();
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable task) {
                return new Thread(task, prefix + threadNumber.getAndIncrement());
            }
        };
        BlockingQueue<Runnable> workQueue = pool.getQueueCapacity() == null ? new LinkedBlockingDeque<Runnable>()
                : new ArrayBlockingQueue<Runnable>(pool.getQueueCapacity());
        return new ThreadPoolExecutor(pool.getCoreSize(), pool.getMaxSize(), pool.getKeepAlive(),
                TimeUnit.MILLISECONDS, workQueue, threadFactory, new ThreadPoolExecutor.DiscardPolicy());
    }

    @Bean
    @ConditionalOnProperty(prefix = WxGatewayProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public WxPortalController wxPortalController(WxRouter wxRouter, WxGatewayProperties properties) {
        WxPortalHandler handler = new WxPortalHandler(wxRouter);
        return new WxPortalController(handler, properties);
    }

}
