package br.com.draju.templateapi.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
public class CustomerDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Customer customer;

    @NonNull
    private IdDocument document;
}
