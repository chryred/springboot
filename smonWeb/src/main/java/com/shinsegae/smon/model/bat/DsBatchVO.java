package com.shinsegae.smon.model.bat;

import lombok.ToString;

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

	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobShortDesc() {
		return jobShortDesc;
	}
	public void setJobShortDesc(String jobShortDesc) {
		this.jobShortDesc = jobShortDesc;
	}
	public String getJobLongDesc() {
		return jobLongDesc;
	}
	public void setJobLongDesc(String jobLongDesc) {
		this.jobLongDesc = jobLongDesc;
	}
	public Integer getJobIdRank() {
		return jobIdRank;
	}
	public void setJobIdRank(Integer jobIdRank) {
		this.jobIdRank = jobIdRank;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getLeftPx() {
		return leftPx;
	}
	public void setLeftPx(Integer leftPx) {
		this.leftPx = leftPx;
	}
	public Integer getLengthPx() {
		return lengthPx;
	}
	public void setLengthPx(Integer lengthPx) {
		this.lengthPx = lengthPx;
	}
	public String getStaDate() {
		return staDate;
	}
	public void setStaDate(String staDate) {
		this.staDate = staDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRunMajorStatus() {
		return runMajorStatus;
	}
	public void setRunMajorStatus(String runMajorStatus) {
		this.runMajorStatus = runMajorStatus;
	}
	public String getRunMinorStatus() {
		return runMinorStatus;
	}
	public void setRunMinorStatus(String runMinorStatus) {
		this.runMinorStatus = runMinorStatus;
	}
	public String getElapsedRunSecs() {
		return elapsedRunSecs;
	}
	public void setElapsedRunSecs(String elapsedRunSecs) {
		this.elapsedRunSecs = elapsedRunSecs;
	}
	public String getRunStartDate() {
		return runStartDate;
	}
	public void setRunStartDate(String runStartDate) {
		this.runStartDate = runStartDate;
	}
	public String getRunStartTime() {
		return runStartTime;
	}
	public void setRunStartTime(String runStartTime) {
		this.runStartTime = runStartTime;
	}
	public String getRunEndDate() {
		return runEndDate;
	}
	public void setRunEndDate(String runEndDate) {
		this.runEndDate = runEndDate;
	}
	public String getRunEndTime() {
		return runEndTime;
	}
	public void setRunEndTime(String runEndTime) {
		this.runEndTime = runEndTime;
	}
	public Integer getRnum() {
		return rnum;
	}
	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public Integer getLev() {
		return lev;
	}
	public void setLev(Integer lev) {
		this.lev = lev;
	}
	public String getBaseDay() {
		return baseDay;
	}
	public void setBaseDay(String baseDay) {
		this.baseDay = baseDay;
	}
	public String getSubFolderYn() {
		return subFolderYn;
	}
	public void setSubFolderYn(String subFolderYn) {
		this.subFolderYn = subFolderYn;
	}
	public String getSubFolderPath() {
		return subFolderPath;
	}
	public void setSubFolderPath(String subFolderPath) {
		this.subFolderPath = subFolderPath;
	}
	public Integer getSubJobCnt() {
		return subJobCnt;
	}
	public void setSubJobCnt(Integer subJobCnt) {
		this.subJobCnt = subJobCnt;
	}
	public Integer getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}
	public Integer getFokCnt() {
		return fokCnt;
	}
	public void setFokCnt(Integer fokCnt) {
		this.fokCnt = fokCnt;
	}
	public String getSuccessYn() {
		return successYn;
	}
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}
	public String getSubYn() {
		return subYn;
	}
	public void setSubYn(String subYn) {
		this.subYn = subYn;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getDisplaySub() {
		return displaySub;
	}
	public void setDisplaySub(String displaySub) {
		this.displaySub = displaySub;
	}
	public Integer getRunid() {
		return runid;
	}
	public void setRunid(Integer runid) {
		this.runid = runid;
	}
	public Integer getControllingRunid() {
		return controllingRunid;
	}
	public void setControllingRunid(Integer controllingRunid) {
		this.controllingRunid = controllingRunid;
	}
	public String getStaDay() {
		return staDay;
	}
	public void setStaDay(String staDay) {
		this.staDay = staDay;
	}
	public String getStaTime() {
		return staTime;
	}
	public void setStaTime(String staTime) {
		this.staTime = staTime;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getElapseTime() {
		return elapseTime;
	}
	public void setElapseTime(String elapseTime) {
		this.elapseTime = elapseTime;
	}
	public Integer getMasterPid() {
		return masterPid;
	}
	public void setMasterPid(Integer masterPid) {
		this.masterPid = masterPid;
	}
	public Integer getConductorPid() {
		return conductorPid;
	}
	public void setConductorPid(Integer conductorPid) {
		this.conductorPid = conductorPid;
	}
	public Integer getTotOkCnt() {
		return totOkCnt;
	}
	public void setTotOkCnt(Integer totOkCnt) {
		this.totOkCnt = totOkCnt;
	}
	public String getTotRate() {
		return totRate;
	}
	public void setTotRate(String totRate) {
		this.totRate = totRate;
	}
	public Integer getJobCnt() {
		return jobCnt;
	}
	public void setJobCnt(Integer jobCnt) {
		this.jobCnt = jobCnt;
	}
	public Integer getJobOkCnt() {
		return jobOkCnt;
	}
	public void setJobOkCnt(Integer jobOkCnt) {
		this.jobOkCnt = jobOkCnt;
	}
	public String getJobRate() {
		return jobRate;
	}
	public void setJobRate(String jobRate) {
		this.jobRate = jobRate;
	}
	public Integer getTotalCpu() {
		return totalCpu;
	}
	public void setTotalCpu(Integer totalCpu) {
		this.totalCpu = totalCpu;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public String getColid() {
		return colid;
	}
	public void setColid(String colid) {
		this.colid = colid;
	}
	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	public int getPageScale() {
		return pageScale;
	}
	public void setPageScale(int pageScale) {
		this.pageScale = pageScale;
	}	
	
	
	
}
