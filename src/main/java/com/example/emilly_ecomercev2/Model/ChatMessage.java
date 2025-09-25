package com.example.emilly_ecomercev2.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String content;

    @Column(nullable = true)
    private boolean fromAdmin;

    private String timestamp;


    private String sender;


    private String recipient;

}
