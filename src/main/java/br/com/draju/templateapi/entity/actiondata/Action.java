package br.com.draju.templateapi.entity.actiondata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "The entity that represents the action", 
description = "All actions details are here, including the action parts")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String courtHouse;
    private String courtCity;
    private String courtState;
    private String totalValue;
    private String date;

    @ManyToOne
    private Petitioner petitioner;

    @ManyToOne
    private Defendant defendant;

    @OneToMany
    private List<ActionArgument> arguments;
}
