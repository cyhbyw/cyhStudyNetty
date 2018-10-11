package com.cyh.attribute;

import io.netty.util.AttributeKey;

/**
 * @author: CYH
 * @date: 2018/10/11 17:37
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
