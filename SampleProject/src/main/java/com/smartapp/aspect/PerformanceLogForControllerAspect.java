package com.smartapp.aspect;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

/**This aspect is used for performance logging. The order of this is set to top i.e at -2. 
 * The order of aspect for exception handling is -1. The advice in this aspect will be called everytime 
 * before and after execution of any method from classes annotated with @Service and @Repository annotation. 
 * Using JAMon monitor to monitor the methods.Monitor can provide values like start time, end time, access time etc */
@Aspect
@Order(-2)
public class PerformanceLogForControllerAspect {
		
	private static final String  START_END ="startEnd";
	private static final String  PERFORMANCE ="Performance";
	private static final String  PERFORMANCE_LOG_VALUE ="Logging";
	
	private static final String  METHOD_NAME ="methodName";
	private static final String  DAYS_SINCE_1970 ="daysSince1970";
	private static final String  MILLIS_FOR_DAY ="millisForDay";
	
	public PerformanceLogForControllerAspect() {
	}
	
	/** This advice will be called everytime 
	 * before and after execution of any method from classes annotated wiht @Service and @Repository annotation 
	 * Before any Service/Repository method is invoked, advice starts the monitor. After the method is executed, 
	 * this advice will stop the monitor and will log the monitor values   */
    @Around("@within(org.springframework.stereotype.Controller)")
    public Object performanceLogging(ProceedingJoinPoint joinPoint) throws Throwable{
    		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    		String methodName = createMethodName(joinPoint);
    		// check if logger level is TRACE. This is a mandatory condition for performance logging
			if (logger.isTraceEnabled()) {
				MDC.put(START_END , "S");
				mdcAdd(methodName);
				logger.trace("Performance tracing start");
				mdcRemove();
			}
			try {
				// this will execute the underlying service or repository method
				return joinPoint.proceed();
			}
			finally {
				if (logger.isTraceEnabled()) {
					// stop the monitor.
					MDC.put(START_END , "E");
					mdcAdd(methodName);
					logger.trace("Performance tracing end");
					mdcRemove();
				}
			}
	        
	}
    
    /** return the name of the service/repository method along with signature to be monitored*/
	private String createMethodName(ProceedingJoinPoint joinPoint) {
		String str = joinPoint.getSignature().toString();
		String methodName = "";
		int lastDotIndex = str.lastIndexOf(".");
		if(lastDotIndex != -1){
			methodName = str.substring(lastDotIndex+1);
		}
		return methodName;
	}
	
	private void mdcRemove(){
		MDC.remove(START_END);
		MDC.remove(PERFORMANCE);
		MDC.remove(METHOD_NAME);
		MDC.remove(DAYS_SINCE_1970);
		MDC.remove(MILLIS_FOR_DAY);
	}
	
	private void mdcAdd(String methodName){
		MDC.put(METHOD_NAME, methodName);
		MDC.put(PERFORMANCE, PERFORMANCE_LOG_VALUE);		
		MDC.put(PERFORMANCE, "Logging");
		MDC.put(DAYS_SINCE_1970, String.valueOf(Calendar.getInstance().getTimeInMillis()));
		MDC.put(MILLIS_FOR_DAY, String.valueOf(Calendar.getInstance().getTimeInMillis()));
	}
}
