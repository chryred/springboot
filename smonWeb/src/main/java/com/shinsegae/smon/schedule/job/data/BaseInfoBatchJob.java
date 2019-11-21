package com.shinsegae.smon.schedule.job.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shinsegae.smon.schedule.job.support.ScheduleJob;

@Component
public class BaseInfoBatchJob extends ScheduleJob {
	
	@Autowired
	BaseInfoService baseInfoService;

	@Override
	public void execute() {
		baseInfoService.syncBaseInfo();
	}
}
