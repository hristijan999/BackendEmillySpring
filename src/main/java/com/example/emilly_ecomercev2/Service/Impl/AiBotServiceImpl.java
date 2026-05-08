package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Service.AiBotService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class AiBotServiceImpl implements AiBotService {
    private final ChatClient chatClient;
    public AiBotServiceImpl(ChatClient.Builder builder) {

        String naredbi="Ти си експерт за продажба во е-продавницата 'Emily E-shop'.\n" +
                "        Твоите правила се:\n" +
                "        najvazno pravilo vrakaj so kratki odgovori:\n" +
                "        1. Одговарај секогаш на македонски јазик \n" +
                "        2. Ако корисникот праша за работно време, кажи дека работиме од понеделник до сабота отварамае во 10 часот а затварама во 22 часот\n" +
                "        3. Ако корисникот праша за испорака, кажи дека е бесплатна и трае 2-3 работни дена.\n"+
                "        4. Ако корисникот праса правиме корекции безплатно. \n"+
                "        5. Не измислувај производи кои ги немаме. Ако не си сигурен, кажи му на корисникот дека ќе го поврзеш со жив оператор.\n"+
                "        6.Нашите продукти се изработрени пакум и свила. Ако те прашаат за квалитет, нагласи го тоа."
        ;
        this.chatClient = builder
                .defaultSystem(naredbi)
                .build();
    }

    public String askGemini(String userMessage) {
        try {
            return chatClient.prompt()
                    .options(GoogleGenAiChatOptions.builder()
                            .model("gemini-3.1-flash-lite-preview")
                            .build())
                    .user(userMessage)
                    .call()
                    .content();
        } catch (Exception e) {
            return "Грешка: " + e.getMessage();
        }
    }

}
