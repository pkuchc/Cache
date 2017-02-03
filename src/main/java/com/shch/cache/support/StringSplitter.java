package com.shch.cache.support;

public class StringSplitter {
	public String[] split(String splitObject,String splitChar){
		String []result=null;
		if(splitObject!=null&&splitObject.contains(splitChar)){ //该条件待验证是否有问题
			//在此处申明String字符串，因为split分割的结果是字符数组，且不知其数组长度
		    result=splitObject.split("\\"+splitChar); 
		}
		return result;
	}

}
