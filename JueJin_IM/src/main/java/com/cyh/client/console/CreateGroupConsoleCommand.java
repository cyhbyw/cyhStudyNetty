package com.cyh.client.console;

import java.util.Arrays;
import java.util.Scanner;

import com.cyh.protocol.request.CreateGroupRequestPacket;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 16:32
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITTER = ",";

    @Override
    public void execute(Scanner scanner, Channel channel) {
        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();
        CreateGroupRequestPacket requestPacket = new CreateGroupRequestPacket();
        requestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITTER)));
        channel.writeAndFlush(requestPacket);
    }
}
