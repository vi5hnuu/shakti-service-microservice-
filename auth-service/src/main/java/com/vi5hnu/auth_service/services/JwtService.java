package com.vi5hnu.auth_service.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    public String generateJwtToken(String userId,long jwtExpirationMilliSeconds,String jwtSecret){
        final Date curDate=new Date();
        final Date expireDate=new Date(curDate.getTime()+jwtExpirationMilliSeconds);
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(curDate)
                .setExpiration(expireDate)
                .signWith(key(jwtSecret), SignatureAlgorithm.HS256).compact();
    }

    public Claims getClaims(String token,Key jwtSecret){
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key key(String secret){
        final byte[] decodedSecret= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedSecret);
    }
}
