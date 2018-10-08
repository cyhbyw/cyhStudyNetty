package com.cyh;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author: CYH
 * @date: 2018/10/8 16:52
 */
public class ByteBufTest {

    public static void main(String[] args) {
        // 初始化为 9 个字节的容量
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf(9, 100)", buffer);

        // 先写入 4 个字节，write 方法会改变写指针（还有 5 个字节）
        buffer.writeBytes(new byte[] {1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // 写入一个 Int 占用 4 个字节（还有 1 个字节）
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // 写入最后一个字节，Buffer 此时不可写
        buffer.writeBytes(new byte[] {5});
        print("writeBytes(5)", buffer);

        // 当发现 Buffer 不可用时会自动扩容
        buffer.writeBytes(new byte[] {6});
        print("writeBytes(6)", buffer);

        // get 方法不会改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);

        // set 方法不会改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法将改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }


    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }

}
