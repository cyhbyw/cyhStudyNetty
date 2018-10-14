package com.cyh.protocol.command;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.cyh.protocol.request.CreateGroupRequestPacket;
import com.cyh.protocol.request.JoinGroupRequestPacket;
import com.cyh.protocol.request.ListGroupMembersRequestPacket;
import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.request.MessageRequestPacket;
import com.cyh.protocol.request.QuitGroupRequestPacket;
import com.cyh.protocol.response.CreateGroupResponsePacket;
import com.cyh.protocol.response.JoinGroupResponsePacket;
import com.cyh.protocol.response.ListGroupMembersResponsePacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.protocol.response.MessageResponsePacket;
import com.cyh.protocol.response.QuitGroupResponsePacket;
import com.cyh.serialize.Serializer;
import com.cyh.serialize.impl.JsonSerializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author: CYH
 * @date: 2018/10/8 17:45
 */
public class PacketCodeC {

    public static final Integer MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> REQUEST_COMMAND_MAP = new ConcurrentHashMap<>();
    private static final Map<Byte, Serializer> SERIALIZER_MAP = new ConcurrentHashMap<>();
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {
        REQUEST_COMMAND_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        REQUEST_COMMAND_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        REQUEST_COMMAND_MAP.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        REQUEST_COMMAND_MAP.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        REQUEST_COMMAND_MAP.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        REQUEST_COMMAND_MAP.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);

        REQUEST_COMMAND_MAP.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        REQUEST_COMMAND_MAP.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);

        REQUEST_COMMAND_MAP.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        REQUEST_COMMAND_MAP.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);

        REQUEST_COMMAND_MAP.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        REQUEST_COMMAND_MAP.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);

        Serializer serializer = new JsonSerializer();
        SERIALIZER_MAP.put(serializer.getSerializeAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet) {
        return encode(ByteBufAllocator.DEFAULT, packet);
    }

    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        ByteBuf byteBuf = allocator.ioBuffer();
        encode(byteBuf, packet);
        return byteBuf;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 Magic Number
        byteBuf.skipBytes(4);
        // 跳过 版本号
        byteBuf.skipBytes(1);
        // 获取序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 获取请求指令
        byte command = byteBuf.readByte();
        // 获取数据包长度、数据
        int length = byteBuf.readInt();
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Class<? extends Packet> requestCommand = REQUEST_COMMAND_MAP.get(command);
        Serializer serializer = SERIALIZER_MAP.get(serializeAlgorithm);

        return (Objects.nonNull(requestCommand) && Objects.nonNull(serializer))
                ? serializer.deserialize(requestCommand, data) : null;
    }

}
