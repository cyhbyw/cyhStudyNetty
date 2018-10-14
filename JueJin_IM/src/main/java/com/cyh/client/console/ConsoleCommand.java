package com.cyh.client.console;

import java.util.Scanner;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 16:30
 */
public interface ConsoleCommand {

    /**
     * 控制台命令执行者
     * @param scanner
     * @param channel
     */
    void execute(Scanner scanner, Channel channel);
}
