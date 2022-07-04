package com.example.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    DELETE(0, "用户被删除"),
    NORMAL(1, "用户正常中"),
    DISABLE(2, "用户被封禁");
    private final Integer value;
    private final String label;
}
