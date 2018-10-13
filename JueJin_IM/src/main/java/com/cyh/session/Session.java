package com.cyh.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: CYH
 * @date: 2018/10/13 0013 18:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private String userId;
    private String username;

}
