package com.shch.cache.update;

import org.springframework.beans.factory.annotation.Autowired;

import com.shch.cache.mapping.ICacheOperation;
import com.shch.cache.mapping.config.PropertyConfigurer;

public class AbstractCacheScheduleUpdate {
//	@Autowired
//	public PropertyConfigurer propertyConfigurer;
	@Autowired
	public ICacheOperation cacheOperation;
		
//	public Object[] getObsoleteResult(String key){
//		String value=(String) propertyConfigurer.g+etPropertyValue(key);
//		 //scheduleUpdate=stock.info,clear.info
//		//对字符串的截取
//		String[] splitResult=null;
//		if(value!=null&&value.toString().contains(",")){ //该条件待验证是否有问题
//			//在此处申明String字符串，因为split分割的结果是字符数组，且不知其数组长度
//		    splitResult=value.toString().split("\\,");
//		    
//		}
//		Object []result = null;
//		for(int i=0;i<splitResult.length;i++){
//			if(splitResult[i]!=null){
//				result[i]=cacheOperation.doGet(splitResult[i]);//先取出老的缓存;
//			}
//		}
//		
//		return result;
//	}


	public void scheduleUpdateTask() {
		// TODO Auto-generated method stub
		
	}
	

}
