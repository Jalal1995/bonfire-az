package com.bonfire.az.bonfireaz.entity.api.register;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegRs {
    private int code;
    private String message;

    public static RegRs USER_EXISTS(String email) {
        return new RegRs(-1, String.format("user already exists with email %s", email));
    }
}
