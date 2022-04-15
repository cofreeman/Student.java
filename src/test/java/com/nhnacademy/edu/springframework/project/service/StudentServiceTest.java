package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.repository.CsvScores;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import com.nhnacademy.edu.springframework.project.repository.Scores;
import com.nhnacademy.edu.springframework.project.repository.Students;
import com.nhnacademy.edu.springframework.project.singleton.JavaConfigMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {JavaConfigMain.class, LoggerAspect.class})
class StudentServiceTest {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Mainconfiguration.class);

    Students students = context.getBean("csvStudents",CsvStudents.class);
    Scores scores = context.getBean("csvScores",CsvScores.class);
    DefaultStudentService defaultStudentService = context.getBean("defaultStudentService",DefaultStudentService.class);

    @BeforeEach
    void setUp() throws IOException {
        scores.load();
        students.load();
    }
    @Test
    void getPassedStudents() {
        students.findAll();
        List<Student> list = new ArrayList<>(defaultStudentService.getPassedStudents());
        for (int i = 0; i < list.size(); i++) {
            assertThat(list.get(i).getScore().getScore() >= 60).isTrue();
        }
    }

    @Test
    void getStudentsOrderByScore() {
        List<Student> list = new ArrayList<>(defaultStudentService.getStudentsOrderByScore());
        for (int i = 0; i < list.size() - 1; i++) {
            assertThat(list.get(i).getScore().getScore() <= list.get(i).getScore().getScore()).isTrue();
        }
    }
}