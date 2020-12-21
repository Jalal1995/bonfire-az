package com.bonfire.az.bonfireaz.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRs {
    private int code;
    private String token;
    private String userId;
}
