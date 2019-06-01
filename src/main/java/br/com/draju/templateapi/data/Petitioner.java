package br.com.draju.templateapi.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Petitioner {

	public String fullName;
	public String email;
	public String gender;
	public String profession;
	public String maritialStatus;
	public String cpf;
	public String rg;
	public String fullAdress;
}
