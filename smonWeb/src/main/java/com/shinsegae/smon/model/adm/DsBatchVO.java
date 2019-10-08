package com.shinsegae.smon.model.adm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DsBatchVO {
	String jobId;
	String projectName;
	String folderPath;
	String jobName;
	String jobShortDesc;
	String jobLongDesc;
	Integer jobIdRank;
	String flag;
	Integer leftPx;
	Integer LengthPx;
	String staDate;
	String endDate;
	String runMajorStatus;
	String runMinorStatus;
	String elapsedRunSecs;
	String runStartDate;
	String runStartTime;
	String runEndDate;
	String runEndTime;
	Integer rnum;
	String subFolderPath;
	Integer subJobCnt;
	Integer totCnt;
	Integer fokCnt;
	Integer runid;
	Integer controllingRunid;
	String staDay;
	String staTime;
	String endDay;
	String endTime;
	String elapseTime;
	Integer masterPid;
	Integer conductorPid;
	Integer totOkCnt;
	String totRate;
	Integer jobCnt;
	Integer jobOkCnt;
	String jobRate;
	Integer totalCpu;
	
	
	String mgrId ;
	String mgrName;
	String mgrSys;
	String mgrGrade;
	String mgrStatecd;
	Integer rownum;
     
	 
    		
	/** 폴더구조 */
	String id;
	String pId;
	String pId1;
	String name;
	String icon;
	String open;
	String menulink;
	String menuOrder;
	String useyn;
	Integer lev;
	String authCd;
	String level;
	String menuImg;
 
	
	
	String menuId;
	String menuNm;
	String upMenuId;
 

	/** 권한그룹*/
	String authGroupid;
	String authGroupnm;
	String authGroupremark;
	String regId;
	String regDate;
	String modId;
	String modDate;
	String authLevel;
	String roleGroup; 

	/** 조회조건 */
	String baseDay;
	
	String subFolderYn;
	String successYn;
	String subYn;
	String contextPath;
	String displaySub;
	String colid;
	String sorting;
	String sUserList; 
	
	/** 페이지 처리 */
	int start;
	int end;
	int curPage;
	int pageScale;	
}
