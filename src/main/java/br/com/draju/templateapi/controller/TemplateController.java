package br.com.draju.templateapi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.draju.templateapi.data.TemplateDTO;
import br.com.draju.templateapi.util.TemplateUtils;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/template")
public class TemplateController {

    /**
     * Get template info
     * {
     * "key": "firstName",
     * "type": "input",
     * "templateOptions": {
     * "label": "First Name"
     * }
     *
     * @return
     */
    @GetMapping(path = "/getsimple", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Test default endpoint, return a simple template definition")
    public ResponseEntity<TemplateDTO> getSimpleTemplate() {
        TemplateDTO dto = TemplateUtils.createSimpleTemplate();
        return ResponseEntity.ok(dto);
    }


}
