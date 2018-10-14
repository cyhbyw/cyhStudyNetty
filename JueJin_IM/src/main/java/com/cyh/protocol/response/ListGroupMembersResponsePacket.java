package com.cyh.protocol.response;

import java.util.List;

import com.cyh.protocol.command.Command;
import com.cyh.protocol.command.Packet;
import com.cyh.session.Session;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:27
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
