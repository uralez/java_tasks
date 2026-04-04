package com.ayakovlev.interviewprep.controller;

import com.ayakovlev.interviewprep.dto.GradePointDto;
import com.ayakovlev.interviewprep.dto.QuestionDto;
import com.ayakovlev.interviewprep.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(HomeController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private TopicService topicService;
    @MockBean
    private EvaluatorService evaluatorService ;
    @MockBean
    private QuestionService questionService ;
    @MockBean
    private AnswerService answerService;

    @Test
    @WithMockUser(username = "Andrew", roles = "USER")
    void loginPage_returnsLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
//                .andExpect(view().name("login"))
        ;
    }

    @Test
    @WithMockUser(username = "Andrew", roles = "USER")
    void registerPage_returnsRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser(username = "Andrew", roles = "USER")
    void getQuestions_returnsJsonList() throws Exception {
        when(questionService.findByTopicWithTranslation(any(), anyString()))
                .thenReturn(List.of(new QuestionDto(1L, "What is equals()?")));

        mockMvc.perform(get("/questions").param("topicId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].text").value("What is equals()?"));
    }

    @Test
    @WithMockUser(username = "Andrew", roles = "USER")
    void getGrades_returnsJsonList() throws Exception {
        when(answerService.findGradesByQuestion(anyLong(), any()))
                .thenReturn(List.of(new GradePointDto(1L, LocalDate.of(2025, 12, 15), new BigDecimal("4.5"), "Abc")));

        mockMvc.perform(get("/grades").param("questionId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].grade").value(4.5));

    }
}
