package com.shch.cache.mapping;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import com.shch.cache.Cache;
import com.shch.cache.CacheManager;
import com.shch.cache.mapping.config.CacheSettings;
import com.shch.cache.mapping.config.PropertyConfigurer;
import com.shch.cache.support.CompositeCacheManager;

public class CacheMapping {
    //public ReadProperty readProp; //该属性Bean待注入
    //public CacheSettings cacheSettings;
    public PropertyConfigurer propertyConfigurer;
    public CompositeCacheManager compCacheManager; //待在Xml中配置或用自动注入，然后comCacheManager Bean的配置中，要配置多个实现了CacheManager的其他Bean
    private String cacheName=null;//暂时只实现三种：CurrentHashMap、redis和Memcached
    private static Logger logger=Logger.getLogger(CacheMapping.class);
    
	public CacheMapping(){

	}
//	public void setreadProp(ReadProperty readProp){
//		this.readProp=readProp;
//	}
//	public void setcacheSettings(CacheSettings cacheSettings){
//		this.cacheSettings=cacheSettings;
//	}
	public void setcompCacheManager(CompositeCacheManager compCacheManager){
		this.compCacheManager=compCacheManager;
	}
	
	public void setPropertyConfigurer(PropertyConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}
	//通过key返回Cache实例
	public Cache routeCache(String key) throws ReflectiveOperationException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		keySplit(key); //先执行该方法，才能得到RealKey和cacheName
		Cache cache=compCacheManager.getCache(cacheName);
        if(cache==null){
        	logger.error("路由查找的缓存"+cacheName+"的实例为空！");
        }
		return cache;
	}

	private void keySplit(String key) throws NoSuchFieldException, SecurityException, IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String[] splitResult=null;
		if(key!=null&&key.toString().contains(".")){ //该条件待验证是否有问题
			//在此处申明String字符串，因为split分割的结果是字符数组，且不知其数组长度
		    splitResult=key.toString().split("\\."); 
			if(splitResult[0]!=null){ //人为规定只有两段,key的命名规范检查逻辑以后放在前置处理
				String key_pro=splitResult[0];
		       // realKey=key;
				//cacheName=(String)readProp.prop.getProperty(key_pro);	
		        //注解的key:stock.info，则key_pro=stock
		       
				//****难点：通过反射根据CacheSetting中的属性名得到属性的值（CacheSetting类读取applicaiton.properties配置文件的信息）
/*		        Class<? extends CacheSettings> clazz=cacheSettings.getClass();
		        Field field=clazz.getDeclaredField(key_pro);//通过类的实例得到类的属性
		        if(field!=null){

		        	  PropertyDescriptor pd=new PropertyDescriptor(field.getName(),clazz);
		        	  Method getMethod=pd.getReadMethod();//获得属性的读方法，pd.getWriteMethod获得写方法，即set方法
		        	  String result=(String) getMethod.invoke(cacheSettings);
		        	  if(logger.isDebugEnabled()){
		        		  logger.debug("通过反射得到的属性名："+field.getName()+"   值为："+result);
		        	  }
		        	  if(result!=null){
		        		  cacheName=result;
		        	  }else{
		        		  logger.error("配置文件application.properties中"+key_pro+"的值为空！");
		        	  }
		        }else{
		        	  logger.error("配置文件application.properties中没有配置"+key_pro+"的信息！");
		        }
		        
*/              cacheName=(String) propertyConfigurer.getPropertyValue(key_pro);
				logger.debug("properties文件key--"+key_pro+"    value--"+cacheName);
			}else{
				logger.error("注解中缓存的key有问题，第一个分隔符.之前为空！");
			}
	    }else{
	    	logger.error("注解中缓存的key为空或者key的格式不正确！");	    
	    }		
    }
	
}
