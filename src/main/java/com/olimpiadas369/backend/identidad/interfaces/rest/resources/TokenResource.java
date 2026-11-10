package com.olimpiadas369.backend.identidad.interfaces.rest.resources;

public record TokenResource(String token, String tokenType) {
    public TokenResource(String token) { this(token, "Bearer"); }
}
