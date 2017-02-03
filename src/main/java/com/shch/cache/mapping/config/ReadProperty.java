package com.shch.cache.mapping.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/*直接采用Java读取文件字节流的方式获取配置文件application.properties信息
 * */
public class ReadProperty {
	public static Properties prop=new Properties(); //静态属性
	private FileInputStream fis;
	public String filePath;//该路径待在xml中配置为：application.properties
	
	public ReadProperty(){
		
	}
	public void setFilePath(String filePath){
		this.filePath=filePath;
	}

	public void loadProperty() throws IOException{ //初始化方法，在xml的Bean定义时要指定
		fis=new FileInputStream(filePath);
		prop.load(fis);
		//prop.list(System.out);//测试
		//return prop;
	}

}
