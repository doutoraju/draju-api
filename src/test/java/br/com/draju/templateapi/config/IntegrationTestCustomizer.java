package br.com.draju.templateapi.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class IntegrationTestCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
        public void customize(ConfigurableServletWebServerFactory factory) {
            factory.setPort(8080);
            factory.setContextPath("/test");
        }
}
