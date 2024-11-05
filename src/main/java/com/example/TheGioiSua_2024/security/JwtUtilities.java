/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class JwtUtilities {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long jwtExpiration;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String user = extractUsername(token);
    return (user.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  //    public String generateToken(String username , List<String> roles)
  public String generateToken(Long id, String username, String roleName) {
    String Id = String.valueOf(id);
    return Jwts.builder().claim("id", Id).setSubject(username).claim("role", roleName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS)))
        .signWith(SignatureAlgorithm.HS256, secret).compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      log.info("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace: {}", e);
    } catch (MalformedJwtException e) {
      log.info("Invalid JWT token.");
      log.trace("Invalid JWT token trace: {}", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.trace("Expired JWT token trace: {}", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.trace("Unsupported JWT token trace: {}", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace: {}", e);
    }
    return false;
  }

  public String getToken(HttpServletRequest httpServletRequest) {
    final String bearerToken = httpServletRequest.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    } // The part after "Bearer "
    return null;
  }

  // JwtUtilities.java
  public String generateVerificationToken(Long userId) {
    long expirationTime = 15 * 60 * 1000; // 15 phút tính bằng milliseconds
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public String generateRecoveryToken(Long userId) {
    long EXPIRATION_TIME = 1000 * 60 * 20; // 20 minutes

    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject("Password Recovery")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public Long verifyRecoveryToken(String token) {
    try {
      // Parse the token and extract claims
      Claims claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();

      // Check if the subject is valid for recovery purposes
      if (!"Password Recovery".equals(claims.getSubject())) {
        throw new IllegalArgumentException("Invalid token subject");
      }

      // Return the userId from claims
      return Long.parseLong(claims.get("userId").toString());
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
    } catch (io.jsonwebtoken.SignatureException | io.jsonwebtoken.MalformedJwtException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not verify token");
    }
  }

  public Long verifyVerificationToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    return Long.parseLong(claims.getSubject());
  }
}
