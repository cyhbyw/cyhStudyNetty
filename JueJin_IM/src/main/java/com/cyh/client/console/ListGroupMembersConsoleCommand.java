package com.cyh.client.console;

import java.util.Scanner;

import com.cyh.protocol.request.ListGroupMembersRequestPacket;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:25
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.print("列出群聊成员，请输入 groupId: ");
        String groupId = scanner.next();
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
