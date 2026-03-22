package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.service.DemoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @PostMapping("/login")
    public String demoLogin(HttpServletRequest request, HttpServletResponse response){
        Student demo = demoService.createDemoStudent();

        // вручную логиним студунта в Spring Security
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(demo, null, demo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // Save context in the session / сохраняем контекст в сессию
        HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        repo.saveContext(SecurityContextHolder.getContext(), request, response);

        return "redirect:/";
    }
}
