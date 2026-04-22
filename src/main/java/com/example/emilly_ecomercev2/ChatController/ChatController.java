package com.example.emilly_ecomercev2.ChatController;

import com.example.emilly_ecomercev2.Model.Chat.ChatMessage;
import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import com.example.emilly_ecomercev2.Repository.ChatMessageRepository;
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

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatSessionService chatSessionService;
    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat.send")
    public void send(ChatMessage message, SimpMessageHeaderAccessor headers) {
        // Access WS session and shared HTTP session attributes if needed

        // Broadcast to admin stream
        messagingTemplate.convertAndSend("/topic/admin", message);

        // Per-client stream
        if (StringUtils.hasText(message.getClientId())) {
            ChatSession session = chatSessionService.getOrCreateSession(message.getClientId(), message.getFromm());
            message.setChatSession(session);
            chatMessageRepository.save(message);
            messagingTemplate.convertAndSend("/topic/user." + message.getClientId(), message);
            System.out.println(message.getClientId());
        }

        System.out.println(headers);
        System.out.println(message.getContent());
        System.out.println(message);
    }


}
