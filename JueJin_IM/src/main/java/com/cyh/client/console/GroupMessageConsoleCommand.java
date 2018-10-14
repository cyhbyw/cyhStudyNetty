package com.cyh.client.console;

import java.util.Scanner;

import com.cyh.protocol.request.GroupMessageRequestPacket;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:59
 */
public class GroupMessageConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.print("输入 <groupId> <message>");
        String groupId = scanner.next();
        String message = scanner.next();

        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setGroupId(groupId);
        requestPacket.setMessage(message);
        channel.writeAndFlush(requestPacket);
    }
}
