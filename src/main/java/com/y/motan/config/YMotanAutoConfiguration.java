package com.y.motan.config;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 丁国航 Meow on 2019-11-26
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(YMotanConfig.class)
public class YMotanAutoConfiguration {

    @Resource
    private YMotanConfig yMotanConfig;

    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage(yMotanConfig.getBasePackage());
        return motanAnnotationBean;
    }

    @Bean(name = "defaultYRegistry")
    public RegistryConfigBean registryConfig() {
        if (yMotanConfig.isLocal()) {
            RegistryConfigBean config = new RegistryConfigBean();
            config.setRegProtocol("local");
            return config;
        }

        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol(yMotanConfig.getProtocol());
        config.setAddress(yMotanConfig.getAddress());
        return config;
    }

    @Bean
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setAccessLog(true);
        config.setShareChannel(true);
        config.setRegistry("defaultYRegistry");
        config.setApplication(yMotanConfig.getApplication());
        return config;
    }

}
