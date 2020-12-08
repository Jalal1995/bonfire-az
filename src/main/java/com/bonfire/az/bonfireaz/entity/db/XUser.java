package com.bonfire.az.bonfireaz.entity.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.bonfire.az.bonfireaz.util.Util;
import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
public class XUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String email;
    private String password;
    private String roles;
    private String name;

    private final static String DELIMITER = ":";

    public XUser(String email, String password, String name, String[] roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        setRoles(roles);
        setUserId();
    }

    public String[] getRoles() {
        return roles == null || roles.isEmpty() ? new String[]{}
                : roles.split(DELIMITER);
    }

    public void setRoles(String[] roles) {
        this.roles = String.join(DELIMITER, roles);
    }

    public void setUserId() {
        Util u = new Util();
        String generateUserId = u.generateUserId();
        this.setUserId(generateUserId);
    }
}
