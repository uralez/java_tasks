package com.ayakovlev.interviewprep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AuditConfig {


    @Bean
        public AuditorAware<String> auditorProvider(){
        return () -> Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication()
        )
                .filter(auth -> auth.isAuthenticated())
                .filter(auth -> !auth.getName().equals("anonymousUser"))
                .map(Authentication::getName);
        /*
        return new AuditorAware<String>(){
            @Override
            public Optional<String> getCurrentAuditor(){
                // Достаём объект аутентификации из контекста безопасности
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                // Если пользователь не залогинен - возвращаем пустой Optional
                if(authentication == null){
                    return Optional.empty();
                }
                // Если пользователь - "гость" (не прошёл авторизацию)
                if(!authentication.isAuthenticated()){
                    return Optional.empty();
                }
                // Spring Security для неалогиненных пользователей
                // возвращает не null, а специальную строку "anonymousUser"
                if(authentication.getName().equals("anonymousUser")){
                    return Optional.empty();
                }
                // Всё хорошо - возвращаем логин текущего пользователя
                return Optional.of(authentication.getName());
            }
        };
        */
    }
}
