package com.bonfire.az.bonfireaz.entity.sec;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class XUserDetails implements UserDetails {

    private final int id;
    private String userId;
    private final String email;
    private final String password;
    private final String[] roles;


    public XUserDetails(int id, String userId, String email, String password, String[] roles) {
        this.userId = userId;
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;

    }

    @Override
    public String toString() {
        return String.format("XUserDetails[%d:'%s':'%s':{%s}]", id, email, password, Arrays.toString(roles));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles)
                .map(s -> "ROLE_" + s)
                .map(s -> (GrantedAuthority) () -> s)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
