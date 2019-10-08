package com.shinsegae.smon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccessLogActionMapVO {
	private String URL;
	private String gubun1;
	private String gubun2;
	private String gubun3;
	private String actionName;
	private String actionGubunCd;
	private String menuId;
	private String regId;
	private String regDate;
	private String modId;
	private String modDate;
	private String personalYN;
	private String voConditionYN;
	private String voClassName;
	private String voColumnName;
	private String voArrayYN;
	
	public AccessLogActionMapVO() {
		super();
		this.actionGubunCd = "R";
		this.personalYN = "N";
		this.voConditionYN = "N";
		this.voArrayYN = "N";
	}

	public AccessLogActionMapVO(String URL, String gubun1, String gubun2,
			String gubun3, String actionName, String actionGubunCd,
			String menuId, String regId, String regDate, String modId,
			String modDate, String personalYN, String voConditionYN,
			String voClassName, String voColumnName, String voArrayYN) {
		super();
		this.URL = URL;
		this.gubun1 = gubun1;
		this.gubun2 = gubun2;
		this.gubun3 = gubun3;
		this.actionName = actionName;
		this.actionGubunCd = actionGubunCd;
		this.menuId = menuId;
		this.regId = regId;
		this.regDate = regDate;
		this.modId = modId;
		this.modDate = modDate;
		this.personalYN = personalYN;
		this.voConditionYN = voConditionYN;
		this.voClassName = voClassName;
		this.voColumnName = voColumnName;
		this.voArrayYN = voArrayYN;
	}	
}
