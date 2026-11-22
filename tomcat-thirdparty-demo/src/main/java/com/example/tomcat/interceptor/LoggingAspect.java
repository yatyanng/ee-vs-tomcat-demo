package com.example.tomcat.interceptor;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger LOG = Logger.getLogger(LoggingAspect.class.getName());
	
    @Around("execution(* com.example.tomcat.service.*.*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    	LOG.info("[Spring AOP Interceptor] Calling: " + joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}