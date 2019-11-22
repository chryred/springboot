package com.shinsegae.smon.schedule.job.support;

import com.shinsegae.smon.util.NLogger;

public abstract class ScheduleJob {

	public abstract void execute() throws Exception;
	
	public void beforeJob() {
		NLogger.debug("schedule before Trtm : ", getClass().getName());
	}
	
	public void afterJob() {
		NLogger.debug("schedule afterJob Trtm");
	}
}
