package com.shch.cache.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public abstract class AbstractCacheInterceptor {
	protected static Logger logger=Logger.getLogger(AbstractCacheInterceptor.class);
	protected Method method;
	

	public void beforeCheckAndProceed(ProceedingJoinPoint joinPoint){
		String targetName=joinPoint.getTarget().getClass().getName();//拦截的方法名所在的类名
		String methodName=joinPoint.getSignature().getName(); //拦截的方法名		
		if(logger.isDebugEnabled()){ //logger是debug层级时才输出信息
			logger.debug("Aop拦截的对象是：class--"+targetName+" method--"+methodName);
		}
		Object[] arguments=joinPoint.getArgs();//拦截方法的 参数
        method=((MethodSignature)joinPoint.getSignature()).getMethod();//拦截方法的句柄对象
	}



}
