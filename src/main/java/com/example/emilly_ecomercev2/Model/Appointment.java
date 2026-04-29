package com.example.emilly_ecomercev2.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String date;
    private String timeRange;
}
