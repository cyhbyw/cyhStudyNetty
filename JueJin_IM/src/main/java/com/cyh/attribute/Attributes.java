package com.cyh.attribute;

import com.cyh.session.Session;

import io.netty.util.AttributeKey;

/**
 * @author: CYH
 * @date: 2018/10/11 17:37
 */
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
