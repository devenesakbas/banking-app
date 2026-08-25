package com.banking.banking_app_backend.notification.service.imp;

import com.banking.banking_app_backend.notification.exception.EmailSendException;
import com.banking.banking_app_backend.notification.dto.reponse.ResetCodeResponse;
import com.banking.banking_app_backend.notification.dto.request.ResetCodeRequest;
import com.banking.banking_app_backend.notification.service.EmailService;
import com.banking.banking_app_backend.notification.template.GeneratedResetCodeTemplate;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${notification.email.reset-code.subject}")
    String resetCodeSubject;

    @Value("${notification.email.reset-code.from}")
    String resetCodeFrom;

    private final JavaMailSender mailSender;

    private final GeneratedResetCodeTemplate generatedResetCodeTemplate;

    @Override
    public ResetCodeResponse sendResetCodeEmail(ResetCodeRequest request) {

        String resetCodeTo = request.to();
        String resetCodeTemplate = generatedResetCodeTemplate.getTemplate(request.code());

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(resetCodeFrom);
            helper.setTo(resetCodeTo);
            helper.setSubject(resetCodeSubject);
            helper.setText(resetCodeTemplate, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new EmailSendException(e.getMessage());
        }

        return ResetCodeResponse.builder()
                .send(true)
                .build();

    }

}
