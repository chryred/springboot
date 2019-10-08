package com.shinsegae.smon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserAccessLogVO {
	
	int		seqNo;
	String 	userId;
	String 	userGubunCd;
	String 	logDate;
	String  URL;
	String 	reqParams;
	String 	logIp;
	String 	regId;
	String 	regDate;
	String	modId;
	String	modDate;
	String 	execTime;
	
	public UserAccessLogVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAccessLogVO(String userId, String URL, String reqParams, String logIp,
			String regId, String modId, String execTime) {
		super();
		this.userId = userId;
		this.URL = URL;
		this.reqParams = reqParams;
		this.logIp = logIp;
		this.regId = regId;
		this.modId = modId;
		this.execTime = execTime;
	}
}