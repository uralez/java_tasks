package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.TopicFormDto;
import com.ayakovlev.interviewprep.entity.SupportedLanguage;
import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.entity.TopicTranslation;
import com.ayakovlev.interviewprep.repository.QuestionRepository;
import com.ayakovlev.interviewprep.repository.TopicRepository;
import com.ayakovlev.interviewprep.repository.TopicTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final TopicRepository topicRepository;
    private final TopicTranslationRepository topicTranslationRepository;
    private final QuestionRepository questionRepository;
    private final MessageSource messageSource;

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
     * @param locale            the current user's locale, used to localize the error message
     * @return                  redirect to the home page
     */
    @PostMapping("/topic/{id}")
    public String updateTopic(@PathVariable Long id, TopicFormDto dto, RedirectAttributes redirectAttributes, Locale locale){
        Optional<Topic> topicOpt = topicRepository.findById(id);
        if (topicOpt.isEmpty()) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }
        Topic topic = topicOpt.get();

        topic.setOrderNumber(dto.getOrderNumber());
        topicRepository.save(topic);

        List<TopicTranslation> existingTranslations = topic.getTranslations();
        existingTranslations.forEach(t -> {
            String newText = dto.getTranslations().get(t.getLocale().toUpperCase());
            if (newText != null) {
                t.setName(newText);
            }
        });
        topicTranslationRepository.saveAll(existingTranslations);

        String header = messageSource.getMessage(
                "admin.topic.updated", new Object[]{topic.getId(), topic.getOrderNumber()}, locale);
        StringBuilder sb = new StringBuilder(header).append("\n");
        existingTranslations.forEach(t -> sb.append(t.getLocale().toUpperCase()).append(": ").append(t.getName()).append("\n"));

        redirectAttributes.addFlashAttribute("successMessage", sb.toString());
        return "redirect:/";
    }

    @PostMapping("/topic")
    public String saveTopic(TopicFormDto dto, Model model, Locale locale){
        // validation
        long maxAllowed = topicRepository.count() + 1;
        if(dto.getOrderNumber() == null || dto.getOrderNumber() > maxAllowed || dto.getOrderNumber() < 1){
            String errorMessage = messageSource.getMessage(
                    "admin.form.orderNumber.invalid", new Object[]{maxAllowed}, locale);
            model.addAttribute("orderNumber", maxAllowed);
            model.addAttribute("languages", Arrays.stream(SupportedLanguage.values()).map(Enum::name).toList());
            model.addAttribute("errorMessage", errorMessage);
            return "admin/topic-form";
        }

        // saving
        Topic topic = new Topic();
        topic.setOrderNumber(dto.getOrderNumber());
        topicRepository.save(topic);

        List<TopicTranslation> translations = dto.getTranslations().entrySet().stream()
                .map(entry -> {
                    TopicTranslation translation = new TopicTranslation();
                    translation.setTopic(topic);
                    translation.setLocale(entry.getKey().toLowerCase());
                    translation.setName(entry.getValue());
                    return translation;
                })
                .toList();

        topicTranslationRepository.saveAll(translations);

        return "redirect:/";
    }

    @GetMapping("/topic/new")
    public String newTopicForm(Model model){
        int nextOrderNumber = (int)topicRepository.count() + 1;
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
        Optional<Topic> topicOpt = topicRepository.findById(id);
        if (topicOpt.isEmpty()) {
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }
        Topic topic = topicOpt.get();

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
    }

    @PostMapping("/topic/{id}/delete")
    public String deleteTopic(@PathVariable Long id, RedirectAttributes redirectAttributes, Locale locale){
        Optional<Topic> topicOpt = topicRepository.findById(id);
        if (topicOpt.isEmpty()){
            String errorMessage = messageSource.getMessage("admin.topic.notFound", new Object[]{id}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }

        Topic topic = topicOpt.get();
        long questionCount = questionRepository.countByTopicId(id);
        if (questionCount > 0){
            String errorMessage = messageSource.getMessage("admin.topic.hasQuestions", new Object[]{questionCount}, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/";
        }

        topicTranslationRepository.deleteAll(topic.getTranslations());
        topicRepository.delete(topic);

        return "redirect:/";
    }
}
