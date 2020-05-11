package br.com.draju.it;

import static br.com.draju.templateapi.util.Contants.RESOURCES_LOCATION;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Map;

import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.util.GeneratorUtils;
import br.com.draju.templateapi.util.TestUtils;


public class Docx4JIntegrationTest {
	
	/**
	 * Non spring test - Simple instanciation
	 */
	private TestUtils testUtils = new TestUtils();
	
	@Test
	@DisplayName("Assert template is presented")
	public void testTemplateIsFound() throws Exception {
        String location = new java.io.File(".").getCanonicalPath() + RESOURCES_LOCATION;
        assertNotNull(location);
        assertTrue(new File(location).isDirectory());
        assertTrue(new File(location).canRead());
        assertTrue(new File(location + TestUtils.TEST_TEMPLATE_NAME).isFile());
	}
	
	@Test
	@DisplayName("Test that Docx4J loads the action template")
	public void testTemplateIsLoaded() throws Exception {
        MainDocumentPart documentPart = testUtils.createDocumentFromTestTemplate();
        assertNotNull(documentPart);
	}
	

	@Test
	@DisplayName("Test that generation of file is working with Docx4J")
	public void testTemplateReplaceGeneration() throws Exception {
        //prepare document template and actiondata for test
		MainDocumentPart documentPart = testUtils.createDocumentFromTestTemplate();
        Action action = testUtils.getActionData();
        
        //parse the actiondata
        Map<String, String> variables = GeneratorUtils.getReplacingParametersFromDTO(action);
        String documentContent = documentPart.getContents().getBody().getContent().toString();
        assertNotNull(documentContent);
        
        //replace and test again
        documentPart.variableReplace(variables);        
        documentContent = documentPart.getContents().getBody().getContent().toString();
        
        assertNotNull(documentContent);
        assertTrue(testUtils.wasContentReplaced(documentContent));
       
	}

}
