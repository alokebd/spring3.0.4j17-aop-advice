package com.vision.aop.aspect;

import java.util.Arrays;

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
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	//1. Before Advice 
	@Before("execution(* com.vision.aop.service.EmployeeService.addEmployee(..))")
	public void logBefore(JoinPoint joinPoint) {
		log.debug("logBefore running .....");
		log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		System.out.println("logBefore running .....");
		
		System.out.println("getSignature() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
				+ "name:"+ joinPoint.getSignature().getName()+ ", arg:"+ Arrays.toString(joinPoint.getArgs()));
		
	}

	//2. After Advice
	@After("execution(* com.vision.aop.service.EmployeeService.addEmployee(..))")
	public void logAfter(JoinPoint joinPoint) {
		log.debug("logAfter running .....");
		log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		
		System.out.println("logAfter running .....");
		System.out.println("logAfter() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
				+ "name:"+ joinPoint.getSignature().getName()+ ", arg:"+ Arrays.toString(joinPoint.getArgs()));
	}

	//3 AfterReturing Advice
	@AfterReturning(pointcut = "execution(* com.vision.aop.service.EmployeeService.deleteEmployee(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.debug("logAfterReturning running .....");
		log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		
		System.out.println("logAfterReturning running .....");
		System.out.println("logAfterReturning() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
				+ "name:"+ joinPoint.getSignature().getName()+ ", arg:"+ Arrays.toString(joinPoint.getArgs()));
		System.out.println("logAfterReturning () - result:"+result.toString());

	}

	
	//4. afterThrowing exception advice
	@AfterThrowing(pointcut = "execution(* com.vision.aop.service.EmployeeService.updateEmployee(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		log.debug("logAfterThrowing running .....");
		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), error.getCause() != null ? error.getCause() : "NULL");
		
		System.out.println("logAfterThrowing running .....");
		System.out.println("logAfterReturning() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
				+ "name:"+ joinPoint.getSignature().getName());
	}
	
	//5. Around Advice - that includes all the Advices
	@Around("execution(* com.vision.aop.service.EmployeeService.getEmployeeById(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("logAround running .....");
		System.out.println("logAround running .....");
		if (log.isDebugEnabled()) {
			log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
			
			System.out.println("On Debug mode -logAround() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
					+ "name:"+ joinPoint.getSignature().getName()+ ", arg:"+ Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			if (log.isDebugEnabled()) {
				log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
				
				System.out.println("logAround() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
						+ "name:"+ joinPoint.getSignature().getName() + ", result:"+ result);
			}
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

			System.out.println("logAround() type : "+joinPoint.getSignature().getDeclaringTypeName() + ", "
					+ "name:"+ joinPoint.getSignature().getName()+ ", arg:"+ Arrays.toString(joinPoint.getArgs()));
			throw e;
		}

	}

}
