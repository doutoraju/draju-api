package br.com.draju.templateapi.controller;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.facade.DocxGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
public class TemplateController {
		
		@Autowired
		private DocxGenerator docxGenerator;

	 	@PostMapping(path = "/fill", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	    public ResponseEntity<InputStreamResource> fillTemplate(@RequestBody Action actionData) {
	 		 return retrieveTemplate(actionData);
	    }

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
