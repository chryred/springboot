package com.shinsegae.smon.model.bat;

import lombok.ToString;

/*********************************
 * 배치 검색 조건
 * @author 153712 김성일
 *********************************/
@ToString
public class BatchJob {
	
	private String jobGroup;
	
	private String triggerName;
	
	private String jobName;
	
	private String triggerType;
	
	private String jobClass;
	
	private String description;
	
	private String cronExpression;
	
	private int repeatInterval;

	private int repeatCount;
	
	private String jobStatus;
	
	private String jobListener;
	
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getJobListener() {
		return jobListener;
	}

	public void setJobListener(String jobListener) {
		this.jobListener = jobListener;
	}		
	
	public void setKoreanVO(BatchJob paramVO) {
		try {
			this.jobGroup = decodeUS7ASCII(paramVO.getJobGroup());
			this.triggerName = decodeUS7ASCII(paramVO.getTriggerName());
			this.jobName = decodeUS7ASCII(paramVO.getJobName());
			this.triggerType = decodeUS7ASCII(paramVO.getTriggerType());
			this.jobClass = decodeUS7ASCII(paramVO.getJobClass());
			this.description = decodeUS7ASCII(paramVO.getDescription());
			this.cronExpression = decodeUS7ASCII(paramVO.getCronExpression());
			this.jobStatus = decodeUS7ASCII(paramVO.getJobStatus());
			this.jobListener = decodeUS7ASCII(paramVO.getJobListener());
			
		} catch (Exception e) {
			System.out.println("BatchJob 한글화 실패");
		}
	}
	
	private String decodeUS7ASCII(String value) {
    	if(value== null) {
    		return "";
    	}
    	String str = "";
    	try {
    		str = new String(value.getBytes("8859_1"), "KSC5601");
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return str;
    }	
}
