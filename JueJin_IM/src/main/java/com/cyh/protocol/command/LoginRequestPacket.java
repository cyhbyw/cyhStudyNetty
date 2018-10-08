package com.cyh.protocol.command;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/8 17:38
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
