package com.shch.cache.ShchCache;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.SerializationUtils;

import com.shch.cache.Cache;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache{
	private String name;
	private Jedis jedisClient;
	//private RedisTemplate<String,Object> redtemp;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		Object result=null;
		byte[] v=jedisClient.get(key.toString().getBytes());
		Serializable val=(Serializable) SerializationUtils.deserialize(v);
		result=val;
		return result;
	}

	@Override
	public void put(Object key, Object value) {
		// TODO Auto-generated method stub
		if(value!=null){
			Serializable val=(Serializable) value;//value必须是Serializable类型
			jedisClient.set(key.toString().getBytes(), SerializationUtils.serialize(val));
		}
	}

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub
		jedisClient.del(key.toString().getBytes());

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		jedisClient.flushDB();//清空数据
	}

}
