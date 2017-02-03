package com.shch.cache.interceptor;

import java.io.Serializable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.shch.cache.annotation.CachePut;
import com.shch.cache.mapping.ICacheOperation;

@Aspect //定义切面
@Component//声明组件
public class CachePutCacheInterceptor extends AbstractCacheInterceptor implements ICacheInterceptor{
	public ICacheOperation cacheOperation;//待注入CacheOperation的Bean实例

	public void setCacheOperation(ICacheOperation cacheOperation) {
		this.cacheOperation = cacheOperation;
	}

	@Override
	@Pointcut("@annotation(com.shch.cache.annotation.CachePut)")
	public void cachePointcut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Around("cachePointcut()")
	public Object checkAndProceed(ProceedingJoinPoint joinPoint) throws Throwable {
		super.beforeCheckAndProceed(joinPoint);//调用执行父类的方法		
        Object result=null;
        CachePut cacheput=method.getAnnotation(CachePut.class);
        //   String[] cacheName=cacheable.value();//缓存的名字
        String key=cacheput.key();//获取的注解的key
        String cron=cacheput.scheduleUpdateCron(); //定时任务表达式
        //String key=cacheable.keyGenerator();//自定义缓存的生成规则
        logger.debug("拦截方法的注解CachePut指定缓存的key："+key);                
        result=joinPoint.proceed();//始终执行被拦截的方法，但是怎么保证执行完了，更新缓存？？
        cacheOperation.doPut(key,(Serializable)result); //将执行结果放到缓存中，待考虑，result要序列化？？
        if(cron!=null){
        	//加入定时任务支持逻辑
        	
        	
        }
        return result;
        
	}

}
