package com.shinsegae.smon.model.bat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*******************************
 * 배치 트리거 모델
 * @author 153712 김성일
 ********************************/
@ToString
public class BatchTrigger {
	private String triggerName;
	private String jobName;
	private String triggerType; //CRON, SIMPLE
	private String triggerStatus; //PAUSED, WATING;

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

	public String getTriggerStatus() {
		return triggerStatus;
	}

	public void setTriggerStatus(String paramTriggerStatus) {
		String triggerStatus = paramTriggerStatus;
		if(!"PAUSED".equals(triggerStatus)) {
			triggerStatus = "WAITING";
		}
		
		this.triggerStatus = triggerStatus;
	}
}
