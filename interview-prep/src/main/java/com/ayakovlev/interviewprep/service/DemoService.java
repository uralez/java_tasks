package com.ayakovlev.interviewprep.service;

import com.ayakovlev.interviewprep.entity.Role;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.repository.AnswerRepository;
import com.ayakovlev.interviewprep.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {
    private final StudentRepository studentRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Student createDemoStudent() {
        Student template = studentRepository.findByLogin("demo_template") // returns Optional
                .orElseThrow(() -> new RuntimeException("Demo template not found"));

        String demoLogin = "demo_" + UUID.randomUUID().toString().substring(0, 8);
        Student demo = new Student();
        demo.setLogin(demoLogin);
        demo.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        demo.setName("Guest");
        demo.setRole(Role.DEMO);
        studentRepository.save(demo);
        answerRepository.copyAnswersFromTemplate(template.getId(), demo.getId());
        return demo;
    }

    /*
    0 — в 0 секунд
    0 — в 0 минут
    * — в любой час (0, 1, 2 ... 23)
    * — в любой день месяца
    * — в любой месяц
    * — в любой день недели
     */
    @Scheduled(cron = "0 0 * * * *") // once an hour
    @Transactional
    public void deleteExpiredDemoAccounts() {
        // отсекать
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        int deleted = studentRepository.deleteByStudentAndDcreBefore(Role.DEMO, cutoff);
        log.info("Deleted expired DEMO accounts: {}", deleted);
    }
}
