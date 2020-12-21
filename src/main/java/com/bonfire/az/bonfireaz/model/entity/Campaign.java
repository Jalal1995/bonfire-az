package com.bonfire.az.bonfireaz.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = {"user", "store"})
@ToString(exclude = {"user", "store"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {

    @Id
    @Column(name = "campaign_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private String name;

    @Column(columnDefinition = "varchar(100)")
    private String about;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "user_campaigns",
            joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "u_id", referencedColumnName = "u_id"))
    private XUser user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "store_campaigns",
            joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "store_id", referencedColumnName = "store_id"))
    private Store store;
}
