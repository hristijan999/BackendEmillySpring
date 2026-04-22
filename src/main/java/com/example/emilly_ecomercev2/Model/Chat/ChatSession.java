package com.example.emilly_ecomercev2.Model.Chat;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;

    private String clientName;

    private Instant createdAt = Instant.now();

    private boolean active = true;

    public ChatSession(String clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }
}

