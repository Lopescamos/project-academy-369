package com.olimpiadas369.backend.identidad.infrastructure.hashing.bcrypt;

import com.olimpiadas369.backend.identidad.domain.services.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override public String hash(String raw) { return encoder.encode(raw); }
    @Override public boolean matches(String raw, String hash) { return encoder.matches(raw, hash); }
}
