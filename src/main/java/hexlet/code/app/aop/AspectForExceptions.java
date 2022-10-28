package hexlet.code.app.aop;

import hexlet.code.app.exception.NotAuthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectForExceptions {
    @Around("execution(public String getJWT(..))")
    public Object getJWTAdvise(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            throw new NotAuthorizedException();
        }
        return result;
    }
}
