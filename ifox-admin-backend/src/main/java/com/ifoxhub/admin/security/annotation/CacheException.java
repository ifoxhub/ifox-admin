package com.ifoxhub.admin.security.annotation;

import java.lang.annotation.*;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
