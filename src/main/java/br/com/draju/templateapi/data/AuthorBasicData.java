package br.com.draju.templateapi.data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthorBasicData {
	
	@Valid 
	@NotNull
    private String firstName;
    
	@Valid 
	@NotNull
    private String lastName;

}
