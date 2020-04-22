package br.com.draju.templateapi.util;

import static br.com.draju.templateapi.util.Contants.RESOURCES_LOCATION;

import java.io.FileInputStream;
import java.io.InputStream;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Component;

import br.com.draju.templateapi.entity.IdDocument;
import br.com.draju.templateapi.entity.IdDocumentType;
import br.com.draju.templateapi.entity.actiondata.Action;
import br.com.draju.templateapi.entity.actiondata.Defendant;
import br.com.draju.templateapi.entity.actiondata.Petitioner;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestUtils {

	/*
	 * Local data used for replacing the document template
	 */
	public final static String TEST_TEMPLATE_NAME = "test_template.docx";
	//other non-static data
	public final String FULL_NAME = "Victor Vieira";
	public final String FULL_ADDRESS = "Rua Schiller 82, AP 603, Cristo Rei";
	public final String CPF = "123456789-09";
	public final String ID_TYPE = "CNPJ/MF";
	public final String PERSON_TYPE = "pessoa jurídica";
	public final String DEFENDANT_FULL_NAME = "Vivo S.A";
	public final String DEFENDANT_ADDRESS = "Av. Berrini 1465, São Paulo/SP";

	public Action getActionData() {
		// prepare actiondata for test
		Action action = new Action();
		Petitioner petitioner = new Petitioner();
		IdDocument document = new IdDocument();
		document.setDocumentType(IdDocumentType.CPF);
		document.setDocumentValue(CPF);
		petitioner.setKeyDocument(document);
		petitioner.setFullName(FULL_NAME);
		petitioner.setFullAddress(FULL_ADDRESS);
		petitioner.setCpf(CPF);
		action.setPetitioner(petitioner);
		Defendant defendant = new Defendant();
		defendant.setIdType(ID_TYPE);
		defendant.setPersonType(PERSON_TYPE);
		defendant.setFullName(DEFENDANT_FULL_NAME);
		defendant.setFullAddress(DEFENDANT_ADDRESS);
		action.setDefendant(defendant);

		return action;
	}

	public MainDocumentPart createDocumentFromTestTemplate() {
		try {
			String templateDir = new java.io.File(".").getCanonicalPath() + RESOURCES_LOCATION;
			InputStream templateInputStream = new FileInputStream(new java.io.File(templateDir + TEST_TEMPLATE_NAME));
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
			VariablePrepare.prepare(wordMLPackage);
			return wordMLPackage.getMainDocumentPart();
		} catch (Exception e) {
			log.error("Error create document from test template", e);
			return null;
		}

	}

	public boolean wasContentReplaced(String documentContent) {
		return documentContent.contains(FULL_NAME) && documentContent.contains(FULL_ADDRESS)
				&& documentContent.contains(CPF) && documentContent.contains(ID_TYPE)
				&& documentContent.contains(PERSON_TYPE) && documentContent.contains(DEFENDANT_FULL_NAME)
				&& documentContent.contains(DEFENDANT_ADDRESS);
	}

}
