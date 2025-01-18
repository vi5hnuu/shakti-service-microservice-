package com.vi5hnu.app_service.security.filters;

import com.vi5hnu.app_service.Dto.UserRole;
import com.vi5hnu.app_service.configuration.SecurityConfig;
import com.vi5hnu.app_service.services.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${app.jwt-secret}") private String jwtSecret;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            String token= jwtService.getTokenFromRequest(request);
            if(token==null || !this.jwtService.verifyToken(token)) {
                filterChain.doFilter(request,response);return;
            }

            //check validity of token
            final Claims claims= this.jwtService.getClaims(token,jwtService.key(jwtSecret));
            final String userId= claims.getSubject();//userId
            final ArrayList<String> roles=claims.get("roles", ArrayList.class);

            //authorities
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userId,null,roles.stream().map(SimpleGrantedAuthority::new).toList());//authenticated user
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);//done handle api request
        }catch (Exception e){
            filterChain.doFilter(request,response);//done handle api request
        }
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerTokenRaw=request.getHeader("Authorization");
        if(bearerTokenRaw==null || !bearerTokenRaw.startsWith("Bearer")){return null;}
        return bearerTokenRaw.substring(7);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return Arrays.stream(SecurityConfig.ENDPOINTS_WHITELIST).anyMatch(endpoint-> new AntPathRequestMatcher(endpoint).matches(request));
    }
}