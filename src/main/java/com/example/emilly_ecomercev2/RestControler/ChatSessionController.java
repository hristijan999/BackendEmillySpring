package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.Chat.ChatMessage;
import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import com.example.emilly_ecomercev2.Repository.ChatMessageRepository;
import com.example.emilly_ecomercev2.Service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/chat")
@RequiredArgsConstructor
public class ChatSessionController {

    private final ChatSessionService chatSessionService;
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/sessions")
    public List<ChatSession> getActiveSessions() {
        return chatSessionService.getActiveSessions();
    }

    @GetMapping("/sessions/{sessionId}/messages")
    public List<ChatMessage> getChatHistory(@PathVariable Long sessionId) {
        return chatMessageRepository.findByChatSessionId(sessionId);
    }
}

