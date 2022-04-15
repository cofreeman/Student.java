package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.CsvToList;
import com.nhnacademy.edu.springframework.project.service.Student;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * TODO 3 :
 * load 를 제외한 메소드 실행시
 * 데이터 로드가 완료되지 않으면 IllegalStateException 이 발생해야 한다.
 **/

/**
 * TODO 7 :
 * singleton 클래스를 만드세요.
 */
@Service
public class CsvStudents implements Students {

    static boolean isFileLoaded = false;


    private final Map<Integer, Student> students = new HashMap<>();


    public static void isFileLoaded() {
        if (!isFileLoaded) {
            throw new IllegalStateException("파일로딩이 완료되지 않았습니다");
        }
    }

    @Override
    public void load() throws IOException {
        List<List<String>> list = CsvToList.readToList("src/main/resources/data/student.csv");
        for (int i = 0; i < list.size(); i++) {
            students.put(Integer.valueOf(list.get(i).get(0)), new Student(i, list.get(i).get(1)));
        }
        isFileLoaded = true;
    }

    @Override
    public Collection<Student> findAll() {
        return this.students.values();
    }

    @Override
    public void merge(Collection<Score> scores) {
        List<Score> scoreList = scores.stream().collect(Collectors.toList());
        for (int j = 1; j <= students.size(); j++) {
            students.get(j).setScore(scoreList.get(j - 1));
        }
    }
}

