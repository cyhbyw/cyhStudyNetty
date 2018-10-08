package com.cyh.consumer;

import java.lang.reflect.Proxy;

import com.cyh.proxy.MyInvocationHandler;
import com.cyh.service.HelloService;

/**
 * @author: CYH
 * @date: 2018/10/8 10:58
 */
public class ConsumerApp {

    public static void main(String[] args) {
        HelloService helloService = create(HelloService.class);
        System.out.println(helloService.sayHello("rpc-demo"));
    }

    private static <T> T create(Class<T> c) {
        MyInvocationHandler invocationHandler = new MyInvocationHandler(c);
        return (T) Proxy.newProxyInstance(c.getClassLoader(), new Class[] {c}, invocationHandler);
    }

}
