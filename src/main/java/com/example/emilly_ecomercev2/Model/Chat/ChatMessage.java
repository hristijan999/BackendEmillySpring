package com.example.emilly_ecomercev2.Model.Chat;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean admin;
    private String clientId;
    private String content;
    private String fromm;
    private Instant timestamp = Instant.now();

    @ManyToOne
    @JoinColumn(name = "chat_session_id")
    private ChatSession chatSession;
}