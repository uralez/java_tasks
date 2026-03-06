package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.StudentRegisterDto;
import com.ayakovlev.interviewprep.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("dto", new StudentRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("dto") StudentRegisterDto dto){
        studentService.register(dto.getLogin(), dto.getPassword(), dto.getName(), dto.getEmail());
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("currentTime",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                )
        );
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
