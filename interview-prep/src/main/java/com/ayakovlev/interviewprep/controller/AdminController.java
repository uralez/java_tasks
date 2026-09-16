package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.TopicFormDto;
import com.ayakovlev.interviewprep.entity.SupportedLanguage;
import com.ayakovlev.interviewprep.entity.Topic;
import com.ayakovlev.interviewprep.entity.TopicTranslation;
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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final TopicRepository topicRepository;
    private final TopicTranslationRepository topicTranslationRepository;
    private final MessageSource messageSource;

    @GetMapping("/test")
    public String adminTest(){
        return "index"; // просто, чтобы маршрут существовал
    }

    @PostMapping("/topic/{id}")
    public String updateTopic(@PathVariable Long id, TopicFormDto dto){
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + id));

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

    @GetMapping("/topic/{id}/edit")
    public String editTopicForm(@PathVariable Long id, Model model){
        Topic topic = topicRepository.findById(id)
                // Приводит к странице 404, а желательно вменяемую ошибку на странице
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + id));

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
}
