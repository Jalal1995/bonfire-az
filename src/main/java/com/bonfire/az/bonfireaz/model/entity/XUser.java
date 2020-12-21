package com.bonfire.az.bonfireaz.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.bonfire.az.bonfireaz.util.Util;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(exclude = {"stores", "campaigns"})
@ToString(exclude = {"stores", "campaigns"})
@Table(name = "user")
public class XUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long id;

    private String userId;

    private String email;

    private String password;

    private String roles;

    private String name;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private final static String DELIMITER = ":";

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Store> stores = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Campaign> campaigns = new ArrayList<>();

    public XUser(String email, String password, String name, String[] roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        setRoles(roles);
        setUserId();
        setCreatedDate();
        setUpdatedDate();
    }

    private void setUpdatedDate() {
        this.setUpdatedDate(LocalDate.now());
    }

    public String[] getRoles() {
        return roles == null || roles.isEmpty() ? new String[]{}
                : roles.split(DELIMITER);
    }

    public void setRoles(String[] roles) {
        this.roles = String.join(DELIMITER, roles);
    }

    private void setUserId() {
        Util u = new Util();
        String generateUserId = u.generateUserId();
        this.setUserId(generateUserId);
    }

    private void setCreatedDate() {
        this.setCreatedDate(LocalDate.now());
    }
}
