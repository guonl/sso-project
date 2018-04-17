package com.guonl.sso.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by guonl
 * Date 2018/4/13 上午10:02
 * Description:
 */
@EnableAutoConfiguration
@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class,args);
    }
}
