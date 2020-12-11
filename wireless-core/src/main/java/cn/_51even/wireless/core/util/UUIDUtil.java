package cn._51even.wireless.core.util;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * 获得一个UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}



}