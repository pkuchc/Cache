package com.shch.cache.update;
/*调度器，单独的线程扫描定时任务有哪些，同时要管理多个定时任务
 * 对多个任务的差异化管理
 * */

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.shch.CacheScheduleUpdate;
import com.shch.cache.mapping.CacheOperation;

public class CacheScheduleUpdateJob implements Runnable{ //调度器怎么设计？？
	private String cron=null;
	private ICacheScheduleUpdate cacheScheduleUpdate; //待注入实现该接口的实例
	private Object key;
	private Object obsoleteCacheResult;
	private CacheOperation cacheOperation;
	private static Logger logger=Logger.getLogger(CacheScheduleUpdateJob.class);
	
	private ThreadPoolTaskScheduler tpts=null;
	
	private String cronExpression="0/2 * * * * ? ";
	
	
	@PostConstruct //Spring中容器初始化方法，等同于xml配置文件中的init-method
	private void start(){ //初始化方法，IOC容器加载这个Bean时，先执行初始化方法
		tpts=new ThreadPoolTaskScheduler();
		//必须初始化才能使用
		tpts.initialize();
		
		CronTrigger cronTrigger=new CronTrigger(cronExpression);
		//待规划两个类，不要在类里面引用本身this
		tpts.schedule(this, cronTrigger);//启动定时任务
		
		
		//待研究如何启动，以及线程池的配置？？？？？
		//tpts.schedule(Runnable task, Trigger trigger);
		
	}
	@PreDestroy //Spring中容器销毁方法，等同于xml配置文件中的destory-method
	private void stop(){
		tpts.shutdown(); //关闭定时任务
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.debug("启动线程方法体do nothing！");
	}

}
