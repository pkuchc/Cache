package com.shch.cache.interceptor;

import java.io.Serializable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.shch.cache.annotation.Cacheable;
import com.shch.cache.mapping.ICacheOperation;

@Aspect //定义切面
@Component//声明组件
public class CacheableCacheInterceptor extends AbstractCacheInterceptor implements ICacheInterceptor{
	public ICacheOperation cacheOperation;//待注入CacheOperation的Bean实例

	public void setCacheOperation(ICacheOperation cacheOperation) {
		this.cacheOperation = cacheOperation;
	}

	@Override
	@Pointcut("@annotation(com.shch.cache.annotation.Cacheable)")
	public void cachePointcut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Around("cachePointcut()")
	public Object checkAndProceed(ProceedingJoinPoint joinPoint) throws Throwable {
		super.beforeCheckAndProceed(joinPoint);//调用执行父类的方法		
        Object result=null;
        Cacheable cacheable=method.getAnnotation(Cacheable.class);
        //   String[] cacheName=cacheable.value();//缓存的名字
        String key=cacheable.key();//获取的注解的key
        //String key=cacheable.keyGenerator();//自定义缓存的生成规则
        logger.debug("拦截方法的注解Cacheable指定缓存的key："+key);
        
        /*怎么设计路由接口，还有Cachemanager和cache接口怎么对接？？
         * 还要兼容Spring框架本身通过cacheName[]找到对应的cache进行缓存
        */
        result=cacheOperation.doGet(key);
        if(result==null){//缓存中没有
        	result=joinPoint.proceed();
        	cacheOperation.doPut(key, (Serializable)result); //待考虑，result要序列化？？
        }
        //待扩展：缓存的时效——当缓存不为空时，要考虑缓存是否过期？如果过期则需要重新执行被拦截的方法体，并更新缓存
        
        
        return result;
        
	}

}
