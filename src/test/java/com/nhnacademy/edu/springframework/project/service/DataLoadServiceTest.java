package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.repository.*;
import com.nhnacademy.edu.springframework.project.singleton.JavaConfigMain;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {JavaConfigMain.class, LoggerAspect.class})
class DataLoadServiceTest {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Mainconfiguration.class);

    Students students = context.getBean("csvStudents",CsvStudents.class);
    Scores scores = context.getBean("csvScores",CsvScores.class);
    CsvDataLoadService csvDataLoadService = context.getBean("csvDataLoadService",CsvDataLoadService.class);
    @Test
    void loadAndMerge() throws IOException {
        assertThatThrownBy(()->scores.findAll())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("파일로딩이 완료되지 않았습니다");
        assertThatThrownBy(() -> students.findAll())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("파일로딩이 완료되지 않았습니다");
        csvDataLoadService.loadAndMerge();
        students.findAll().stream().forEach(s ->s.getScore().equals(null));
        List<Student> list = students.findAll().stream().collect(Collectors.toList());
        List<Score> list1 = scores.findAll().stream().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            assertThat(list.get(i).getScore().equals(list1.get(i))).isTrue();
        }
    }
}