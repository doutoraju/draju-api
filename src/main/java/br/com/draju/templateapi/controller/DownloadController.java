package br.com.draju.templateapi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class DownloadController {

	/**
	 * http://localhost:8081/template-api/api/download
	 * TODO: Modify to get real file
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamingResponseBody> download(final HttpServletResponse response) {

		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment;filename=sample.zip");

		StreamingResponseBody stream = out -> {

			final String home = System.getProperty("user.home");
			final File directory = new File(home + File.separator + "Documents" + File.separator + "sample");
			final ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

			if (directory.exists() && directory.isDirectory()) {
				try {
					for (final File file : directory.listFiles()) {
						final InputStream inputStream = new FileInputStream(file);
						final ZipEntry zipEntry = new ZipEntry(file.getName());
						zipOut.putNextEntry(zipEntry);
						byte[] bytes = new byte[1024];
						int length;
						while ((length = inputStream.read(bytes)) >= 0) {
							zipOut.write(bytes, 0, length);
						}
						inputStream.close();
					}
					zipOut.close();
				} catch (final IOException e) {
					log.error("Exception while reading and streaming data {} ", e);
				}
			}
		};
		log.info("steaming response {} ", stream);
		return new ResponseEntity(stream, HttpStatus.OK);
	}
}
