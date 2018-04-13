package com.guonl.sso.server.core.config;

import com.guonl.sso.core.util.JedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by guonl
 * Date 2018/4/12 下午5:23
 * Description:
 */
@Configuration
public class SsoRedisConfig implements InitializingBean{

    @Value("${redis.address}")
    private String redisAddress;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisUtil.init(redisAddress);
    }
}
