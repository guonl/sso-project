package com.guonl.sso.sample.config;

import com.guonl.sso.core.conf.Conf;
import com.guonl.sso.core.filter.SsoTokenFilter;
import com.guonl.sso.core.util.JedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SsoConfig {

    @Value("${sso.server}")
    private String ssoServer;

    @Value("${sso.logout.path}")
    private String ssoLogoutPath;

    @Value("${sso.redis.address}")
    private String ssoRedisAddress;

    @Bean
    public FilterRegistrationBean ssoFilterRegistration() {

        // redis init
        JedisUtil.init(ssoRedisAddress);

        // filter
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("SsoFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoTokenFilter());
        registration.addInitParameter(Conf.SSO_SERVER, ssoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, ssoLogoutPath);

        return registration;
    }

}
