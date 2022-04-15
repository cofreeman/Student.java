package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.CsvScores;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import com.nhnacademy.edu.springframework.project.repository.Scores;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class CsvDataLoadService implements DataLoadService {
    private final Scores scores;
    private final Students students;
    @Autowired
    public CsvDataLoadService(Scores scores, Students students) {
        this.scores = scores;
        this.students = students;
    }

    @Override
    public void loadAndMerge() throws IOException {
        scores.load();
        students.load();
        students.merge(scores.findAll());
    }
}
