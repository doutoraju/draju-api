package br.com.draju.templateapi.facade;

import static br.com.draju.templateapi.util.Contants.RESOURCES_LOCATION;
import static br.com.draju.templateapi.util.Contants.TEMPLATE_NAME;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.entity.actiondata.Defendant;
import br.com.draju.templateapi.entity.actiondata.Petitioner;
import br.com.draju.templateapi.util.GeneratorUtils;

@Component
public class DocxGenerator {
    
    /**
     * Reads a template and return a byte array after replacing tags
     * @param action 
     * @return
     * @throws Exception
     */
    public byte[] simpleTemplateGeneration(Action action) throws Exception {
        InputStream templateInputStream = 
           this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_NAME);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);
        HashMap<String, String> variables = new HashMap<>();
        variables.put("Action.Petitioner.fullName", action.getPetitioner().getFullName());
        documentPart.variableReplace(variables);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wordMLPackage.save(outputStream);
        return outputStream.toByteArray();
    }
    
    
    /**
     * Generate action file and save on file system
     * @param actionInformation The Action data
     * @param templatePath The template full path
     * @param destinationPath Location to store file
     * @param variables Variables to replace in the document
     * @throws Exception
     */
    public void generateDocxAndSave(Action actionInformation, String templatePath,
    		String destinationPath, Map<String, String> variables) throws Exception {
        InputStream templateInputStream = new FileInputStream(new java.io.File(templatePath));
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);
        documentPart.variableReplace(variables);
        wordMLPackage.save(new File(destinationPath));
    }
    
}