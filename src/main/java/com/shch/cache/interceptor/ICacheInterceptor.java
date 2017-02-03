package com.shch.cache.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

public interface ICacheInterceptor {

	public void cachePointcut(); //定义切点
	public Object checkAndProceed(ProceedingJoinPoint joinPoint) throws Throwable;//拦截处理方法
}
