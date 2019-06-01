package br.com.draju.templateapi.entity;

import javax.persistence.*;

@Entity
@Table(name="CUSTOMER_ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected int number;

    protected String street;

    protected String city;

    protected String province;

    protected String zip;

    protected String country;

    public Address() { }

    //TODO: Getter and setter methods
    // ...
}