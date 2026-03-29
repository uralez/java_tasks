package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.AnswerPageDto;
import com.ayakovlev.interviewprep.entity.Answer;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.service.AnswerService;
import com.ayakovlev.interviewprep.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService     answerService;
    private final EvaluatorService  evaluatorService;

    @GetMapping("/{id}")
    public String answerPage(
        @PathVariable Long id,
        @AuthenticationPrincipal Student student,
        Model model
    ){
        Answer answer = answerService.findById(id);

        if (!answer.getStudent().getId().equals(student.getId())
                && !student.getRole().name().equals("ADMIN")){
            return "redirect:/error/403";
        }

        List<Answer> allAnswers = answerService.findByStudentAndQuestion(
                answer.getStudent(),
                answer.getQuestion().getId()
        );

        List<AnswerPageDto> dtos = IntStream.range(0, allAnswers.size())
                .mapToObj(i -> new AnswerPageDto(allAnswers.get(i), i + 1))
                .toList();

        int currentIndex = IntStream.range(0, allAnswers.size())
                .filter(i -> allAnswers.get(i).getId().equals(answer.getId()))
                .findFirst()
                .orElse(0);

        Double avgTopic=answerService.avgGradeByTopic(answer.getStudent(), answer.getQuestion().getTopic().getId());
        Double avgQuestion  = answerService.avgGradeByQuestion(answer.getStudent(), answer.getQuestion().getId());

        model.addAttribute("answer",        answer);
        model.addAttribute("allAnswers",    dtos);
        model.addAttribute("currentIndex",  currentIndex);
        model.addAttribute("evaluators",    evaluatorService.findAll());
        model.addAttribute("avgTopic",      avgTopic);
        model.addAttribute("avgQuestion",   avgQuestion);

        return "answer";
    }
}
