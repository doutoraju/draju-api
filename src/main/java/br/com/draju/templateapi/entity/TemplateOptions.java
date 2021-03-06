package br.com.draju.templateapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TemplateOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private OptionType optionType;
    private boolean enabled;
}
