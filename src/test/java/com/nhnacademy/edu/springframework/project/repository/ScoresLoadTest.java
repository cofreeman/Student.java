package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.singleton.JavaConfigMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringJUnitConfig(classes = {JavaConfigMain.class, LoggerAspect.class})
class ScoresLoadTest {
    AnnotationConfigApplicationContext context;
    Scores scores;

    @BeforeEach()
    void setUp(){
        context = new AnnotationConfigApplicationContext(Mainconfiguration.class);
        scores = context.getBean("csvScores",CsvScores.class);
    }


    @DisplayName("load 후 findall 하면 오류 x")
    @Test
    void loadTest(){

        assertThatThrownBy(()->scores.findAll())
                .isInstanceOf(IllegalStateException.class);

    }

}