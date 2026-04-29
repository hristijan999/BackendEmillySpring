package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Repository.ChatMessageRepository;
import com.example.emilly_ecomercev2.Service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/chat")
@RequiredArgsConstructor
public class ChatSession {

    private final ChatSessionService chatSessionService;
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/sessions")
    public List<com.example.emilly_ecomercev2.Model.Chat.ChatSession> getActiveSessions() {
        return chatSessionService.getActiveSessions();
    }
    @GetMapping("/AllSessions")
    public List<com.example.emilly_ecomercev2.Model.Chat.ChatSession> getAllSessions() {
        return chatSessionService.getAllSessions();
    }


}

