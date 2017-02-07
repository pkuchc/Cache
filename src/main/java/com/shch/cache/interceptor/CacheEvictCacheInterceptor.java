package com.shch.cache.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shch.cache.annotation.CacheEvict;
import com.shch.cache.annotation.Cacheable;
import com.shch.cache.mapping.ICacheOperation;

@Aspect //定义切面
@Component//声明组件
public class CacheEvictCacheInterceptor extends AbstractCacheInterceptor implements ICacheInterceptor{
	@Autowired
	public ICacheOperation cacheOperation;//待注入CacheOperation的Bean实例

	public void setCacheOperation(ICacheOperation cacheOperation) {
		this.cacheOperation = cacheOperation;
	}

	@Override
	@Pointcut("@annotation(com.shch.cache.annotation.CacheEvict)")
	public void cachePointcut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Around("cachePointcut()")
	public Object checkAndProceed(ProceedingJoinPoint joinPoint) throws Throwable {
		super.beforeCheckAndProceed(joinPoint);//调用执行父类的方法		
        Object result=null;
        CacheEvict cacheevict=method.getAnnotation(CacheEvict.class);
        //   String[] cacheName=cacheable.value();//缓存的名字
        String key=cacheevict.key();//获取的注解的key
        //String key=cacheable.keyGenerator();//自定义缓存的生成规则
        logger.debug("拦截方法的注解CacheEvict指定缓存的key："+key);                        
        result=joinPoint.proceed(); //在拦截方法执行后，再执行缓存删除动作
        cacheOperation.doEvict(key);
        return result;
        
	}

}
