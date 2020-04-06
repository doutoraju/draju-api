package br.com.draju.templateapi.entity.actiondata;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@ApiModel(value = "An special argument for the action", 
description = "These arguments are defined based on the template")
public class ActionArgument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ApiModelProperty(value = "The name of the argument", required = true)
    private String argumentName;

    @ApiModelProperty(value = "The value of the argument", required = true)
    private String argumentValue;
}
