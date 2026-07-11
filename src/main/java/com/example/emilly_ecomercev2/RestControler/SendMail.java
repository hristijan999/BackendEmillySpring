package com.example.emilly_ecomercev2.RestControler;


import com.example.emilly_ecomercev2.Model.Appointment;
import com.example.emilly_ecomercev2.Model.MailRequest;
import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Service.Impl.MailServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Api/Mail")
public  class SendMail {

    public final MailServiceImpl mailServiceImpl;

    public SendMail(MailServiceImpl mailServiceImpl) {
        this.mailServiceImpl = mailServiceImpl;
    }

    @PostMapping("/Order")
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
//        List<Roba> cart = (List<Roba>) model.get("cart");
//        cart.forEach(r -> System.out.println(r.getId() + " " + r.getSizePicked()));

        mailServiceImpl.sendOrderMail(request.getEmail(), model);
    }

    @PostMapping("/Appointment")
    public void sendAppointment(@RequestBody Appointment request) throws MessagingException {
        Map<String, Object> model = Map.of(
                "name", request.getName(),
                "surname", request.getSurname(),
                "email", request.getEmail(),
                "phone", request.getPhone(),
                "date",request.getDate(),
                "timeRange",request.getTimeRange()
        );
        mailServiceImpl.sendAppointmentMail(request.getEmail(), model);
    }
}


