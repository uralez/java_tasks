package com.ayakovlev.interviewprep.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorConfig implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        System.out.println("ERROR STATUS CODE: " + statusCode);
        if (statusCode != null && statusCode == HttpStatus.NOT_FOUND.value()){
            return "error/404";
        }
        if (statusCode != null && statusCode == HttpStatus.FORBIDDEN.value()){
            return "error/403";
        }
        return "error/404"; // fallback
    }

    @GetMapping("/error/403")
    public String error403(){
        return "error/403";
    }
}
