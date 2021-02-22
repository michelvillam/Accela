package com.accela.personaddress.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String postalCode;

   // @ManyToOne
   // @JoinColumn(name = "person_id", nullable=false)
    @Column(nullable = false)
    private int personId;
}
