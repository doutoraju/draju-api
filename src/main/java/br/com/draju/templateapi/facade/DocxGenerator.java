package br.com.draju.templateapi.facade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import br.com.draju.templateapi.data.AuthorBasicData;

@Component
public class DocxGenerator {
    private static final String TEMPLATE_NAME = "template.docx";
    
    /**
     * Reads a template and return a byte array after replacing tags
     * @param authorInformation
     * @return
     * @throws Exception
     */
    public byte[] generateDocxByteFromTemplate(AuthorBasicData authorInformation) throws Exception {
        InputStream templateInputStream = 
           this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_NAME);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);
        HashMap<String, String> variables = new HashMap<>();
        variables.put("firstName", authorInformation.getFirstName());
        variables.put("lastName", authorInformation.getLastName());
        documentPart.variableReplace(variables);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wordMLPackage.save(outputStream);
        return outputStream.toByteArray();
    }
    
    /**
     * Reads a template and return a stream after replacing tags
     * @param authorInformation
     * @return
     * @throws Exception
     */
    public InputStreamResource generateDocxStreamFromTemplate(AuthorBasicData authorInformation) throws Exception {
    	String templateDir = new java.io.File(".").getCanonicalPath() + "\\target\\classes\\";
        InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEMPLATE_NAME));
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);
        HashMap<String, String> variables = new HashMap<>();
        variables.put("firstName", authorInformation.getFirstName());
        variables.put("lastName", authorInformation.getLastName());
        documentPart.variableReplace(variables);
        File outputFile = new File(templateDir + authorInformation.getLastName());
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
        HashMap<String, String> variables = new HashMap<>();
        variables.put("firstName", "Victor");
        variables.put("lastName", "Vieira");
        documentPart.variableReplace(variables);
        System.out.println("Content after replace is: " + documentPart.getContents().getBody().getContent().toString());
	}
}