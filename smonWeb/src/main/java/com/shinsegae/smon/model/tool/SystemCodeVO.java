package com.shinsegae.smon.model.tool;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemCodeVO {
	String groupCode;
	String systemCode;
	String systemName;
	String hostName;
	String dbSid;
	String entEmpno;
	String entDate;
	String modEmpno;
	String modDate;
	String svnYn;
	String svnUrl;
	String svnUser;
	String svnPwd;
	int svnDispUnit;
}
