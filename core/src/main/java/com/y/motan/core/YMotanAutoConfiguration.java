package com.y.motan.core;

import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import com.y.motan.core.config.YMotanConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;

/**
 * @author 丁国航 Meow on 2019-11-28
 */
@Slf4j
public class YMotanAutoConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String PREFIX = "y-motan.";
    private static final int PREFIX_LENGTH = PREFIX.length();

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        YMotanConfig yMotanConfig = parseConfig();
        System.out.println(yMotanConfig);

        registerProtocolConfig(registry, yMotanConfig);
        registerRegistryConfig(registry, yMotanConfig);
        registerBaseServiceConfig(registry, yMotanConfig);
        registerBaseClientConfig(registry, yMotanConfig);
        registerAnnotationBean(registry, yMotanConfig);

        registerMotanStarter(registry);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private YMotanConfig parseConfig() {
        YMotanConfig yMotanConfig = new YMotanConfig();

        for (PropertySource<?> property : ((AbstractEnvironment) this.environment).getPropertySources()) {

            if (property instanceof MapPropertySource) {
                for (String propertyName : ((MapPropertySource) property).getPropertyNames()) {
                    if (!propertyName.startsWith(PREFIX)) {
                        continue;
                    }

                    try {
                        String fieldName = propertyName.substring(PREFIX_LENGTH);
                        Field field = yMotanConfig.getClass().getDeclaredField(fieldName);
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(yMotanConfig, property.getProperty(propertyName));
                        field.setAccessible(accessible);
                    } catch (Exception e) {
                        log.error("set field {} error", propertyName, e);
                    }
                }
            }
        }
        return yMotanConfig;
    }

    private void registerAnnotationBean(BeanDefinitionRegistry registry, YMotanConfig config) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(AnnotationBean.class)
                .addPropertyValue("package", config.getBasePackages())
                .getBeanDefinition();
        registry.registerBeanDefinition("motanAnnotationBean", beanDefinition);
    }

    private void registerProtocolConfig(BeanDefinitionRegistry registry, YMotanConfig config) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ProtocolConfigBean.class)
                .addPropertyValue("id", "motan")
                .addPropertyValue("default", true)
                .addPropertyValue("name", "motan")
                .addPropertyValue("maxContentLength", 1048576)
                .getBeanDefinition();
        registry.registerBeanDefinition("motan", beanDefinition);
    }

    private void registerRegistryConfig(BeanDefinitionRegistry registry, YMotanConfig config) {
        if (config.isLocal()) {
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(RegistryConfigBean.class)
                    .addPropertyValue("regProtocol", "local")
                    .addPropertyValue("default", true)
                    .getBeanDefinition();
            registry.registerBeanDefinition("defaultYRegistry", beanDefinition);
            return;
        }

        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(RegistryConfigBean.class)
                .addPropertyValue("regProtocol", config.getProtocol())
                .addPropertyValue("address", config.getAddress())
                .addPropertyValue("default", true)
                .getBeanDefinition();
        registry.registerBeanDefinition("defaultYRegistry", beanDefinition);
    }

    private void registerBaseServiceConfig(BeanDefinitionRegistry registry, YMotanConfig config) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(BasicServiceConfigBean.class)
                .addPropertyValue("export", "motan:10003")
                .addPropertyValue("default", true)
                .addPropertyValue("accessLog", "true")
                .addPropertyValue("shareChannel", true)
                .addPropertyValue("application", config.getApplication())
                .addPropertyValue("group", config.getGroup())
                .addPropertyValue("module", config.getModule())
                .addPropertyValue("registry", "defaultYRegistry")
                .getBeanDefinition();
        registry.registerBeanDefinition("basicServiceConfigBean", beanDefinition);
    }

    private void registerBaseClientConfig(BeanDefinitionRegistry registry, YMotanConfig config) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(BasicRefererConfigBean.class)
                .addPropertyValue("default", true)
                .addPropertyValue("check", true)
                .addPropertyValue("application", config.getApplication())
                .addPropertyValue("group", config.getGroup())
                .addPropertyValue("module", config.getModule())
                .addPropertyValue("accessLog", "true")
                .addPropertyValue("throwException", true)
                .addPropertyValue("registry", "defaultYRegistry")
                .getBeanDefinition();
        registry.registerBeanDefinition("basicRefererConfigBean", beanDefinition);
    }

    private void registerMotanStarter(BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MotanStarter.class)
                .getBeanDefinition();
        registry.registerBeanDefinition("motanStarter", beanDefinition);
    }

}
