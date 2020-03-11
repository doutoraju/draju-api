package br.com.draju.templateapi.controller;

import br.com.draju.templateapi.data.TemplateDTO;
import br.com.draju.templateapi.util.TemplateUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DummyRest {

    @GetMapping(path = "/")
    public String simpleRest() {
        return "OK";
    }
}
