package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatSessionId(Long sessionId);
}

