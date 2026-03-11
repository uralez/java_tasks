package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.StudentRegisterDto;
import com.ayakovlev.interviewprep.entity.*;
import com.ayakovlev.interviewprep.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final StudentService    studentService;
    private final TopicService      topicService;
    private final EvaluatorService  evaluatorService;
    private final QuestionService   questionService;
    private final AnswerService     answerService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("dto", new StudentRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("dto") StudentRegisterDto dto, BindingResult result){
        if(result.hasErrors()){
            return "register"; // возвращаем форму с ошибками
        }

        studentService.register(dto.getLogin(), dto.getPassword(), dto.getName(), dto.getEmail());
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal Student student){
        model.addAttribute("currentTime",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                )
        );
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("evaluators", evaluatorService.findAll());
        model.addAttribute("today", LocalDate.now().toString());
        model.addAttribute("topicStats", answerService.findTopicsWithQuestions(student));
        return "index";
    }

    @PostMapping("/answer")
    public String saveAnswer(
            @RequestParam Long questionId,
            @RequestParam String text,
            @RequestParam BigDecimal grade,
            @RequestParam Long evaluatorId,
            @RequestParam LocalDate answerDate,
            @AuthenticationPrincipal Student student // @AuthenticationPrincipal Student student — Spring Security
                                                    // автоматически подставляет текущего залогиненного пользователя.
                                                    // Это работает потому что Student реализует UserDetails.
    ){
        Question question = new Question();
        question.setId(questionId);

        Evaluator evaluator = new Evaluator();
        evaluator.setId(evaluatorId);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setText(text);
        answer.setGrade(grade);
        answer.setEvaluator(evaluator);
        answer.setAnswerDate(answerDate);
        answer.setStudent(student);

        answerService.save(answer);
        // After submitting the form, the user may refresh the page.
        // Without PRG (Post/Redirect/Get), the form would be submitted again.
        // To avoid this, we redirect to a GET request.
        return "redirect:/";
    }

    /*
    * @ResponseBody говорит Spring что возвращаемый объект нужно сериализовать в JSON и отправить прямо в тело
    * HTTP-ответа, а не искать шаблон Thymeleaf с таким именем.
    *
    * Без @ResponseBody Spring попытался бы найти шаблон templates/[список вопросов].html и упал бы с ошибкой.
    * С @ResponseBody Spring берёт список List<Question>, преобразует его в JSON через Jackson и отправляет браузеру:
    * json[
    *   {"id": 1, "text": "Чем == отличается от equals()?", ...},
    *   {"id": 2, "text": "Как работает hashCode()?", ...}
    * ]
    * Именно этот JSON получает fetch() в JavaScript и заполняет им список вопросов.
    * */
    @GetMapping("/questions")
    @ResponseBody
    public List<Question> getQuestionsByTopic(@RequestParam Long topicId){
        Topic topic = new Topic();
        topic.setId(topicId);
        return questionService.findByTopic(topic);
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/grades")
    @ResponseBody
    public List<GradePointDto> getGradesByQuestion(
            @RequestParam Long questionId,
            @AuthenticationPrincipal Student student){
        return answerService.findGradesByQuestion(questionId, student);
    }
}
