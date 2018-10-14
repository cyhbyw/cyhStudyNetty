package com.cyh.protocol.response;

import com.cyh.protocol.command.Command;
import com.cyh.protocol.command.Packet;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:02
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private Boolean success;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
