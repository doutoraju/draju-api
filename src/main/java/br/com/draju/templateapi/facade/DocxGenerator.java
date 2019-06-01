package br.com.draju.templateapi.facade;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import br.com.draju.templateapi.data.Action;
import br.com.draju.templateapi.util.GeneratorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import br.com.draju.templateapi.data.Petitioner;

@Component
public class DocxGenerator {
    private static final String TEMPLATE_NAME = "template_002.docx";
    
    /**
     * Reads a template and return a byte array after replacing tags
     * @param action
     * @return
     * @throws Exception
     */
    public byte[] generateDocxByteFromTemplate(Action action) throws Exception {
        InputStream templateInputStream = 
           this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_NAME);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);
        HashMap<String, String> variables = new HashMap<>();
        //variables.put("Action.", action.getClass());
        //variables.put("lastName", action.getLastName());
        documentPart.variableReplace(variables);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wordMLPackage.save(outputStream);
        return outputStream.toByteArray();
    }
    
    /**
     * Reads a template and return a stream after replacing tags
     * @param actionInformation
     * @return
     * @throws Exception
     */
    public InputStreamResource generateDocxStreamFromTemplate(Action actionInformation) throws Exception {
    	String templateDir = new java.io.File(".").getCanonicalPath() + "\\target\\classes\\";
        InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEMPLATE_NAME));
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);
        Map<String, String> variables = GeneratorUtils.getReplacingParametersFromDTO(actionInformation);
        documentPart.variableReplace(variables);
        File outputFile = new File(templateDir + actionInformation.getPetitioner().getCpf());
        wordMLPackage.save(outputFile);
        return new InputStreamResource(new FileInputStream(outputFile));
    }
    
    
    /**
     * Simple main test
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	String templateDir = new java.io.File(".").getCanonicalPath() + "\\target\\classes\\";
        InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEMPLATE_NAME));
        System.out.println("Will process: " + templateInputStream.toString());
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        System.out.println("sieze is: " + documentPart.getContents().getBody().getContent().toString());
        VariablePrepare.prepare(wordMLPackage);

        //prepare data for test
        Petitioner petitioner = new Petitioner();
        Action action = new Action();
        petitioner.setFullName("Victor Vieira");
        petitioner.setFullAdress("Rua Schiller 82, AP 603, Cristo Rei");
        petitioner.setCpf("123456789-09");
        action.setPetitioner(petitioner);

        //print JSON
        // Creating Object of ObjectMapper define in Jakson Api
        ObjectMapper Obj = new ObjectMapper();
        try {
            // get Oraganisation object as a json string
            String jsonStr = Obj.writeValueAsString(action);
            // Displaying JSON String
            System.out.println("PRINT JSON");
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //parse the data
        Map<String, String> variables = GeneratorUtils.getReplacingParametersFromDTO(action);
        documentPart.variableReplace(variables);
        System.out.println("Content after replace is: " + documentPart.getContents().getBody().getContent().toString());
	}
}