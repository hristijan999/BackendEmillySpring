package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.MailRequest;
import com.example.emilly_ecomercev2.Service.Impl.MailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/Mail")
public class SendMail {

    public final MailService mailService;

    public SendMail(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public void sendMail(@RequestBody MailRequest request) throws MessagingException {
        Map<String, Object> model = Map.of(
                "name", request.getName(),
                "surname", request.getSurname(),
                "email", request.getEmail(),
                "phone", request.getPhone(),
                "city",request.getCity(),
                "location", request.getLocation(),
                "cart", request.getCart()
        );
        System.out.println(model.get("cart"));
        mailService.sendOrderMail(request.getEmail(), model);
    }
}
