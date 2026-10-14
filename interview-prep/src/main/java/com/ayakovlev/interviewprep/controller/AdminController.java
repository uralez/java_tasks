package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.QuestionFormDto;
import com.ayakovlev.interviewprep.dto.TopicFormDto;
import com.ayakovlev.interviewprep.dto.TranslateRequestDto;
import com.ayakovlev.interviewprep.entity.*;
import com.ayakovlev.interviewprep.service.AnswerService;
import com.ayakovlev.interviewprep.service.QuestionService;
import com.ayakovlev.interviewprep.service.TopicService;
import com.ayakovlev.interviewprep.service.exception.InvalidOrderNumberException;
import com.ayakovlev.interviewprep.service.exception.QuestionNotFoundException;
import com.ayakovlev.interviewprep.service.exception.TopicHasQuestionsException;
import com.ayakovlev.interviewprep.service.exception.TopicNotFoundException;
import com.ayakovlev.interviewprep.service.translation.DeepLTranslationProvider;
import com.ayakovlev.interviewprep.service.translation.MyMemoryTranslationProvider;
import com.ayakovlev.interviewprep.service.translation.TranslationException;
import com.ayakovlev.interviewprep.service.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final TopicService topicService;
    private final TranslationService translationService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    private final MessageSource messageSource;
    private final DeepLTranslationProvider deepL;
    private final MyMemoryTranslationProvider myMemory;

    @GetMapping("/test")
    public String adminTest(){
        return "index"; // просто, чтобы маршрут существовал
    }

    /**
     * Saves changes to an existing topic submitted from the topic-form page.
     *
     * @param id                the id of the topic being updated
     * @param dto               the submitted form data (order number and translations)
     * @param redirectAttributes used to pass an error message on redirect if the topic is not found
     * @param model             used to pass data back to the topic-form view when order number validation failes
     * @param locale            the current user's locale, used to localize the error message
     * @return                  redirect to the home page
     */
    @PostMapping("/topic/{id}")
    public String updateTopic(@PathVariable Long id, TopicFormDto dto, RedirectAttributes redirectAttributes, Model model, Locale locale){
        try {
            Topic topic = topicService.updateTopic(id, dto);

            String header = messageSource.getMessage(
                    "admin.topic.updated", new Object[]{topic.getId(), topic.getOrderNumber()}, locale);
            StringBuilder sb = new StringBuilder(header).append("\n");
            topic.getTranslations().forEach(t ->
                    sb.append(t.getLocale().toUpperCase()).append(": ").append(t.getName()).append("\n"));

            redirectAttributes.addFlashAttribute("successMessage", sb.toString());
        } catch (InvalidOrderNumberException ex) {
            String errorMessage = messageSource.getMessage(
                    "admin.form.orderNumber.invalid", new Object[]{ex.getMaxAllowed()}, locale);
            model.addAttribute("orderNumber", ex.getMaxAllowed());
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("errorMessage", errorMessage);
            return "admin/topic-form";
        } catch (TopicNotFoundException ex) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/";
    }

    @PostMapping("/topic")
    public String saveTopic(TopicFormDto dto, RedirectAttributes redirectAttributes, Model model, Locale locale){
        try {
            Topic topic = topicService.createTopic(dto);

            String header = messageSource.getMessage(
                    "admin.topic.created", new Object[]{topic.getId(), topic.getOrderNumber()}, locale
            );
            StringBuilder sb = new StringBuilder(header).append("\n");
            topic.getTranslations().forEach(t ->
                    sb.append(t.getLocale().toUpperCase()).append(": ").append(t.getName()).append("\n"));

            // ... we need to pass it through flash or common attrinbute
            redirectAttributes.addFlashAttribute("successMessage", sb.toString());
            return "redirect:/";
        } catch (InvalidOrderNumberException ex) {
            String errorMessage = messageSource.getMessage(
                    "admin.form.orderNumber.invalid", new Object[]{ex.getMaxAllowed()}, locale);
            model.addAttribute("orderNumber", ex.getMaxAllowed());
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("errorMessage", errorMessage);
            return "admin/topic-form";
        }
    }

    @GetMapping("/topic/new")
    public String newTopicForm(Model model){
        long nextOrderNumber = topicService.getCount() + 1;
        model.addAttribute("orderNumber", nextOrderNumber);
        model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
        return "admin/topic-form";
    }

    /**
     * Navigates from the main page to the administrative page for editing the theme.
     *
     * @param id                the id of the topic to edit
     * @param model             the model used to pass data to the topic-form view
     * @param redirectAttributes used to pass an error message on redirect if the topic is not found
     * @param locale            the current user's locale, used to localize the error message
     * @return the topic-form view, or a redirect to the home page if the topic is not found
     */
    @GetMapping("/topic/{id}/edit")
    public String editTopicForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, Locale locale){
        try {
            Topic topic = topicService.findById(id);

            List<TopicTranslation> translations = topic.getTranslations();
            Map<String, String> translationMap = translations.stream()
                    .collect(Collectors.toMap(t -> t.getLocale().toUpperCase(),
                            TopicTranslation::getName
                    ));

            model.addAttribute("orderNumber", topic.getOrderNumber());
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("translations", translationMap);
            model.addAttribute("topicId", topic.getId());

            return "admin/topic-form";
        } catch (TopicNotFoundException e) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }
    }

    @PostMapping("/topic/{id}/delete")
    public String deleteTopic(@PathVariable Long id, RedirectAttributes redirectAttributes, Locale locale){
        try {
            Topic topic = topicService.deleteTopic(id);

            String header = messageSource.getMessage(
                    "admin.topic.deleted", new Object[]{topic.getId(), topic.getOrderNumber()}, locale
            );
            StringBuilder sb = new StringBuilder(header).append("\n");
            topic.getTranslations().forEach(t ->
                    sb.append(t.getLocale().toUpperCase()).append(": ").append(t.getName()).append("\n"));

            redirectAttributes.addFlashAttribute("successMessage", sb.toString());
        } catch (TopicNotFoundException exception) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        } catch (TopicHasQuestionsException exception) {
            String errorMessage = messageSource.getMessage("admin.topic.hasQuestions", new Object[]{exception.getQuestionCount()}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        return "redirect:/";
    }

    @GetMapping("/topic/{topicId}/question/new")
    public String newQuestionForm(@PathVariable Long topicId, Model model, RedirectAttributes redirectAttributes, Locale locale){
        try {
            Topic topic = topicService.findById(topicId);
            long nextOrderNumber = questionService.count() + 1;
            model.addAttribute("orderNumber", nextOrderNumber);
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("topicId", topicId);

            return "admin/question-form";
        } catch (TopicNotFoundException e) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{topicId}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }
    }

    @PostMapping("/topic/{topicId}/question")
    public String saveQuestion(@PathVariable Long topicId, QuestionFormDto dto, RedirectAttributes redirectAttributes, Model model, Locale locale){
        try {
            Question question = questionService.createQuestion(topicId, dto);

            String header = messageSource.getMessage(
                    "admin.question.created", new Object[]{question.getId(), question.getOrderNumber()}, locale
            );
            StringBuilder sb = new StringBuilder(header).append("\n");
            question.getTranslations().forEach(qt ->
                    sb.append(qt.getLocale().toUpperCase()).append(": ").append(qt.getText()).append("\n"));

            // ... we need to pass it through flash or common attribute
            redirectAttributes.addFlashAttribute("successMessage", sb.toString());
            return "redirect:/";
        } catch (TopicNotFoundException e) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{topicId}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        } catch (InvalidOrderNumberException ex) {
            String errorMessage = messageSource.getMessage(
                    "admin.form.orderNumber.invalid", new Object[]{ex.getMaxAllowed()}, locale
            );
            model.addAttribute("orderNumber", ex.getMaxAllowed());
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("errorMessage", errorMessage);
            return "admin/question-form";
        }
    }

    @GetMapping("/question/{id}/edit")
    public String editQuestionForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, Locale locale){
        try {
            Question question = questionService.findById(id);

            List<QuestionTranslation> translations = question.getTranslations();
            Map<String, String> translationMap = translations.stream()
                    .collect(Collectors.toMap(
                            t -> t.getLocale().toUpperCase(),
                            QuestionTranslation::getText
                    ));

            model.addAttribute("orderNumber", question.getOrderNumber());
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("translations", translationMap);
            model.addAttribute("questionId", question.getId());
            model.addAttribute("topicId", question.getTopic().getId());

            return "admin/question-form";
        } catch (QuestionNotFoundException e) {
            String errorMessage = messageSource.getMessage("admin.question.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }
    }

    @PostMapping("/question/{id}")
    public String updateQuestion(@PathVariable Long id, QuestionFormDto dto, RedirectAttributes redirectAttributes, Model model, Locale locale){
        try {
            Question question = questionService.updateQuestion(id, dto);
            List<QuestionTranslation> existingTranslations = question.getTranslations();

            String header = messageSource.getMessage(
                    "admin.question.updated", new Object[]{question.getId(), question.getOrderNumber()}, locale);

            StringBuilder sb = new StringBuilder(header).append("\n");
            existingTranslations.forEach(t ->
                    sb.append(t.getLocale().toUpperCase()).append(": ").append(t.getText()).append("\n"));

            redirectAttributes.addFlashAttribute("successMessage", sb.toString());
            return "redirect:/";
        } catch (QuestionNotFoundException e) {
            String errorMessage = messageSource.getMessage("admin.question.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        } catch (InvalidOrderNumberException ex){
            String errorMessage = messageSource.getMessage(
                    "admin.form.orderNumber.invalid", new Object[]{ex.getMaxAllowed()}, locale);
            model.addAttribute("orderNumber", ex.getMaxAllowed());
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("errorMessage", errorMessage);
            return "admin/question-form";
        }
    }

    @GetMapping("/question/{questionId}/stats")
    @ResponseBody
    public Map<String, Long> getQuestionStats(@PathVariable Long questionId) {
        long answerCount = answerService.countByQuestionId(questionId);
        long studentCount = answerService.countDistinctStudentsByQuestionId(questionId);
        return Map.of("answerCount", answerCount, "studentCount", studentCount);
    }

    @PostMapping("/question/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, RedirectAttributes redirectAttributes, Locale locale){
        try {
            Question question = questionService.deleteQuestion(id);

            String header = messageSource.getMessage(
                    "admin.question.deleted", new Object[]{question.getId(), question.getOrderNumber()}, locale);
            StringBuilder sb = new StringBuilder(header).append("\n");
            question.getTranslations().forEach(t ->
                    sb.append(t.getLocale().toUpperCase()).append(": ").append(t.getText()).append("\n"));

            redirectAttributes.addFlashAttribute("successMessage", sb.toString());
        } catch (QuestionNotFoundException e) {
            String errorMessage = messageSource.getMessage("admin.question.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/";
    }

    @GetMapping("/test-translate")
    @ResponseBody
    public String testTranslate(){
        try {
            return deepL.translate("Hello, how are you?", "EN", "DE");
        } catch (TranslationException ex) {
            return "Translation failed: " + ex.getMessage() + "\nCause: " + ex.getCause();
        }
    }

    @GetMapping("/test-translate-mymemory")
    @ResponseBody
    public String testTranslateMyMemory() {
        return myMemory.translate("Hello, how are you?", "EN", "DE");
    }

    @PostMapping("/translate")
    @ResponseBody
    public Map<String, Map<String, String>> translateForForm(@RequestBody TranslateRequestDto dto){
        return translationService.translateToAll(dto.getText(), dto.getSourceLang(), dto.getTargetLangs());
    }
}
