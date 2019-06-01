package br.com.draju.templateapi.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties
public class Defendant {

    public String fullName;
    public String mainID;
    public String fullAddress;

}
