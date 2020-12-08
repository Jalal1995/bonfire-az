package com.bonfire.az.bonfireaz.entity.api.register;

import lombok.Data;

@Data
public class RegRq {
    private String email;
    private String password;
    private String name;
}
