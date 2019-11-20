package com.shinsegae.smon.model.bat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ShubBatchVO {
	String jobId;
	String projectName;
	String folderPath;
	String jobName;
	String jobShortDesc;
	String jobLongDesc;
	Integer jobIdRank;
	String flag;
	Integer leftPx;
	Integer lengthPx;
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
	
	/** 폴더구조 */
	String id;
	String pId;
	String name;
	String icon;
	String open;
	Integer lev;
	
	/** 조회조건 */
	String baseDay;
	String subFolderYn;
	String successYn;
	String subYn;
	String contextPath;
	String displaySub;
	String colid;
	String sorting;
	
	/** 페이지 처리 */
	int start;
	int end;
	int curPage;
	int pageScale;
}
