package br.com.draju.templateapi.entity.actiondata;

import br.com.draju.templateapi.entity.IdDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Petitioner {

	@EmbeddedId
	private IdDocument keyDocument;

	private String fullName;
	private String email;
	private String gender;
	private String profession;
	private String maritialStatus;
	private String cpf;
	private String rg;
	private String fullAdress;
}
