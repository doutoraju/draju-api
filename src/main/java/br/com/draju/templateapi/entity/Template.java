package br.com.draju.templateapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TemplateConfig")
@Data
/**
 * Exemplo:
 type: Cobranca invedida
 defendantType: Juridica
 TemplateOptions:
 - Dano moral: true
 - Tutela de urgencia: false
 */
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String defendantType;
    private List<TemplateOptions> templateOptions;
}
