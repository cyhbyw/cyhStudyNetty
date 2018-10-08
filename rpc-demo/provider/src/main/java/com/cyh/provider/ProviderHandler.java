package com.cyh.provider;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cyh.message.TransportMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: yanhua.chen@bkjk.com
 * @date: 2018/10/8 10:24
 */
public class ProviderHandler extends SimpleChannelInboundHandler {

    private Map<String, Object> registryMap = new ConcurrentHashMap<>(1);

    public ProviderHandler() {
        scanInterfaceImpl("com.cyh.service.impl");
        System.out.println("registryMap=" + registryMap);
    }

    private void scanInterfaceImpl(String packagePath) {
        URL url = this.getClass().getClassLoader().getResource(packagePath.replaceAll("\\.", "/"));
        File dir = new File(url.getPath());
        for (File file : dir.listFiles()) {
            String subPath = packagePath + "." + file.getName();
            if (file.isDirectory()) {
                scanInterfaceImpl(subPath);
            } else {
                putImplClassIntoMap(subPath.replaceAll(".class", ""));
            }
        }
    }

    private void putImplClassIntoMap(String implClass) {
        try {
            Class<?> clazz = Class.forName(implClass);
            Class<?> theOnlyInterface = clazz.getInterfaces()[0];
            registryMap.put(theOnlyInterface.getName(), clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        TransportMessage request = (TransportMessage) msg;
        System.out.println("服务端收到客户端请求: " + request);
        Object implClassInstance = registryMap.get(request.getInterfaceName());
        Method method = implClassInstance.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        Object result = method.invoke(implClassInstance, request.getArgs());
        ctx.writeAndFlush(result);
        ctx.close();
    }
}
