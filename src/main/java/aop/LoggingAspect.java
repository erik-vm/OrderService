package aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

//    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {

        Logger logger = LogManager.getLogger(joinPoint.getTarget().getClass());

        logger.info("method name: " + joinPoint.getSignature().getName());
        logger.debug("method arguments : " + Arrays.toString(joinPoint.getArgs()));
    }
}