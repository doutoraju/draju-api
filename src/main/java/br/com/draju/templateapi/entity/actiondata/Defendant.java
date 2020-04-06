package br.com.draju.templateapi.entity.actiondata;

import br.com.draju.templateapi.entity.IdDocument;
import io.swagger.annotations.ApiModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@JsonIgnoreProperties
@ApiModel(value = "The entity that represents the defendant", 
description = "All defendant data is present here and should be completed")
public class Defendant {

    @EmbeddedId
    @JsonIgnore
    private IdDocument keyDocument;

    private String fullName;
    private String mainID;
    private String idType;
    private String fullAddress;
    private String personType;

}
