package com.bonfire.az.bonfireaz.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"user", "campaigns"})
@ToString(exclude = {"user", "campaigns"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    @Column(columnDefinition = "varchar(100)")
    private String title;

    @Column(columnDefinition = "varchar(100)")
    private String subtitle;

    @Column(columnDefinition = "varchar(1000)")
    private String about;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "user_stores",
            joinColumns = @JoinColumn(name = "store_id", referencedColumnName = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "u_id", referencedColumnName = "u_id"))
    private XUser user;

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Campaign> campaigns = new ArrayList<>();
}
