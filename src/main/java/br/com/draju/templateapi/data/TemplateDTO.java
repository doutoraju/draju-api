package br.com.draju.templateapi.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateDTO {

    private String templateName;
    private List<TemplateField> templateFieldList;
}
