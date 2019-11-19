package com.shinsegae.smon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CubeoneVO {
	private String status = "200";
	private String msg;
	private int crudLog;
	private String itemCd;
	private String tableName;
	private String columnName;
	private String errorMsg;
	private String retMsg;
	
	public String toParamValue() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("msg=").append(msg)
		  .append("&").append("crudLog=").append(crudLog)
		  .append("&").append("itemCd=").append(itemCd)
		  .append("&").append("tableName=").append(tableName)
		  .append("&").append("columnName=").append(columnName);
		
		return sb.toString();
	}
}
