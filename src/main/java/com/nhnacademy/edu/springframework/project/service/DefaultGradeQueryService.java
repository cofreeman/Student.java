package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class DefaultGradeQueryService implements GradeQueryService {

    private final Scores scores;
    private final Students students;
    @Autowired
    public DefaultGradeQueryService(Scores scores, Students students) {
        this.scores = scores;
        this.students = students;
    }

    @Override
    public List<Score> getScoreByStudentName(String name) {
        List<Score> list3 = new ArrayList<>();
        // TODO 5: 학생 이름으로 점수를 반환합니다. 동명이인 고려합니다.
        List<Score> list = scores.findAll().stream().collect(Collectors.toList());
        List<Student> student = students.findAll().stream().filter(s-> Boolean.parseBoolean(s.getName())).collect(Collectors.toList());
        for (int i = 0; i < student.size(); i++) {
            list3.add(list.get(student.get(i).getSeq()));
        }
        return list3;
    }

    @Override
    public Score getScoreByStudentSeq(int seq) {

        List<Student> student = students.findAll().stream().filter(s-> s.getSeq() == seq).collect(Collectors.toList());
        return scores.findAll().get(student.get(0).getSeq() -1 );

    }
}
