package com.shch.cache.mapping;


public class CacheOperation extends AbstractCacheOperation implements ICacheOperation{
	//注意：在XML配置该类时，需要配置抽象类的cacheMapping属性
	@Override
	public Object doGet(Object key) {
		// TODO Auto-generated method stub
		super.getCache(key.toString());
        if(cache!=null){ //疑问：当执行到该处时，会主动执行AbstractCacheOperation中的getCache方法吗？
        	return cache.get(key);
        }else{
     	   logger.error("缓存实例"+cache+"中不存在缓存："+key.toString());
     	   return null;
        }		
	}

	@Override
	public void doPut(Object key, Object result) {
		// TODO Auto-generated method stub
		super.getCache(key.toString());
        if(cache!=null){ 
        	cache.put(key, result);
        }else{
        	logger.error("缓存实例"+cache+"存入缓存key--"+key.toString()+"  value--"+result.toString()+"失败！");
     	   
        }		
	}

	@Override
	public void doEvict(Object key) {
		// TODO Auto-generated method stub
		super.getCache(key.toString());
        if(cache!=null){ 
        	cache.evict(key);
        }else{
        	logger.error("缓存实例"+cache+"删除缓存key--"+key.toString()+"失败！");     	   
        }
	}

	@Override
	public void doClear(Object key) {
		// TODO Auto-generated method stub
		super.getCache(key.toString());
        if(cache!=null){ 
			cache.clear();
        }else{
			logger.error("缓存实例"+cache+"清空失败！");     	   
        }
	}


}
