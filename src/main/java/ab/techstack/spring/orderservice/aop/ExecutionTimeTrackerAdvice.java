package ab.techstack.spring.orderservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {

    Logger logger = LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);

    @Around("@annotation(ab.techstack.spring.orderservice.aop.customannotation.CustomExecutionTime)")
    public Object trackExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long endTimeMillis = System.currentTimeMillis();
        logger.info("Method Name"+pjp.getSignature()+" time taken: "+(endTimeMillis - startTimeMillis));
        return proceed;
    }
}
