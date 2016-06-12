package com.smartapp.aspect;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

/** This aspect is used for exception handling.The order of this aspect is one level below the top
 * i.e at -1. Aspect for performance logging is at the top.The advice in this aspect will be called after a Runtime Exception is thrown.
 * */
 @Aspect
@Order(-1)
public class ExceptionLoggerAspect {
 
	/** This advice will be called if a Runtime Exception is thrown from classed annotated with @Service or @Repository annotation.
	 *  This advice creates a string buffer with the parameter info,and puts it into MDC.The key for parameter info in MDC is paramInfoForException. 
	 *  If the exception is generated from dao, this method will be called twice. First time from persistence layer and when the exception 
	 *  is traversed back to service layer this method will be called for second time. 
	 *  Param Info will contain Class name with package from exception was generated, name of the method from where exception was generated, arguments of the method  
	 *  Below is the sample format of paramInfoForException  
	 *  ClassName : com.deere.dsfj.ordercomponent.dao.jdbc.CustomerDaoImpl MethodName : List com.deere.dsfj.ordercomponent.dao.CustomerDao.findByName(String) Param[0] : Amit 
	 */
    @AfterThrowing(value="@within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)", throwing="rt")
    public void populateParameterInfoInMDC(JoinPoint joinPoint, RuntimeException rt) throws Throwable{
    	StringBuffer strBuffer = null;
    	
    	//check whether paramInfoForException is present in MDC
		String paramInfoValue = (String) MDC.get("paramInfoForException");
    	if(paramInfoValue != null){
    		strBuffer = new StringBuffer(paramInfoValue);
    	}else{
    		strBuffer = new StringBuffer();
    	}     
    	
    	// put the param info using string buffer
    	
    	//get the class name with package
    	String classNameWithPackage = joinPoint.getTarget().getClass().getName();
    	// WHOLE PACKAGE NAME
    	// String className = joinPoint.getTarget().getClass().getName();
    	
    	//get the signature of the method from where exception was generated
    	String methodName = joinPoint.getSignature().toString();
    	strBuffer.append("\r\n");
    	
    	// add the class name and method name to string buffer
    	strBuffer.append("ClassName : " );
    	strBuffer.append(classNameWithPackage);
    	strBuffer.append("-------");
    	strBuffer.append("\r\n");
    	strBuffer.append("MethodName : " );
    	strBuffer.append(methodName);
    	strBuffer.append("-------");
    	strBuffer.append("\r\n");
    	
    	// get the argument values. 
    	Object[] arguments = joinPoint.getArgs();

    	// add the argument values to the string buffer
    	int loopCount = 0;
    	for(Object obj : arguments){
        	strBuffer.append("Param[" + loopCount + "] : ");
        	if(obj != null){
        		strBuffer.append(obj.toString());
        	}else{
        		strBuffer.append("null");
        	}
        	strBuffer.append("\r\n");
    		loopCount++;
    	}
    	strBuffer.append("Exception StackTrace : " );
    	strBuffer.append(ExceptionUtils.getStackTrace(rt));
    	strBuffer.append("-------");
    	strBuffer.append("\r\n");
    	strBuffer.append("***********");
    	
    	// add the param info in MDC
    	MDC.put("paramInfoForException", strBuffer.toString());
    }
}


