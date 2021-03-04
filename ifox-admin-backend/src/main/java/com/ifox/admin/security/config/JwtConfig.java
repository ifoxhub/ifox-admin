package com.ifox.admin.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/3/4 1:19 下午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {

    private String tokenHeader = "Authorization";

    private String secret = "Rqy7cgvO3zcPTcGdwV29MkwhruN/MxxYiF7UQnmIXBWiz+mD1hoJktLquMX4/xJ0c/nhqHBKzFvj6H5nZktNxQ==";

    /**
     * JWT的超期限时间(60*60*24*7)
     */
    private Long expiration = 604800L;

    private String tokenHead = "Bearer ";

}
