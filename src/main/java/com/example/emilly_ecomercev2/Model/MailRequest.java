package com.example.emilly_ecomercev2.Model;


import lombok.Data;

import java.util.List;

@Data
public class MailRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String location;
    private String city;
    private List<Roba> cart;
}