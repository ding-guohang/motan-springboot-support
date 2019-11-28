package com.y.motan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 丁国航 Meow on 2019-11-26
 */
@SpringBootApplication(scanBasePackages = "com.y.motan.demo")
@EnableConfigurationProperties
public class ServerBootstrap {

    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        SpringApplication.run(ServerBootstrap.class, args);

    }
}
