package com.bonfire.az.bonfireaz.model.request;

import lombok.Data;

@Data
public class ForgotRq {
    private String token;
    private String password;
}
