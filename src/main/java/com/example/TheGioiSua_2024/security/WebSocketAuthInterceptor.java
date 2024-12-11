package com.example.TheGioiSua_2024.security;

import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.repository.UserRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    @Autowired
    JwtUtilities jwtUtilities;
    @Autowired
    UserRepository userRepository;

    // Sử dụng Map đơn giản để lưu trữ mối quan hệ sessionId và username
    private Map<String, String> sessionUsernameMap = new HashMap<>();

    public WebSocketAuthInterceptor(JwtUtilities jwtUtilities, UserRepository userRepository) {
        this.jwtUtilities = jwtUtilities;
        this.userRepository = userRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        String token = request.getURI().getQuery();
        if (token != null) {
            String ntoken[] = token.split("=");
            if (jwtUtilities.validateToken(ntoken[1])) {
                String user = jwtUtilities.extractUsername(ntoken[1]);
                User userDetails = userRepository.findByUsername(user).orElseThrow();
                if (userDetails != null) {
                    // Tạo sessionId và đặt thông tin vào attributes
                    String sessionId = UUID.randomUUID().toString();
                    attributes.put("sessionId", sessionId);  // Lưu sessionId
                    attributes.put("username", userDetails.getUsername());  // Lưu username
                    attributes.put("role", userDetails.getRole().getRoleName());  // Lưu username
                      // Cho phép kết nối WebSocket
                }
            }
        }
        return true;  // Từ chối nếu token không hợp lệ hoặc không tìm thấy người dùng
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
        // Thực hiện hành động sau handshake nếu cần thiết
    }

    // Phương thức để lấy username từ sessionId
    public String getUsernameBySessionId(String sessionId) {
        return sessionUsernameMap.get(sessionId);
    }
}
