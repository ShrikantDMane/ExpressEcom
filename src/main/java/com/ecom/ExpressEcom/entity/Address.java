package com.ecom.ExpressEcom.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    private String streetName;
    private String city;
    private String state;
    private int zipCode;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;


}
