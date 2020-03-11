package br.com.draju.templateapi.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class IdDocument implements Serializable {

    private IdDocumentType documentType;

    private String documentValue;
}
