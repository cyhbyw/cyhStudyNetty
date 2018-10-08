package com.cyh.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.cyh.serialize.SerializeAlgorithm;
import com.cyh.serialize.Serializer;

/**
 * @author: CYH
 * @date: 2018/10/8 17:40
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte getSerializeAlgorithm() {
        return SerializeAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
