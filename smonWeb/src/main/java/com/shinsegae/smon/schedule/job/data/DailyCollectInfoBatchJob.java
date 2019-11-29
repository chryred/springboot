package com.shinsegae.smon.schedule.job.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shinsegae.smon.schedule.job.support.ScheduleJob;

@Component
public class DailyCollectInfoBatchJob extends ScheduleJob {
	
	@Autowired
	DailyCollectInfoService dailyCollectInfoService;

	@Override
	public void execute() throws Exception {
		dailyCollectInfoService.syncDailyCollectInfo();
	}
}
