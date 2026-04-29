package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Chat.ChatMessage;
import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatSession> findByChatSessionId(Long sessionId);
}

