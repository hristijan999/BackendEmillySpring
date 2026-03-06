package com.example.emilly_ecomercev2.Model.Chat;

import lombok.Data;

import java.time.Instant;

@Data
public class ChatMessage {
    private boolean admin;
    private String clientId;
    private String content;
    private String from;
    private Instant timestamp = Instant.now();
}