package br.com.draju.templateapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IntegrationTestCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
        public void customize(ConfigurableServletWebServerFactory factory) {
            factory.setPort(8081);
            factory.setContextPath("/test");
            log.info("Reseting port and contexts");
        }
}
