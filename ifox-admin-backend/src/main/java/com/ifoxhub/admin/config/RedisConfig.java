package com.ifoxhub.admin.config;

import com.ifoxhub.admin.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
