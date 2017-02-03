package com.shch.cache.ShchCache;

import java.util.concurrent.ConcurrentHashMap;

import com.shch.cache.Cache;

public class ConcurrentHashMapCache implements Cache{
	private final String cacheName; //final参数，只能通过构造方法注入，不能通过set方法注入，为什么？？待查？？
	private ConcurrentHashMap<Object,Object> cacheStore=new ConcurrentHashMap<Object,Object>(); //缓存真正的存储实体，就是一个ConcurrentHashMap

	
	public ConcurrentHashMapCache(String cacheName){ //通过构造方法注入缓存名字
		this.cacheName=cacheName;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.cacheName;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		
		return cacheStore.get(key);
	}

	@Override
	public void put(Object key, Object value) {
		// TODO Auto-generated method stub
		cacheStore.put(key, value);
	}

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub
		cacheStore.remove(key);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		cacheStore.clear();
	}

}
