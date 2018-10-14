package com.cyh.client.console;

import java.util.Scanner;

import com.cyh.protocol.request.QuitGroupRequestPacket;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:15
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.print("输出将要退出的 groupId: ");
        String groupId = scanner.next();
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
