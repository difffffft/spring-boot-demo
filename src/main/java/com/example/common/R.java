package com.example.common;

import lombok.*;


/**
 * 统一前端返回结果结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public static R success(String msg, Object data) {
        return new R(1, msg, data);
    }

    public static R error(String msg) {
        return new R(0, msg, null);
    }
}
