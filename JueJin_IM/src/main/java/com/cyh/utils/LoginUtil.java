package com.cyh.utils;

import java.util.Objects;

import com.cyh.attribute.Attributes;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author: CYH
 * @date: 2018/10/11 17:38
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return Objects.nonNull(attr) && Objects.nonNull(attr.get());
    }


}
