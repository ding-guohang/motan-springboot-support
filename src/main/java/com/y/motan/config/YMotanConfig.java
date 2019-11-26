package com.y.motan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 丁国航 Meow on 2019-06-28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "y-motan")
public class YMotanConfig {

    private String protocol;
    private String address;
    private String application;
    private String basePackage;

    private boolean local = false;


}
