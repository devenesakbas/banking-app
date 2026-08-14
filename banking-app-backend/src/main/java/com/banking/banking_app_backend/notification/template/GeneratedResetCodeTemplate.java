package com.banking.banking_app_backend.notification.template;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class GeneratedResetCodeTemplate {

    @SneakyThrows
    public String  getTemplate(String code) {

        ClassPathResource resource =
                new ClassPathResource("html/resetCodeHtmlTemplate.html");

        String html = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        return html
                .replace("{{CODE}}", code);
    }

}
