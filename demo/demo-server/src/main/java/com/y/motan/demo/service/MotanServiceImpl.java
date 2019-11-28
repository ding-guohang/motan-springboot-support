package com.y.motan.demo.service;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.y.motan.demo.api.MotanDemoService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 丁国航 Meow on 2019-11-26
 */
@Slf4j
@MotanService
public class MotanServiceImpl implements MotanDemoService {

    @Override
    public String getString(String input) {
        log.info("get string input {}", input);
        return input.concat("bbbxxx");
    }

    @Override
    public List<String> getList(String input) {
        log.info("get string list input {}", input);
        return Stream.of(input.toCharArray())
                .map(String::new)
                .collect(Collectors.toList());
    }
}
