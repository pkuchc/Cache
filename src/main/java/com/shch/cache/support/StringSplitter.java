package com.shch.cache.support;

import org.springframework.stereotype.Component;

@Component
public class StringSplitter {
	public String[] split(String splitObject,String splitChar){
		String []result=null;
		if(splitObject!=null){
			if(splitObject.contains(splitChar)){
				//在此处申明String字符串，因为split分割的结果是字符数组，且不知其数组长度
			    result=splitObject.split("\\"+splitChar); 
			}else{ //字符串中不含有分隔符，则不用分割，数组只有一个元素
				result=new String[1];
				result[0]=splitObject;			
		    }			
		}
		return result;
	}

}
