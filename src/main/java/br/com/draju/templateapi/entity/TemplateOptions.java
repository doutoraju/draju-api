package br.com.draju.templateapi.entity;

import javax.persistence.Entity;

@Entity
public class TemplateOptions {
    private OptionType optionType;
    private boolean enabled;
}
