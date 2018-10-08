package com.cyh.protocol.command;

import org.junit.Assert;
import org.junit.Test;

import com.cyh.serialize.Serializer;
import com.cyh.serialize.impl.JsonSerializer;

import io.netty.buffer.ByteBuf;

/**
 * @author: CYH
 * @date: 2018/10/8 17:58
 */
public class PacketCodeCTest {

    @Test
    public void testEncodeAndDecode() {
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(1234);
        packet.setUsername("CYH");
        packet.setPassword("123456");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(packet);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Serializer serializer = new JsonSerializer();
        Assert.assertArrayEquals(serializer.serialize(packet), serializer.serialize(decodedPacket));
    }

}
