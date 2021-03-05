package com.ifox.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/3/5 11:44 上午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ifox.cache")
public class CacheProperties {

    private CacheServer server = CacheServer.REDIS;

    enum CacheServer {
        REDIS, CAFFEINE
    }
}
