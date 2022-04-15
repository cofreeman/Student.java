package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.service.Student;
import com.nhnacademy.edu.springframework.project.singleton.JavaConfigMain;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringJUnitConfig(classes = {JavaConfigMain.class, LoggerAspect.class})
class StudentsTest {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Mainconfiguration.class);

    Students students = context.getBean("csvStudents",CsvStudents.class);
    Scores scores = context.getBean("csvScores",CsvScores.class);

    @Test
    void load() throws IOException {
        assertThatThrownBy(() -> students.findAll())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("파일로딩이 완료되지 않았습니다");
        students.load();
        students.findAll();
    }

    @Test
    void findAll() throws IOException {
        students.load();
        assertThat(students.findAll().size()).isEqualTo(15);

    }

    @Test
    void merge() throws IOException {
        students.load();
        scores.load();
        students.merge(scores.findAll());
        students.findAll().stream().forEach(s ->s.getScore().equals(null));
        List<Student> list = students.findAll().stream().collect(Collectors.toList());
        List<Score> list1 = scores.findAll().stream().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            assertThat(list.get(i).getScore().equals(list1.get(i))).isTrue();
        }
    }
}