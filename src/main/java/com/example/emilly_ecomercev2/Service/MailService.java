package com.example.emilly_ecomercev2.Service;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface MailService {
    public void sendAppointmentMail(String to, Map<String, Object> variables)throws MessagingException;
    public void sendOrderMail(String to, Map<String, Object> variables)throws MessagingException;
}
