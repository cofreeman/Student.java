package com.nhnacademy.edu.springframework.project.aspect;

import com.nhnacademy.edu.springframework.project.repository.CsvScores;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
public class LoggerAspect {
    private static final Log log = LogFactory.getLog(LoggerAspect.class);

    @Around("execution(* com.nhnacademy.edu.springframework.project.service.*.*(..))")
    public Object timeCheck(ProceedingJoinPoint pjp) throws Throwable{
        StopWatch stopWatch = new StopWatch(pjp.getSignature().getName());
        try{
            stopWatch.start(pjp.getSignature().getName());
            return pjp.proceed();
        } finally {
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        }
    }
    @Before("execution(* com.nhnacademy.edu.springframework.project.repository.CsvScores.findAll(..))")
    public void isScoreFileLoaded(){

        try{
            System.out.println("dsdsdsds");
            CsvScores.isFileLoaded();
        }catch (IllegalStateException e){
            throw new IllegalStateException("파일로딩이 완료되지 않았습니다");
        }
    }
    @Before("execution(* com.nhnacademy.edu.springframework.project.repository.CsvStudents.findAll(..)) || " +
            "execution(* com.nhnacademy.edu.springframework.project.repository.Students.merge(..))")
    public void isStudentFileLoaded(){
        try{
            System.out.println("ewewewew");
            CsvStudents.isFileLoaded();
        }catch (IllegalStateException e){
            throw new IllegalStateException("파일로딩이 완료되지 않았습니다");
        }
    }
}
