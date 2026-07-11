package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Repository.RobaRepository;
import com.example.emilly_ecomercev2.Service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final RobaRepository robaRepository;
    public MailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, RobaRepository robaRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.robaRepository = robaRepository;
    }

    public void sendOrderMail(String to, Map<String, Object> variables) throws MessagingException {
        // fill template with variables

        List<Roba> frontendCart = (List<Roba>) variables.get("cart");

        // 2. Издвој ги сите ID-а и земи ги точните продукти од база
        List<Long> productIds = frontendCart.stream()
                .map(Roba::getId)
                .toList();
        List<Roba> dbProducts = robaRepository.findAllById(productIds);
        Map<Long, Roba> productMap = dbProducts.stream()
                .collect(Collectors.toMap(Roba::getId, p -> p));

        int totalPrice = frontendCart.stream()
                .mapToInt(item -> {
                    Roba p = productMap.get(item.getId());

                    // Ако продуктот не постои во базата, врати 0 за да не влијае на сумата
                    if (p == null) {
                        return 0;
                    }

                    // 1. Одреди ја точната поединечна цена (со или без попуст)
                    int singlePrice = Boolean.TRUE.equals(p.getPopust()) ? p.getCenaSoPopust() : p.getPrice();

                    // 2. КЛУЧНО: Помножи ја цената со количината од кошничката
                    return singlePrice * item.getQuantity();
                })
                .sum();
        totalPrice+=150;


        Context context = new Context();
        context.setVariables(variables);
// Бидејќи во HTML користиме ${items}, му ја даваме кошничката под тоа име
        context.setVariable("items", frontendCart);
        context.setVariable("totalPrice", totalPrice);

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

    public void sendAppointmentMail(String to, Map<String, Object> variables) throws MessagingException {

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process("AppointmentMail", context);

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
