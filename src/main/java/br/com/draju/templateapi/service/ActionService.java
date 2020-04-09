package br.com.draju.templateapi.service;

import static br.com.draju.templateapi.util.Contants.DESTINATION_FILE_NAME;
import static br.com.draju.templateapi.util.Contants.RESOURCES_LOCATION;
import static br.com.draju.templateapi.util.Contants.TEMPLATE_NAME;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.facade.DocxGenerator;
import br.com.draju.templateapi.util.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActionService {
	
    @Autowired
    private DocxGenerator docxGenerator;

	public String generateActionFile(Action actionInformation) throws ActionGenerationException {
		try {
			String templatePath = new java.io.File(".").getCanonicalPath() + RESOURCES_LOCATION + TEMPLATE_NAME;
			String destinationPath = DESTINATION_FILE_NAME.replaceAll("${cpf}",
					actionInformation.getPetitioner().getCpf());
			Map<String, String> variables = GeneratorUtils.getReplacingParametersFromDTO(actionInformation);
			docxGenerator.generateDocxAndSave(actionInformation, templatePath, destinationPath, variables);
			return destinationPath;
		} catch (Exception ex) {
			log.error("Exception generating action file", ex);
			//TODO: Adding multilang i18n support
			throw new ActionGenerationException("Action file could not be generated", ex);
		}
	}

}
