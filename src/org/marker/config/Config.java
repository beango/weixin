package org.marker.config;


/**
 * 配置文件
 * @author marker
 * @date 2014年8月30日
 * @version 1.0
 */
public interface Config {

	// 赋权类型 
	String grant_type = "client_credential";
	
	// 修改为开发者申请的appid
	String APPID      = "";
	
	// 修改为开发者申请的secret密钥
	String SECRET     = "";
	
}
