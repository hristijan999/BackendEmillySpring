package com.example.emilly_ecomercev2.Service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendOrderMail(String to, Map<String, Object> variables) throws MessagingException {
        // fill template with variables
        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process("MailSend", context);

        // build email
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("hristijan.kolevski099@gmail.com");
        helper.setTo(to);
        helper.setSubject("Order Confirmation - Emilly Shop");
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }
}
