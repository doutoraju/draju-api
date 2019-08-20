package br.com.draju.templateapi.controller;

import br.com.draju.templateapi.data.Action;
import br.com.draju.templateapi.data.TemplateDTO;
import br.com.draju.templateapi.facade.DocxGenerator;
import br.com.draju.templateapi.util.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private DocxGenerator docxGenerator;

    @PostMapping(path = "/fill", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> fillTemplate(@RequestBody Action actionData) {
        return retrieveTemplate(actionData);
    }

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
    public ResponseEntity<TemplateDTO> getSimpleTemplate() {
        TemplateDTO dto = TemplateUtils.createSimpleTemplate();
        return ResponseEntity.ok(dto);
    }

    /**
     * Utils method to retrieve file
     * @param actionData Data to retrieve
     * @return
     */
    private ResponseEntity<InputStreamResource> retrieveTemplate(Action actionData) {
        InputStreamResource result = null;
        try {
            result = docxGenerator.generateDocxStreamFromTemplate(actionData);
            return ResponseEntity.ok()
                    // Content-Disposition
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=template.docx")
                    // Content-Type
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    // Contet-Length
                    .contentLength(result.contentLength()) //
                    .body(result);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: Improve errors
            return ResponseEntity.notFound().build();
        }
    }

}
