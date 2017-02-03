package com.shch.cache.update;

import java.util.HashMap;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.shch.CacheScheduleUpdate1;
import com.shch.ShchCacheApplication;
import com.shch.cache.mapping.config.PropertyConfigurer;
import com.shch.cache.support.StringSplitter;

public class CacheScheduleUpdateJob1 {
	@Autowired
	public PropertyConfigurer propertyConfigurer;
//	@Autowired
//	public CacheUpdateThread cacheUpdateThread;	
	private static ApplicationContext applicationContext=ShchCacheApplication.ctx;
	@Autowired
	public StringSplitter stringSplitter;
	private ThreadPoolTaskScheduler tpts=new ThreadPoolTaskScheduler();
//	@Autowired
//	private ThreadPoolTaskScheduler tpts;
	@Autowired //注入集合(多个实例),根据类型注入
	public Set<ICacheScheduleUpdate> cacheScheduleUpdates;//待注入多个实例对象
	
	public HashMap<String,ICacheScheduleUpdate> cacheScheduleUpdateMap=new HashMap<String,ICacheScheduleUpdate>();
	
	private static Logger logger=Logger.getLogger(CacheScheduleUpdateJob1.class);
	
	//@PostConstruct //Spring中容器初始化方法，等同于xml配置文件中的init-method
    public void updateCache(){
    	/*1.从property配置文件读取cron和key信息
		 * 2.新建定时任务管理对象：new ThreadPoolTaskScheduler
		 * 3.在新建线程中执行cacheOperation.doput(key);代替@CachePut注解功能
		*/
		//收集实现接口的实体类
		for(ICacheScheduleUpdate cacheScheduleUpdate:cacheScheduleUpdates){
			cacheScheduleUpdateMap.put((String) cacheScheduleUpdate.GetKey(), cacheScheduleUpdate);
			logger.debug("ICacheScheduleUpdate实例的名字："+cacheScheduleUpdate.getClass());
		}
		
		String multipleCron= (String) propertyConfigurer.getPropertyValue("cron"); //property文件中的key待设计？？？？
		Object multipleKey=propertyConfigurer.getPropertyValue("key");
		logger.info("从配置文件读取信息：cron表达式"+multipleCron+"\t键key-"+multipleKey);
		
		String[] crons=stringSplitter.split(multipleCron, ",");
		String[] keys=stringSplitter.split(multipleKey.toString(), ",");
		String cron=null;
		String key=null;
		
		//必须初始化才能使用
		tpts.initialize();

		if(crons.length==keys.length){
			logger.debug("crons的长度："+crons.length);
			for(int i=0;i<crons.length;i++){
				cron=crons[i];
				key=keys[i];
				//待考虑一：需要更新多个缓存时，存在多个key
				//有待验证：CacheUpdateThread的Bean设置为prototype,是否每次都新建了一个实例对象？？？
				/*解决办法：不用@Autowired自动注入CacheUpdateThread属性，用IOC容器上下文的getBean方法获取实例，
				 * 能够确保每次都新建一个实例对象（等同于new）
				 * */
//				CacheUpdateThread cacheUpdateThread=(CacheUpdateThread) ShchCacheApplication.ctx.getBean("cacheUpdateThread");
				//？？Bean实例为空？？ 实际上是applicationContext为空
//				if(applicationContext==null){
//					System.out.println("applicationContext is null!");
//				}

	//			CacheUpdateThread cacheUpdateThread=applicationContext.getBean(CacheUpdateThread.class);
	//			CacheUpdateThread cacheUpdateThread=(CacheUpdateThread) applicationContext.getBean("cacheUpdateThread");
				CacheUpdateThread cacheUpdateThread=new CacheUpdateThread();
				
				cacheUpdateThread.setKey(key);
				cacheUpdateThread.setCacheScheduleUpdate(cacheScheduleUpdateMap.get(key));				
				CronTrigger cronTrigger=new CronTrigger(cron);
				//待考虑三：如果有多个实例，如CacheScheduleUpdate1、CacheScheduleUpdate2 ... CacheScheduleUpdateN,怎么处理？？
				//为每一个缓存定时任务新建一个Thread类（2017.1.16）
				//tpts.schedule(Runnable task, Trigger trigger);
				tpts.schedule(cacheUpdateThread, cronTrigger);//启动定时任务
				
			}			
		}else{
			logger.error("配置文件application.properties中cron表达式和key的缓存个数不一致！");
		}
				

		//待考虑二：线程池的使用、
		//待研究如何启动，以及线程池的配置？？？？？
		//tpts.schedule(Runnable task, Trigger trigger);
				
		//待考虑四：每个线程怎么结束？？		
		
    }
}

