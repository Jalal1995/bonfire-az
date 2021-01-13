package com.bonfire.az.bonfireaz.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegRs {
    private int code;
    private String message;
    private UserRs user;

    public static RegRs USER_EXISTS(String email) {
        return new RegRs(-1, String.format("user already exists with email %s", email), null);
    }
}
