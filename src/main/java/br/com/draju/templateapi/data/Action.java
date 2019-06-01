package br.com.draju.templateapi.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {

    public String courtHouse;
    public String courtCity;
    public String courtState;
    public String totalValue;
    public String date;
    public List<String> customArgs;

    public Petitioner petitioner;
    public Defendant defendant;
}
