package com.olimpiadas369.backend.vision.infrastructure.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vision")
public record VisionSettings(String provider, String apiKey, String model) {}
