package com.shch;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.shch.cache.annotation.CacheEvict;
import com.shch.cache.annotation.CachePut;
import com.shch.cache.annotation.Cacheable;

//@Component
//@ImportResource({"classpath:applicationContext.xml"}) //引入xml配置文件
public class CacheDemo {
	private int num=0;
	public static Logger logger=Logger.getLogger(CacheDemo.class);	
	@Cacheable(key="stock.info")
	public int testCacheable(){
		num++;
		logger.debug("执行方法体，num的值："+num);
		return num;
	}
	@CachePut(key="stock.info")
	public int testCachePut(){
		num++;
		logger.debug("执行方法体，num的值："+num);
		return num;
	}
	@CacheEvict(key="stock.info")
	public int testCacheEvict(){
		num++;
		logger.debug("执行方法体，num的值："+num);
		return num;
	}
	@Cacheable(key="clear.info")
	public int testCacheable1(){
		num++;
		logger.debug("执行方法体，num的值："+num);
		return num;
	}

}
