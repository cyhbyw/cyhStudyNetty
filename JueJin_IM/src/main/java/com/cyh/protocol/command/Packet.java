package com.cyh.protocol.command;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/8 17:36
 */
@Data
public abstract class Packet {

    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 获取请求命令/请求类型（其实就是消息类型）
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
