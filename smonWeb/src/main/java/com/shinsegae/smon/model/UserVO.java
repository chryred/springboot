package com.shinsegae.smon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {
	private String mgrId;
	private String mgrPwd;
	private String mgrPwd2;
	private String mgrOldPwd;
	private String mgrName;
	private String mgrSys;
	private String mgrGrade;
	private String certNum;
	private String mgrPwdChgDate;
	private String mgrStateCd;
	private String mgrTask;
	private Integer loginFailCnt;
	
	private String sid;
	private String userIp;
	private String userHostName;
	
	/** 로그인 이력 */
	private String logType;
	private String loginIp;
	private String loginSsid;
	private String logDate;
	private String sessionCreDtm;			
	
	/** 사용자 상태 처리 */
	private String retireGubun;
	private String unusedGubun;
	
	private String roleGroup;
}
