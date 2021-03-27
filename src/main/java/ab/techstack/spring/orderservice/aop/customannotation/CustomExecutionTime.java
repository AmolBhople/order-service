package ab.techstack.spring.orderservice.aop.customannotation;

import org.aspectj.lang.annotation.RequiredTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomExecutionTime {
}
