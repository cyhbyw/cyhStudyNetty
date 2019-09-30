package nia.chapter13;

import java.net.InetSocketAddress;

import lombok.Data;

/**
 * 代码清单 13-1 LogEvent 消息
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Data
public final class LogEvent {
    public static final byte SEPARATOR = (byte) ':';

    /** 发送 LogEvent 的源的 InetSocketAddress */
    private final InetSocketAddress source;

    /** 所发送的 LogEvent 的日志文件的名称 */
    private final String logfile;

    /** 消息内容 */
    private final String msg;

    /** 接收 LogEvent 的时间 */
    private final long received;

    /**
     * 用于传入消息的构造函数
     * @param source
     * @param received
     * @param logfile
     * @param msg
     */
    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

}
