package com.nhnacademy.edu.springframework.project.singleton;

import com.nhnacademy.edu.springframework.project.aspect.LoggerAspect;
import com.nhnacademy.edu.springframework.project.config.Mainconfiguration;
import com.nhnacademy.edu.springframework.project.service.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Collection;

public class JavaConfigMain {

    private static final Log log = LogFactory.getLog(JavaConfigMain.class);

    public static void main(String[] args) throws IOException {


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Mainconfiguration.class);

        DataLoadService dataLoadService = context.getBean("csvDataLoadService", CsvDataLoadService.class);
        dataLoadService.loadAndMerge();

        DefaultStudentService studentService = context.getBean("defaultStudentService", DefaultStudentService.class);
        Collection<Student> passedStudents = studentService.getPassedStudents();
//        log.trace(passedStudents);

        Collection<Student> orderedStudents = studentService.getStudentsOrderByScore();
//        log.trace(orderedStudents);


    }
}
