package com.shch.cache.mapping;
import java.beans.IntrospectionException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.shch.cache.Cache;


public abstract class AbstractCacheOperation {
	@Autowired
	public CacheMapping cacheMapping; //待XML注入实例Bean
	protected Cache cache=null;
	protected static Logger logger=Logger.getLogger(AbstractCacheOperation.class);

//	public void setCacheMapping(CacheMapping cacheMapping) {
//		this.cacheMapping = cacheMapping;
//	}
    
	public void  getCache(String key){ //根据注解的key返回缓存Cache的实例
		try {
			cache=cacheMapping.routeCache(key);
		} catch (SecurityException | IllegalArgumentException | ReflectiveOperationException
				| IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}
