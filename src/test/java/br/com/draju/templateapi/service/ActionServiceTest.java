package br.com.draju.templateapi.service;

import static br.com.draju.templateapi.util.Contants.RESOURCES_LOCATION;
import static br.com.draju.templateapi.util.Contants.TEMPLATE_NAME;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.entity.actiondata.Defendant;
import br.com.draju.templateapi.entity.actiondata.Petitioner;
import br.com.draju.templateapi.util.GeneratorUtils;

@DisplayName("Docx4J API test")
public class ActionServiceTest {
	
    /**
     * Simple main test
     * @param args
     * @throws Exception
     * TODO: Refactor into test method
     */
    public static void main(String[] args) throws Exception {
    	String templateDir = new java.io.File(".").getCanonicalPath() + RESOURCES_LOCATION;
        InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEMPLATE_NAME));
        System.out.println("Will process: " + templateInputStream.toString());
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        System.out.println("sieze is: " + documentPart.getContents().getBody().getContent().toString());
        VariablePrepare.prepare(wordMLPackage);

        //prepare actiondata for test
        Petitioner petitioner = new Petitioner();
        Action action = new Action();
        petitioner.setFullName("Victor Vieira");
        petitioner.setFullAdress("Rua Schiller 82, AP 603, Cristo Rei");
        petitioner.setCpf("123456789-09");
        action.setPetitioner(petitioner);
        Defendant defendant = new Defendant();
        defendant.setIdType("CNPJ/MF");
        defendant.setPersonType("pessoa jurídica");
        defendant.setFullName("Vivo S.A");
        defendant.setFullAddress("Av. Berrini 1465, São Paulo/SP");

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
        //parse the actiondata
        Map<String, String> variables = GeneratorUtils.getReplacingParametersFromDTO(action);
        documentPart.variableReplace(variables);
        System.out.println("Content after replace is: " + documentPart.getContents().getBody().getContent().toString());
	}

}
