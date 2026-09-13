package com.ayakovlev.interviewprep.config;

import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.StudentRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
public class LocaleSavingSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final StudentRepository studentRepository;
    private final LocaleResolver localeResolver;

    public LocaleSavingSuccessHandler(StudentRepository studentRepository, LocaleResolver localeResolver){
        this.studentRepository = studentRepository;
        this.localeResolver = localeResolver;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("Session ID on login: {}", request.getSession().getId());

        boolean explicitlyChosen = request.getCookies() != null &&
                Arrays.stream(request.getCookies())
                        .anyMatch(cookie -> "langExplicitlyChosen".equals(cookie.getName()));

        if (authentication.getPrincipal() instanceof Student student){
            if (explicitlyChosen) {
                Locale locale = localeResolver.resolveLocale(request);
                String currentLang = locale.getLanguage();
//            String currentLang = RequestContextUtils.getLocale(request).getLanguage();
                log.info("onAuthenticationSuccess(): Session_ID on login: {}, currentLang: {}", request.getSession().getId(), currentLang);
                student.setPreferredLanguage(currentLang);
                studentRepository.save(student);
            } else if (student.getPreferredLanguage() != null){
                Locale savedLocale = Locale.forLanguageTag(student.getPreferredLanguage());
                localeResolver.setLocale(request, response, savedLocale);
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
