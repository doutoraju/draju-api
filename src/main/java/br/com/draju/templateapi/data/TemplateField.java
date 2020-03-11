package br.com.draju.templateapi.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateField {
    private String key;
    private String input;
    private String label;

}
