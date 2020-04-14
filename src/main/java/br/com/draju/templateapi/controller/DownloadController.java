package br.com.draju.templateapi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.service.ActionGenerationException;
import br.com.draju.templateapi.service.ActionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class DownloadController {

	@Autowired
	private ActionService service;

	@PostMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Download compacted file (zip)")
	public ResponseEntity<StreamingResponseBody> download(final HttpServletResponse response,
			@RequestBody Action actionData) {

		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment;filename=template.zip");

		StreamingResponseBody stream = out -> {
			try {
				final ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
				final File file = new File(service.generateActionFile(actionData));
				final InputStream inputStream = new FileInputStream(file);
				final ZipEntry zipEntry = new ZipEntry(file.getName());
				zipOut.putNextEntry(zipEntry);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = inputStream.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				inputStream.close();
				zipOut.close();
			} catch (final Exception e) {
				log.error("Exception while reading and streaming data {} ", e);
			}
		};
		log.debug("steaming response {} ", stream);
		return new ResponseEntity(stream, HttpStatus.OK);

	}

	@PostMapping(path = "/fill", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Fills a template document and download content")
	public ResponseEntity<StreamingResponseBody> fillTemplate(final HttpServletResponse response,
			@RequestBody Action actionData) {
		response.setContentType("application/docx");
		response.setHeader("Content-Disposition", "attachment;filename=template.docx");
		StreamingResponseBody stream = out -> {
			try {
				String filePath = service.generateActionFile(actionData);
				log.debug("Filepath to be saved to stream {}", filePath);
				InputStream initialStream = new FileInputStream(filePath);
				byte[] buffer = new byte[initialStream.available()];
				initialStream.read(buffer);
				log.debug("Buffered file to be saved to stream {}", buffer);
				out.write(buffer);
			} catch (ActionGenerationException e) {
				log.error("Error generating template file", e);
			} catch (IOException e) {
				log.error("Error generating template file", e);
			}
		};

		log.debug("steaming response {} ", stream);
		return new ResponseEntity(stream, HttpStatus.OK);
	}

}
