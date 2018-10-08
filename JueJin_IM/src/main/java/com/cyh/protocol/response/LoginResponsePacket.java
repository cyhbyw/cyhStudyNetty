package com.cyh.protocol.response;

import com.cyh.protocol.command.Command;
import com.cyh.protocol.command.Packet;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/8 18:34
 */
@Data
public class LoginResponsePacket extends Packet {

    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
