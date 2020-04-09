package br.com.draju.templateapi;

import static br.com.draju.templateapi.util.Contants.RESOURCES_LOCATION;
import static br.com.draju.templateapi.util.Contants.TEMPLATE_NAME;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.entity.actiondata.Defendant;
import br.com.draju.templateapi.entity.actiondata.Petitioner;
import br.com.draju.templateapi.util.GeneratorUtils;

public class Docx4JTest {
	
	/*
	 * Local variables for replacing the document template
	 */
    private final String FULL_NAME = "Victor Vieira";
    private final String FULL_ADDRESS = "Rua Schiller 82, AP 603, Cristo Rei";
    private final String CPF = "123456789-09";
    private final String ID_TYPE = "CNPJ/MF";
    private final String PERSON_TYPE = "pessoa jurídica";
    private final String DEFENDANT_FULL_NAME = "Vivo S.A";
    private final String DEFENDANT_ADDRESS = "Av. Berrini 1465, São Paulo/SP";
	
	@Test
	@DisplayName("Test that Docx4J loads the action template")
	public void testTemplateIsLoaded() throws Exception {
    	String templateDir = new java.io.File(".").getCanonicalPath() + RESOURCES_LOCATION;
        InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEMPLATE_NAME));
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        
        assertNotNull(documentPart);
	}
	

	@Test
	@DisplayName("Test that generation of file is working with Docx4J")
	public void testTemplateReplaceGeneration() throws Exception {
    	String templateDir = new java.io.File(".").getCanonicalPath() + RESOURCES_LOCATION;
        InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEMPLATE_NAME));
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);

        //prepare actiondata for test
        Petitioner petitioner = new Petitioner();
        Action action = new Action();
		petitioner.setFullName(FULL_NAME);
		petitioner.setFullAdress(FULL_ADDRESS);
		petitioner.setCpf(CPF);
        action.setPetitioner(petitioner);
        Defendant defendant = new Defendant();
		defendant.setIdType(ID_TYPE);
		defendant.setPersonType(PERSON_TYPE);
		defendant.setFullName(DEFENDANT_FULL_NAME);
		defendant.setFullAddress(DEFENDANT_ADDRESS);

        // Creating Object of ObjectMapper define in Jakson Api
        ObjectMapper Obj = new ObjectMapper();
        // get Oraganisation object as a json string
        String jsonStr = Obj.writeValueAsString(action);

        assertNotNull(jsonStr);
        
        //parse the actiondata
        Map<String, String> variables = GeneratorUtils.getReplacingParametersFromDTO(action);
        documentPart.variableReplace(variables);        
        String documentContent = documentPart.getContents().getBody().getContent().toString();
        
        assertNotNull(documentContent);
        assertTrue(documentContent.contains(FULL_NAME));
        assertTrue(documentContent.contains(FULL_ADDRESS));
        assertTrue(documentContent.contains(CPF));
        assertTrue(documentContent.contains(ID_TYPE));
        assertTrue(documentContent.contains(PERSON_TYPE));
        assertTrue(documentContent.contains(DEFENDANT_FULL_NAME));
        assertTrue(documentContent.contains(DEFENDANT_ADDRESS));
	}

}
