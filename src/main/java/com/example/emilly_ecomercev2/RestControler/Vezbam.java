package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.Appointment;
import com.example.emilly_ecomercev2.Model.MailRequest;
import com.example.emilly_ecomercev2.Service.Impl.MailService;
import jakarta.mail.MessagingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("Admin/Vezbam")
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class Vezbam {


    @GetMapping("/proveri")
    public void vezbam(){
        System.out.println("VLEGVIT VO ADMIN");
    }

    @RestController
    @RequestMapping("/Mail")
    public static class SendMail {

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
            mailService.sendAppointmentMail(request.getEmail(), model);
        }
    }
}
