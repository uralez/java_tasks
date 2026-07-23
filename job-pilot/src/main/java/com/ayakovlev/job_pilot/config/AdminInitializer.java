package com.ayakovlev.job_pilot.config;

import com.ayakovlev.job_pilot.entity.User;
import com.ayakovlev.job_pilot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.login}")
    private String adminLogin;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.email}")
    private String adminEmail;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if(!userRepository.existsByLogin(adminLogin)){
            User admin = new User();
            admin.setLogin(adminLogin);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setName(adminName);
            admin.setEmail(adminEmail);
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
    }
}
