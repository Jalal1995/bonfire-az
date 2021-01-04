package com.bonfire.az.bonfireaz.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:app.custom.properties")
public class EmailService {

    @Value("${url.prefix}")
    private String URL_PREFIX;

    @Value("${email.address}")
    private String APP_EMAIL;

    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendMailMessage(String from, String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String content = "<p><h3>";
        content += text;
        content += "</h3></p>";
        content += "<hr><img src='cid:logoImage' />";

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        ClassPathResource resource = new ClassPathResource("/static/images/bonfire-logo.png");
        helper.addInline("logoImage", resource);
        mailSender.send(message);
    }

    public void sendMail(String email, String token) {
        String message = String.format("To reset your password, please click here : %s/password/reset?token=%s",
                URL_PREFIX, token);
        String subject = "Reset password";
        sendMailMessage(
                APP_EMAIL,
                email,
                subject,
                message
        );
    }
}
