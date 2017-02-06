package com.shch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootApplication
//@EnableConfigurationProperties(CacheSettings.class)
public class ShchCacheApplication {

	private static Logger logger=Logger.getLogger(ShchCacheApplication.class);
	
	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(ShchCacheApplication.class, args);
		
		//ApplicationContext ctx=SpringApplication.run(ShchCacheApplication.class, args);
		//ParaCacheDemo demo=(ParaCacheDemo) ctx.getBean("demo");
		//demo.printInfo();
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");

	    //ApplicationContext ctx=SpringApplication.run(ShchCacheApplication.class, args);
		CacheDemo cacheDemo=(CacheDemo) ctx.getBean("cacheDemo");
		int result=0;
		for(int i=0;i<3;i++){
			Thread.sleep(1000);
			result=cacheDemo.testCacheable();
			logger.info("第"+(i+1)+"次执行testCacheable()方法的结果为："+result);	
			Thread.sleep(2000);
		}
		for(int i=0;i<3;i++){
			result=cacheDemo.testCachePut();
			logger.info("第"+(i+1)+"次执行testCachePut()方法的结果为："+result);			
		}
		logger.info("执行testCacheEvict()方法");
		cacheDemo.testCacheEvict();
		result=cacheDemo.testCacheable();
		logger.info("再次执行testCacheable()方法的结果为："+result);
		
		result=cacheDemo.testCacheable1();
		logger.info("执行testCacheable1()方法的结果为："+result);

	}
}
