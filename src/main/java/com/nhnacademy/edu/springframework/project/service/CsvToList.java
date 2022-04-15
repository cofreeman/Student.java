package com.nhnacademy.edu.springframework.project.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvToList {
    private static final Log log = LogFactory.getLog(CsvToList.class);

    public static List<List<String>> readToList(String path) {
        List<List<String>> list = new ArrayList<List<String>>();
        File csv = new File(path);
        BufferedReader br = null;

        Charset.forName("UTF-8");
        String line = "";

        try{
            br = new BufferedReader(new FileReader(csv));
            while((line=br.readLine()) != null) {
                String[] token = line.split(",");
                List<String> tempList = new ArrayList<String>(Arrays.asList(token));
                list.add(tempList);
            }
        }catch (IOException e){
            log.info(IOException.class);
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch(IOException e) {
                log.info(IOException.class);
            }
        }
        return list;
    }
}
