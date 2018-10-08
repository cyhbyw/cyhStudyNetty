package com.cyh.serialize;

import com.cyh.serialize.impl.JsonSerializer;

/**
 * @author: CYH
 * @date: 2018/10/8 17:39
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法类型
     * @return
     */
    byte getSerializeAlgorithm();

    /**
     * Java对象转二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制数据转Java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
