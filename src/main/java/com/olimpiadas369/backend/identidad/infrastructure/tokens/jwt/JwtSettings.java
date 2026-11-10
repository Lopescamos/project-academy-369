package com.olimpiadas369.backend.identidad.infrastructure.tokens.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtSettings(String secret, long expirationMinutes) {}
