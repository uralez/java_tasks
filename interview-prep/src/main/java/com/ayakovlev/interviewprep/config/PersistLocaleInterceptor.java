package com.ayakovlev.interviewprep.config;

import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class PersistLocaleInterceptor implements HandlerInterceptor {
    private final StudentRepository studentRepository;

    public PersistLocaleInterceptor(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler){
        String lang = request.getParameter("lang");
        log.info("preHandle(): Session_ID on DE click: {}, lang: {}", request.getSession().getId(), lang);
        if(lang == null){
            return true;
        }

        Cookie marker = new Cookie("langExplicitlyChosen", "true");
        marker.setPath("/");
        response.addCookie(marker);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Student student) {
            student.setPreferredLanguage(lang);
            studentRepository.save(student);
        }

        return true;
    }
}
