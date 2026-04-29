package com.example.emilly_ecomercev2.ChatController;

import com.example.emilly_ecomercev2.Model.Chat.ChatMessage;
import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import com.example.emilly_ecomercev2.Repository.ChatMessageRepository;
import com.example.emilly_ecomercev2.Service.AiBotService;
import com.example.emilly_ecomercev2.Service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatSessionService chatSessionService;
    private final ChatMessageRepository chatMessageRepository;
    private final AiBotService aiService;

    @MessageMapping("/chat.send")
    public void send(ChatMessage message, SimpMessageHeaderAccessor headers) {
        if (!StringUtils.hasText(message.getClientId())) return;

        // 1. Најди или креирај сесија и зачувај ја пораката од корисникот
        ChatSession session = chatSessionService.getOrCreateSession(message.getClientId(), message.getFromm());
        message.setChatSession(session);
        chatMessageRepository.save(message);

        // 2. Прати ја оригиналната порака до админот и до корисникот (да ја видат веднаш)
        messagingTemplate.convertAndSend("/topic/admin", message);
        messagingTemplate.convertAndSend("/topic/user." + message.getClientId(), message);

        // 3. АКО пораката НЕ е од админ, тогаш одговара ботот
        if (!message.isAdmin()) {
            try {
                System.out.println("Прашувам Gemini..."); // ЛОГ за дебагирање
                String aiContent = aiService.askGemini(message.getContent());
                System.out.println("Gemini одговори: " + aiContent);

                ChatMessage botMessage = new ChatMessage();
                botMessage.setChatSession(session);
                botMessage.setClientId(message.getClientId());
                botMessage.setContent(aiContent);
                botMessage.setFromm("SmartBot");
                botMessage.setAdmin(true);
                botMessage.setTimestamp(Instant.now());

                chatMessageRepository.save(botMessage);
                Thread.sleep(5000);
                messagingTemplate.convertAndSend("/topic/admin", botMessage);
                messagingTemplate.convertAndSend("/topic/user." + message.getClientId(), botMessage);
            } catch (Exception e) {
                System.err.println("ГРЕШКА ПРИ ПОВИК ДО GEMINI: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
