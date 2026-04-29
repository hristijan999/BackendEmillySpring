package com.example.emilly_ecomercev2.Service;

import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import java.util.List;
import java.util.Optional;

public interface ChatSessionService {

    ChatSession getOrCreateSession(String clientId, String clientName);

    Optional<ChatSession> getSession(String clientId);

    List<ChatSession> getAllSessions();

    void deactivateSession(String clientId);

    List<ChatSession> getActiveSessions();
}

