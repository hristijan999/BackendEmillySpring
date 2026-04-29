package com.example.emilly_ecomercev2._WebSocketConfig;

import com.example.emilly_ecomercev2.Service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatSessionService chatSessionService;

    // Track clientId per session to use on disconnect (native headers may be absent there)
    private final Map<String, String> sessionToClientId = new ConcurrentHashMap<>();

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String clientId = accessor.getFirstNativeHeader("clientId"); // client should send this native header
        String clientName = accessor.getFirstNativeHeader("clientName"); // client should send this native header

        if (clientId != null && !clientId.isBlank()) {
            sessionToClientId.put(sessionId != null ? sessionId : "unknown", clientId); // Protect against null sessionId
            chatSessionService.getOrCreateSession(clientId, clientName != null ? clientName : "unknown");
        }

        log.info("WS CONNECT: sessionId={}, clientId={}", sessionId, clientId);

        simpMessageSendingOperations.convertAndSend(
                "/topic/admin",
                Map.of(
                        "event", "CONNECTED",
                        "sessionId", sessionId != null ? sessionId : "unknown",
                        "clientId", clientId != null ? clientId : "unknown",
                        "timestamp", Instant.now().toString()
                )
        );

        if (clientId != null && !clientId.isBlank()) {
            simpMessageSendingOperations.convertAndSend(
                    "/topic/user." + clientId,
                    Map.of(
                            "event", "WELCOME",
                            "message", "Connected",
                            "timestamp", Instant.now().toString()
                    )
            );
        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String clientId = sessionId != null ? sessionToClientId.remove(sessionId) : null;

        if (clientId != null) {
            chatSessionService.deactivateSession(clientId);
        }

        log.info("WS DISCONNECT: sessionId={}, clientId={}", sessionId, clientId);

        simpMessageSendingOperations.convertAndSend(
                "/topic/admin",
                Map.of(
                        "event", "DISCONNECTED",
                        "sessionId", sessionId != null ? sessionId : "unknown",
                        "clientId", clientId != null ? clientId : "unknown",
                        "timestamp", Instant.now().toString()
                )
        );
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("WS SUBSCRIBE: sessionId={}, destination={}", accessor.getSessionId(), accessor.getDestination());
    }

    @EventListener
    public void handleUnsubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("WS UNSUBSCRIBE: sessionId={}, subscriptionId={}", accessor.getSessionId(), accessor.getSubscriptionId());
    }
}
