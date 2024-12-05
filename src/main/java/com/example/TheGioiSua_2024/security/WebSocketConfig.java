package com.example.TheGioiSua_2024.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    JwtUtilities jwtUtilities;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    public WebSocketConfig(JwtUtilities jwtUtilities, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtUtilities = jwtUtilities;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    

   
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new WebSocketAuthInterceptor(jwtUtilities, customerUserDetailsService)) // Thêm interceptor
                .setAllowedOrigins("http://localhost:3000", "http://160.30.21.47:1234",
                        "http://160.30.21.47:3000", "http://160.30.21.47:3004", "http://localhost:3004/")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // Cấu hình message broker
        registry.setApplicationDestinationPrefixes("/app"); // Thêm prefix cho các destination ứng dụng
    }
}
