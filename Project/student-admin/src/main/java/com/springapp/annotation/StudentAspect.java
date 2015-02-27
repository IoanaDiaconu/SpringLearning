package com.springapp.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;

/**
 * Created by ioana.diaconu on 2/26/2015.
 */
@Aspect
public class StudentAspect {
    @Pointcut(value="execution(public * *(..))")
    public void anyPublicMethod() { }

    @Around("anyPublicMethod() && @annotation(studentInfo)")
    public Object process(ProceedingJoinPoint jointPoint, StudentInfo studentInfo) throws Throwable {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("First name",studentInfo.firstName());
        jsonObject.put("Last name",studentInfo.lastName());
        jsonObject.put("Age",studentInfo.age());

        return jsonObject;
    }
}
