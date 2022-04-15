package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.repository.*;
import com.nhnacademy.edu.springframework.project.singleton.JavaConfigMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {JavaConfigMain.class, LoggerAspect.class})
class GradeQueryServiceTest {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Mainconfiguration.class);

    Students students = context.getBean("csvStudents",CsvStudents.class);
    Scores scores = context.getBean("csvScores",CsvScores.class);
    DefaultGradeQueryService defaultGradeQueryService = context.getBean("defaultGradeQueryService",DefaultGradeQueryService.class);


    @BeforeEach
    void setUp() throws IOException {
        scores.load();
        students.load();
    }
    @Test
    void getScoreByStudentName() {

        students.merge(scores.findAll());
        students.findAll().stream().forEach(s ->s.getScore().equals(null));
        List<Student> list = students.findAll().stream().collect(Collectors.toList());
        List<Score> list1 = scores.findAll().stream().collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {
            assertThat(defaultGradeQueryService.getScoreByStudentName(list.get(i).getName()).equals(list1.get(i).getScore()));
        }
    }

    @Test
    void getScoreByStudentSeq() {
        students.merge(scores.findAll());
        students.findAll().stream().forEach(s ->s.getScore().equals(null));
        List<Student> list = students.findAll().stream().collect(Collectors.toList());
        List<Score> list1 = scores.findAll().stream().collect(Collectors.toList());

        for (int i = 1; i < list.size(); i++) {
            assertThat(defaultGradeQueryService.getScoreByStudentSeq(list.get(i).getSeq()).equals(list1.get(i).getScore()));
        }
    }
}