package br.com.draju.templateapi.util;

import br.com.draju.templateapi.data.TemplateDTO;
import br.com.draju.templateapi.data.TemplateField;

import java.util.Arrays;

public class TemplateUtils {

    public static TemplateDTO createSimpleTemplate() {
        TemplateField fullName = TemplateField.builder()
                .input("input")
                .key("fullName")
                .label("Full name:").build();
        TemplateField fullAddress = TemplateField.builder()
                .input("input")
                .key("fullAddress")
                .label("Full address:").build();
        TemplateDTO templateDTO = TemplateDTO.builder()
                .templateName("Simple template")
                .templateFieldList(Arrays.asList(fullName,fullAddress))
                .build();
        return templateDTO;
    }
}
