package com.example.jungleboarding.login.jwt;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@PropertySource("classpath:jwt.yml")
public class TokenProvider {

    private  static  final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private final long expirationMinutes;

    private final long refreshExpirationHours;
    private final String issuer;
    private final RefreshJwtTokenRepository refreshJwtTokenRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-minutes}") long expirationHours,
            @Value("${refresh-expiration-hours}") long refreshExpirationHours,
            @Value("${issuer}") String issuer,
            RefreshJwtTokenRepository refreshJwtTokenRepository
    ) {
        this.secretKey = secretKey;
        this.expirationMinutes = expirationHours;
        this.refreshExpirationHours = refreshExpirationHours;
        this.issuer = issuer;
        this.refreshJwtTokenRepository = refreshJwtTokenRepository;
    }
    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        if(refreshJwtTokenRepository.findByUserId(subject.split(":")[0]).isEmpty()){
            throw new ExpiredJwtException(null, null, "Refresh token expired.");
        }
        return createAccessToken(subject);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String userId = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
        refreshJwtTokenRepository.findByUserId(userId)
                .filter(refreshJwtToken -> refreshJwtToken.validRefreshToken(refreshToken))
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh token expired."));
    }

    private Jws<Claims> validateAndParseToken(String token) {	// validateTokenAndGetSubject 에서 따로 분리
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper
                .readValue(new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1])
                        , StandardCharsets.UTF_8)
                        , Map.class)
                .get("sub")
                .toString();
    }
    public String createAccessToken(String userSpecification) {

        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(userSpecification)  // JWT 토큰 제목
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    public String createRefreshToken(){
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours,ChronoUnit.HOURS)))
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        String jsonSub = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return jsonSub;
    }
}
