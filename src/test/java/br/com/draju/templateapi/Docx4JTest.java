package br.com.draju.templateapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.util.GeneratorUtils;
import br.com.draju.templateapi.util.TestUtils;

public class Docx4JTest {
	
	/**
	 * Non spring test - Simple instanciation
	 */
	private TestUtils testUtils = new TestUtils();
	
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
