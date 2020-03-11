package br.com.draju.templateapi;

import br.com.draju.templateapi.util.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

@SpringBootApplication
@EnableSwagger2
@Slf4j
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
