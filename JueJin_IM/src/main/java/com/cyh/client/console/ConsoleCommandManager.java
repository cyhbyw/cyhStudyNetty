package com.cyh.client.console;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 16:31
 */
@Slf4j
public final class ConsoleCommandManager {

    private static final Map<String, ConsoleCommand> CONSOLE_COMMAND_MAP = new ConcurrentHashMap<>();

    static {
        CONSOLE_COMMAND_MAP.put("CreateGroup", new CreateGroupConsoleCommand());
        CONSOLE_COMMAND_MAP.put("JoinGroup", new JoinGroupConsoleCommand());
        CONSOLE_COMMAND_MAP.put("QuitGroup", new QuitGroupConsoleCommand());
        CONSOLE_COMMAND_MAP.put("ListGroupMembers", new ListGroupMembersConsoleCommand());
    }

    public static void execute(Scanner scanner, Channel channel) {
        System.out.print("输入指令: ");
        String command = scanner.next();
        ConsoleCommand consoleCommand = CONSOLE_COMMAND_MAP.get(command);
        if (Objects.nonNull(consoleCommand)) {
            consoleCommand.execute(scanner, channel);
        } else {
            log.warn("无法识别[{}]指令，请重新输入!", command);
        }
    }

}
