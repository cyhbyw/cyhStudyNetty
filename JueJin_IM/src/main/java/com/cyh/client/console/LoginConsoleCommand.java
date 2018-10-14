package com.cyh.client.console;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cyh.protocol.request.LoginRequestPacket;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 16:45
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        System.out.print("输入用户名登陆: ");
        requestPacket.setUsername(scanner.nextLine());
        requestPacket.setPassword("123456");
        channel.writeAndFlush(requestPacket);
        sleep();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
