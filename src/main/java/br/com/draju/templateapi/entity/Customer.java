package br.com.draju.templateapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
//TODO: Criar Process in English
//TODO: Criar reu in English
//TODO: Criar autor in English
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String fullName;
    @NonNull
    private String email;

    private GenderType gender;
    private String profession;
    private MaritialStatus maritialStatus;

    @NonNull
    @OneToMany
    private List<IdDocument> documentList;

    @NonNull
    @OneToOne
    private Address mainAdress;
}
