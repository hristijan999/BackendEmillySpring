package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Chat.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    Optional<ChatSession> findByClientId(String clientId);

    List<ChatSession> findByActive(boolean active);
}

