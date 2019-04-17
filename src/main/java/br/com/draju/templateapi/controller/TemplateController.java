package br.com.draju.templateapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.draju.templateapi.data.AuthorBasicData;
import br.com.draju.templateapi.facade.DocxGenerator;

@RestController
@RequestMapping("/template")
public class TemplateController {
		
		@Autowired
		private DocxGenerator docxGenerator;
		
		@GetMapping(path = "/get/{lastName}/{firstName}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	    public ResponseEntity<InputStreamResource> fillTemplate(@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName) {
			AuthorBasicData authorData = new AuthorBasicData();
			authorData.setLastName(lastName);
			authorData.setFirstName(firstName);
			return retrieveTemplate(authorData);
	    }
	
	 	@PostMapping(path = "/fill", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	    public ResponseEntity<InputStreamResource> fillTemplate(@RequestBody AuthorBasicData authorData) {
	 		 return retrieveTemplate(authorData);
	    }

		private ResponseEntity<InputStreamResource> retrieveTemplate(AuthorBasicData authorData) {
			InputStreamResource result = null;
	         try {
	             result = docxGenerator.generateDocxStreamFromTemplate(authorData); 
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
