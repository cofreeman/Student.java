package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.CsvToList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** TODO 2 :
 * load 를 제외한 메소드 실행시
 * 데이터 로드가 완료되지 않으면 IllegalStateException 이 발생해야 한다.
 **/
@Service
public class CsvScores implements Scores {
    static boolean isFileLoaded = false;


    private final List<Score> scores = new ArrayList<>();
    public static void isFileLoaded(){
        if(!isFileLoaded){
            throw new IllegalStateException("파일로딩이 완료되지 않았습니다");
        }


    }

    // TODO 5 : score.csv 파일에서 데이터를 읽어 scores 에 추가하는 로직을 구현하세요.
    @Override
    public void load() throws IOException {
        List<List<String>> list = CsvToList.readToList("src/main/resources/data/score.csv");
        for (int i = 0; i < list.size(); i++) {
            scores.add(new Score(Integer.valueOf(list.get(i).get(0)),Integer.valueOf(list.get(i).get(1))));
        }
        isFileLoaded = true;
    }

    @Override
    public List<Score> findAll() {
        return this.scores;
    }
}
