package com.vi5hnu.app_service.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class JwtService {
    @Value("${auth.service.url}") String authServiceUrl;
    private final WebClient.Builder webClientBuilder;
    WebClient webClient;

    public JwtService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        this.webClient=this.webClientBuilder.build();
    }

    public Claims getClaims(String token,Key jwtSecret){
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getClaims(String token,String jwtSecret){
        return Jwts.parserBuilder()
                .setSigningKey(key(jwtSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key key(String secret){
        final byte[] decodedSecret= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedSecret);
    }

    public String getTokenFromRequest(HttpServletRequest request){
        final List<Cookie> matchedCookies=request.getCookies()==null ? Collections.emptyList() : Arrays.stream(request.getCookies()).filter(ck->ck.getName().equals("jwt")).toList();
        String token= matchedCookies.isEmpty() ? null : matchedCookies.get(0).getValue();
        if(token!=null) return token;

        String bearerTokenRaw=request.getHeader("Authorization");
        if(bearerTokenRaw==null || !bearerTokenRaw.startsWith("Bearer")){return null;}
        return bearerTokenRaw.substring(7);
    }

    public boolean verifyToken(String token) {
        try {
            webClient.post()
                    .uri(authServiceUrl + "/jwt/verify") // Load-balanced service call
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> {
                        // Handle non-2xx responses (e.g., invalid token or server errors)
                        return Mono.error(new RuntimeException("Token verification failed: " + clientResponse.statusCode()));
                    })
                    .bodyToMono(Void.class) // No need to map the body if we're only interested in the status
                    .block(); // Block for a response
            return true; // If we reach here, the response was 2xx
        } catch (Exception e) {
            // Log the exception or handle it as needed
            System.err.println("Error verifying token: " + e.getMessage());
            return false; // Token verification failed
        }
    }
}
