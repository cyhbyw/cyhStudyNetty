package com.cyh.protocol.response;

import com.cyh.protocol.command.Command;
import com.cyh.protocol.command.Packet;
import com.cyh.session.Session;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 22:13
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private Session sendUser;
    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
