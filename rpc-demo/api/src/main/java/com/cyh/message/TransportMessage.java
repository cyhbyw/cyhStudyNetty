package com.cyh.message;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: CYH
 * @date: 2018/10/8 10:39
 */
@Data
public class TransportMessage implements Serializable {

    private String interfaceName;
    private String methodName;
    private Class[] parameterTypes;
    private Object[] args;

}
