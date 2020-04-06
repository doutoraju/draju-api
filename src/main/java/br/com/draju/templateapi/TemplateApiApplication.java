package br.com.draju.templateapi;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.draju.templateapi.util.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Slf4j
@EnableSwagger2
@EnableWebMvc
public class TemplateApiApplication {

    @Autowired
    private EnvUtil envUtil;

    public static void main(String[] args) {
        SpringApplication.run(TemplateApiApplication.class, args);
    }

    @PostConstruct
    public void printServerInfo() {
        log.info(envUtil.getServerUrlPrefi());
    }

}
