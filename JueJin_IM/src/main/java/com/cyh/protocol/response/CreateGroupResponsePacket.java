package com.cyh.protocol.response;

import java.util.List;

import com.cyh.protocol.command.Command;
import com.cyh.protocol.command.Packet;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 17:07
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
