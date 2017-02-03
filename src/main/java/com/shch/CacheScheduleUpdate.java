package com.shch;
/*需要在application.xml中配置定时任务
 * <task:scheduled-tasks> 
      <task:scheduled ref="cacheScheduleUpdate" method="scheduleUpdateTask" cron="0/2 * * * * ? "/> 
       <!-- 可以定义多个定时任务 -->
   </task:scheduled-tasks> 
 * 
 * */
import java.io.Serializable;
import org.apache.log4j.Logger;
import com.shch.cache.mapping.ICacheOperation;
import com.shch.cache.update.AbstractCacheScheduleUpdate;
import com.shch.cache.update.ICacheScheduleUpdate;

//@Component("sheduleTask")
public class CacheScheduleUpdate extends AbstractCacheScheduleUpdate implements ICacheScheduleUpdate{
	private static Logger logger=Logger.getLogger(CacheScheduleUpdate.class);
    private int num=0;
    public String key=null; //待注入

	@Override
	//@Scheduled(cron="0/2 * *  * * ? ") //每2秒执行一次
	public void scheduleUpdateTask() {
		// TODO Auto-generated method stub
        key="stock.info"; //暂时写死，需要用户配置指定

//		Object[] result=super.getObsoleteResult("property文件中的key");
		if(IsNeedUpdated(cacheOperation,key)){
			
			cacheOperation.doPut(key,(Serializable)GetNewCacheResult(key));//更新缓存
			logger.debug("缓存过期，定时任务更新缓存！");			
		}else{
			logger.debug("缓存未过期，定时任务无需更新缓存！");
		}		
	}

	@Override
	public boolean IsNeedUpdated(ICacheOperation cacheOperation,Object key) {
		// TODO Auto-generated method stub
		boolean isSame=true;
		Object obsoleteResult=cacheOperation.doGet(key);
		Object newResult=GetNewCacheResult(key);
        logger.info("过时缓存值："+obsoleteResult+"  新的缓存值："+newResult);
        if(obsoleteResult.equals(newResult)){ 
        	logger.debug("obsoleteResult:"+obsoleteResult+"   newResult:"+newResult);
			isSame=false;
		}		
		logger.debug("是否需要更新缓存："+isSame);
		return isSame;
	}

	@Override
	public Object GetNewCacheResult(Object key) {
		// TODO Auto-generated method stub
		//传入key，从数据源获得最新的缓存结果
		num++;
		return num;
	}

	@Override
	public Object GetKey() {
		// TODO Auto-generated method stub
		return "stock.info";
	}

}
