package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.singleton.JavaConfigMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringJUnitConfig(classes = {JavaConfigMain.class, LoggerAspect.class})
class ScoresFindAllTest {
    AnnotationConfigApplicationContext context;
    Scores scores;

    @BeforeEach()
    void setUp(){
        context = new AnnotationConfigApplicationContext(Mainconfiguration.class);
        scores = context.getBean("csvScores",CsvScores.class);
    }
    @DisplayName("모두 로드되었는지")
    @Test
    void findAllTest() throws IOException {

        scores.load();
        assertThat(scores.findAll().size()).isEqualTo(15);
    }
}