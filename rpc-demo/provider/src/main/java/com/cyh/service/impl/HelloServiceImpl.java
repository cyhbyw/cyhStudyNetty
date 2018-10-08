package com.cyh.service.impl;

import java.util.Date;

import com.cyh.service.HelloService;

/**
 * @author: CYH
 * @date: 2018/10/8 10:36
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + ". Now is " + new Date();
    }
}
