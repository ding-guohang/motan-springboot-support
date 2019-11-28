package com.y.motan.demo.client.service;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.y.motan.demo.api.MotanDemoService;
import org.springframework.stereotype.Service;

/**
 * @author 丁国航 Meow on 2019-11-27
 */
@Service
public class MotanServiceHandler {

    @MotanReferer
    private MotanDemoService motanDemoService;

    public MotanDemoService getMotanDemoService() {
        return motanDemoService;
    }
}
