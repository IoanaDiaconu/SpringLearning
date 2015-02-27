package com.spring.app.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Date;

/**
 * Created by ioana.diaconu on 2/23/2015.
 */
@Aspect
public class NotifyAspect {
    @Autowired
    private SimpMessagingTemplate template;

    private static final String WEBSOCKET_TOPIC = "/topic/notify";

    @Pointcut("@annotation(com.spring.app.aop.NotifyClients)")
    public void notifyPointcut() {}

    @Pointcut("execution(* com.spring.app.controller.*.*(..))")
    public void methodPointcut() {}

    @After("methodPointcut() && notifyPointcut()")
    public void notifyClients() throws Throwable {
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
}
