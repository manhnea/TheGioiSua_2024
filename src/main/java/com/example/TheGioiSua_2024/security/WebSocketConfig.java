package com.example.TheGioiSua_2024.security;

import com.example.TheGioiSua_2024.repository.UserRepository;
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
    UserRepository userRepository;

    public WebSocketConfig(JwtUtilities jwtUtilities, UserRepository userRepository) {
        this.jwtUtilities = jwtUtilities;
        this.userRepository = userRepository;
    }

    
    

   
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new WebSocketAuthInterceptor(jwtUtilities, userRepository)) // Thêm interceptor
                .setAllowedOrigins("http://localhost:3000", "http://160.30.21.47:1234",
                        "http://160.30.21.47:3000", "http://160.30.21.47:3004", "http://localhost:3004/")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic","/user");  // Cấu hình message broker
        registry.setApplicationDestinationPrefixes("/app"); // Thêm prefix cho các destination ứng dụng
        registry.setUserDestinationPrefix("/user");
    }
}
