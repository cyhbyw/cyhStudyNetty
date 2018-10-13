package com.cyh.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cyh.attribute.Attributes;
import com.cyh.session.Session;

import io.netty.channel.Channel;

/**
 * @author: CYH
 * @date: 2018/10/11 17:38
 */
public class SessionUtil {

    private static final Map<String, Channel> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>();


    public static void bindSession(Session session, Channel channel) {
        USER_ID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            USER_ID_CHANNEL_MAP.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return USER_ID_CHANNEL_MAP.get(userId);
    }
}
