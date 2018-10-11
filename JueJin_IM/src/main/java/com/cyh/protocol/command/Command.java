package com.cyh.protocol.command;

/**
 * @author: CYH
 * @date: 2018/10/8 17:35
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

}
