package com.epf.Chessgame.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Préfixe des destinations de l'application
        registry.setApplicationDestinationPrefixes("/app");
        // Active le broker pour la gestion des messages
        registry.enableSimpleBroker("/topic");
    }
}
