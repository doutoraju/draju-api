package br.com.draju.templateapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class DummyRest {

    @GetMapping(path = "/")
	@ApiOperation(value = "Test default endpoint, like health check")
    public String simpleRest() {
        return "OK";
    }
}
