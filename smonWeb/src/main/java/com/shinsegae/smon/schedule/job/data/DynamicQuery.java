package com.shinsegae.smon.schedule.job.data;

import java.util.Map;

public class DynamicQuery {
	
	public String excuteQry(Map<String, Object> parameter){
		return parameter.get("QRY").toString();
	}
	
	public String excuteQryString(String strQry){
		return strQry;
	}
}
