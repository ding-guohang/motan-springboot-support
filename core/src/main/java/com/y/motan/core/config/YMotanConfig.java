package com.y.motan.core.config;

import lombok.Data;

/**
 * @author 丁国航 Meow on 2019-06-28
 */
@Data
public class YMotanConfig {

    private String protocol;
    private String address;
    private String application;
    private String basePackages;
    private String group;
    private String module;

    private boolean local = false;


}
