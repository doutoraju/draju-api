package br.com.draju.templateapi.entity;

import br.com.draju.templateapi.data.IdDocumentType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class IdDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private IdDocumentType documentType;

    private String documentValue;
}
