package com.cyh.protocol.request;

import com.cyh.protocol.command.Command;
import com.cyh.protocol.command.Packet;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/8 17:38
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
