package com.y.motan.demo.client;

import com.y.motan.demo.api.MotanDemoService;
import com.y.motan.demo.client.service.MotanServiceHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author 丁国航 Meow on 2019-11-27
 */
@SpringBootApplication(scanBasePackages = "com.y.motan.demo")
@EnableConfigurationProperties
public class ClientBootstrap {

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        ConfigurableApplicationContext ctx = SpringApplication.run(ClientBootstrap.class, args);

        MotanServiceHandler handler = ctx.getBean(MotanServiceHandler.class);
        MotanDemoService service = handler.getMotanDemoService();

        System.out.println(service.getString("stringDemo"));
        System.out.println(service.getList("listString"));
    }
}
