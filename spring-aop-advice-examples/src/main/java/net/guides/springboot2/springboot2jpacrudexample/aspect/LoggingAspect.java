package net.guides.springboot2.springboot2jpacrudexample.aspect;

import java.util.Arrays;

import net.guides.springboot2.springboot2jpacrudexample.neo4j.repository.ThrowableGraphLoader;
import net.guides.springboot2.springboot2jpacrudexample.utils.StackTraceUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution.
 * 
 * @author Ramesh Fadatare
 *
 */
@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ThrowableGraphLoader throwableGraphLoader;

	/**
	 * Run before the method execution.
	 * @param joinPoint
	 */
	@Before("execution(* net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService.addEmployee(..))")
	public void logBefore(JoinPoint joinPoint) {
		log.debug("logBefore running .....");
		String trace = prepareAndSendStackTrace();
		Throwable traceThrowable = new Throwable(trace);
		StackTraceElement[] stackTrace = traceThrowable.getStackTrace();
		throwableGraphLoader.loadThrowableData(stackTrace);
//		for (StackTraceElement element : stackTrace) {
//			log.debug("element.getClassName()"+ element.getClassName());
//			log.debug("element.getMethodName "+ element.getMethodName());
//			log.debug("element.getFileName() "+element.getFileName());
//			log.debug("element.getLineNumber()"+element.getLineNumber());
//		}

		log.debug("TRACE:::\n"+trace);
		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

		
	}

	/**
	 * Run after the method returned a result.
	 * @param joinPoint
	 */
	@After("execution(* net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService.addEmployee(..))")
	public void logAfter(JoinPoint joinPoint) {
		log.debug("logAfter running .....");
		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
	}

	/**
	 * Run after the method returned a result, intercept the returned result as well.
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService.deleteEmployee(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.debug("logAfterReturning running .....");
		log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

	}

	/**
	 * Run around the method execution.
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService.getEmployeeById(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("logAround running .....");
		if (log.isDebugEnabled()) {
			log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			if (log.isDebugEnabled()) {
				log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
			}
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			throw e;
		}

	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param e         exception
	 */

	@AfterThrowing(pointcut = "execution(* net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService.updateEmployee(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		log.debug("logAfterThrowing running .....");
		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), error.getCause() != null ? error.getCause() : "NULL");
	}

	public String prepareAndSendStackTrace(){
		return StackTraceUtils.compactStackTrace(new Throwable());
	}
}
