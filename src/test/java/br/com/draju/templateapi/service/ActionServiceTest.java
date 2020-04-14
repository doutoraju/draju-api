package br.com.draju.templateapi.service;

import static br.com.draju.templateapi.util.Contants.DESTINATION_FILE_NAME;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.util.TestUtils;

@DisplayName("Docx4J API test")
@SpringBootTest
public class ActionServiceTest {
    
    @Autowired
    private ActionService service;
    
    @Autowired
    private TestUtils testUtils;
    
    @BeforeEach
    private void setUP() {
    	String destinationPath = DESTINATION_FILE_NAME.replace("${cpf}", testUtils.CPF);
        File file = new File(destinationPath);
        if(file.isFile()) {
        	file.delete();
        }
    }

	@Test
	@DisplayName("Test that generation of file is working with Docx4J")
    public void testDocxCreation() throws Exception {
	       //prepare actiondata for test
	        Action action = testUtils.getActionData();
	        
	        //call to service API
	        String pathToDoc = service.generateActionFile(action);
	        
	        //path checks
	        assertNotNull(pathToDoc);
	        assertTrue(pathToDoc.contains(testUtils.CPF));
	        
	        //file check
	        File file = new File(pathToDoc);
	        assertNotNull(file);
	        assertTrue(file.isFile());
	        
	        //content check
	        String fileContent = new String(Files.readAllBytes(Paths.get(pathToDoc)));
	        assertNotNull(fileContent);
	}

}
