package com.example.emilly_ecomercev2.Service;

import org.springframework.ai.chat.client.ChatClient;

public interface AiBotService {


    public String askGemini(String userMessage);
}
