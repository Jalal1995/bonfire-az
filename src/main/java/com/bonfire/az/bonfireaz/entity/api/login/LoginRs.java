package com.bonfire.az.bonfireaz.entity.api.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRs {
    private int code;
    private String token;
}
