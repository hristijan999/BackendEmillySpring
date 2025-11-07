package com.example.emilly_ecomercev2.ChatController;

import com.example.emilly_ecomercev2.Model.Chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(ChatMessage message, SimpMessageHeaderAccessor headers) {
        // Access WS session and shared HTTP session attributes if needed
        String sessionId = headers.getSessionId();
        Map<String, Object> sess = headers.getSessionAttributes(); // e.g., user info from login

        // Broadcast to admin stream
        messagingTemplate.convertAndSend("/topic/admin", message);

        // Per-client stream
        if (StringUtils.hasText(message.getClientId())) {
            messagingTemplate.convertAndSend("/topic/user." + message.getClientId(), message);
        }
    }
}
