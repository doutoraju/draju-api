package br.com.draju.templateapi.entity;

import br.com.draju.templateapi.data.GenderType;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
//TODO: Criar Process in English
//TODO: Criar reu in English
//TODO: Criar autor in English
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;

    private GenderType gender;
    private String profession;
    private String maritialStatus;

    @NonNull
    @OneToMany
    private List<IdDocument> documentList;

    @NonNull
    @OneToOne
    private Address mainAdress;
}
