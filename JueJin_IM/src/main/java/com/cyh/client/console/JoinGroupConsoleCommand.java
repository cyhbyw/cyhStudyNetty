package com.cyh.client.console;

import java.util.Scanner;

import com.cyh.protocol.request.JoinGroupRequestPacket;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:00
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId，加入群聊：");
        String groupId = scanner.next();
        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
