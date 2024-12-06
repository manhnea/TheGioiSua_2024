package com.example.TheGioiSua_2024.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final CustomerUserDetailsService customerUserDetailsService;
    private final JwtUtilities jwtUtilities;

    // Constructor để tiêm phụ thuộc
    @Autowired
    public WebSocketHandshakeInterceptor(CustomerUserDetailsService customerUserDetailsService, JwtUtilities jwtUtilities) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.jwtUtilities = jwtUtilities;
    }

    // Hàm xác thực token
    private Authentication authenticateToken(String token) {
        if (token != null && jwtUtilities.validateToken(token)) {
            // Lấy tên người dùng từ token
            String username = jwtUtilities.extractUsername(token);

            // Tải thông tin người dùng từ dịch vụ UserDetails
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                // Tạo Authentication từ UserDetails và trả về
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        }

        return null; // Trả về null nếu không hợp lệ hoặc không tìm thấy người dùng
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Lấy token từ query parameter (nếu có)
        String token = getTokenFromRequest(request);
        System.out.println("Token received: " + token);

        if (token != null && !token.isEmpty()) {
            Authentication authentication = authenticateToken(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication); // Thiết lập Authentication trong SecurityContext
            } else {
                System.out.println("Invalid token.");
            }
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
        // Thực hiện hành động sau khi handshake (nếu cần thiết)
    }

    // Hàm phụ để lấy token từ URL
    private String getTokenFromRequest(ServerHttpRequest request) {
        String token = null;
        String query = request.getURI().getQuery();

        // Kiểm tra nếu có query parameter và lấy token
        if (query != null && query.contains("token=")) {
            token = query.split("token=")[1];
        }

        return token;
    }
}

