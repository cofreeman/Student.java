package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.Scores;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class DefaultStudentService implements StudentService {
    private final Scores scores;
    private final Students students;
    @Autowired
    public DefaultStudentService(Scores scores, Students students) {
        this.scores = scores;
        this.students = students;
    }

    @Override
    public Collection<Student> getPassedStudents() {
        Scores score = scores;
        Students studentRepository = students;
        studentRepository.merge(score.findAll());
        // TODO 1 : pass한 학생만 반환하도록 수정하세요.

        return studentRepository.findAll().stream().filter(s-> !s.getScore().isFail()).collect(Collectors.toList());
    }

    @Override
    public Collection<Student> getStudentsOrderByScore() {
        Scores score = scores;
        Students studentRepository = students;
        studentRepository.merge(score.findAll());
        // TODO 4 : 성적 순으로 학생 정보를 반환합니다.
        return studentRepository.findAll().stream().sorted(Comparator.comparing((Student s)-> s.getScore().getScore())).collect(Collectors.toList());
    }

}
