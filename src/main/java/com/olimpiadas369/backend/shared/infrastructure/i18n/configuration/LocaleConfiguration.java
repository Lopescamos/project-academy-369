package com.olimpiadas369.backend.shared.infrastructure.i18n.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Resolves the request locale from the {@code Accept-Language} header.
 * Spanish is the default because Olimpiadas 369 targets Peruvian schools; English
 * is still served when the client explicitly asks for it.
 */
@Configuration
public class LocaleConfiguration {

    public static final Locale SPANISH = Locale.forLanguageTag("es");

    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(SPANISH);
        resolver.setSupportedLocales(List.of(SPANISH, Locale.ENGLISH));
        return resolver;
    }
}
