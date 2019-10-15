package com.shinsegae.smon.schedule.job;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shinsegae.smon.login.LoginService;
import com.shinsegae.smon.schedule.job.support.ScheduleJob;
import com.shinsegae.smon.util.NLogger;

@Component
public class SampleJob extends ScheduleJob {
	
	@Autowired
	LoginService loginService;

	@Override
	public void execute() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mgr_id", "173917");
		loginService.searchUser(map);
		
		NLogger.debug("=============================");
		NLogger.debug(Thread.currentThread().getName());
		
	}
	
//	@Autowired
//	TaskScheduler taskScheduler; 


//	@Scheduled(cron="0/3 * * * * ?")
//	protected void doExcute() {
//		NLogger.debug("SampleJob", loginService);
////		taskScheduler.
//		
//	}
//	
//	@Scheduled(cron="0/3 * * * * ?")
//	protected void doExcute1() {
//		NLogger.debug("SampleJob2", loginService);
////		taskScheduler.
//		
//	}
//	
//	@Scheduled(cron="0/3 * * * * ?")
//	protected void doExcute3() {
//		NLogger.debug("SampleJob3", loginService);
////		taskScheduler.
//		
//	}
}
