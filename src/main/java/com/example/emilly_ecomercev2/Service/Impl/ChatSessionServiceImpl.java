package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import com.example.emilly_ecomercev2.Repository.ChatSessionRepository;
import com.example.emilly_ecomercev2.Service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatSessionServiceImpl implements ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;

    @Override
    public ChatSession getOrCreateSession(String clientId, String clientName) {
        return chatSessionRepository.findByClientId(clientId)
                .orElseGet(() -> {
                    ChatSession newSession = new ChatSession(clientId, clientName);
                    return chatSessionRepository.save(newSession);
                });
    }

    @Override
    public Optional<ChatSession> getSession(String clientId) {
        return chatSessionRepository.findByClientId(clientId);
    }

    @Override
    public List<ChatSession> getAllSessions() {
        return chatSessionRepository.findAll();
    }

    @Override
    public void deactivateSession(String clientId) {
        chatSessionRepository.findByClientId(clientId).ifPresent(session -> {
            session.setActive(false);
            chatSessionRepository.save(session);
        });
    }

    @Override
    public List<ChatSession> getActiveSessions() {
        return chatSessionRepository.findByActive(true);
    }
}

