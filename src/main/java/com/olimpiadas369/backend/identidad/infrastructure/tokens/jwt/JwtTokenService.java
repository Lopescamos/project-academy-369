package com.olimpiadas369.backend.identidad.infrastructure.tokens.jwt;

import com.olimpiadas369.backend.identidad.application.internal.outboundservices.tokens.AuthenticatedUser;
import com.olimpiadas369.backend.identidad.application.internal.outboundservices.tokens.TokenService;
import com.olimpiadas369.backend.identidad.domain.model.valueobjects.Rol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@EnableConfigurationProperties(JwtSettings.class)
public class JwtTokenService implements TokenService {

    private final JwtSettings settings;
    private final SecretKey key;

    public JwtTokenService(JwtSettings settings) {
        this.settings = settings;
        this.key = Keys.hmacShaKeyFor(settings.secret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String issue(Long usuarioId, String correo, Rol rol) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(settings.expirationMinutes() * 60L);
        return Jwts.builder()
                .subject(usuarioId.toString())
                .claim("correo", correo)
                .claim("rol", rol.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    @Override
    public Optional<AuthenticatedUser> verify(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return Optional.of(new AuthenticatedUser(
                    Long.parseLong(claims.getSubject()),
                    claims.get("correo", String.class),
                    Rol.valueOf(claims.get("rol", String.class))
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
