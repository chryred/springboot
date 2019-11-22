package com.shinsegae.smon.schedule.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.shinsegae.smon.schedule.job.support.ScheduleJob;
import com.shinsegae.smon.util.NLogger;


@Configuration
public class SpringSchedulerConfig {
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	SpringScheduleService springScheduleService;
	
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);
		taskScheduler.setThreadNamePrefix("smon-scheduler-");
		taskScheduler.initialize();
		
		return taskScheduler;
	}
	
	
	@PostConstruct
	public void startScheduler() throws Exception {
		springScheduleService.searchScheduleTrigger().forEach(action -> {
			String strBeanId = (String)action.get("BEAN_ID");
			String strCronExpression = (String)action.get("CRON_EXPRESSION");
			
			ScheduleJob scheduleJob = (ScheduleJob)applicationContext.getBean(strBeanId);
			
			threadPoolTaskScheduler.schedule(() -> {
				try {
					scheduleJob.beforeJob();
					scheduleJob.execute();
					scheduleJob.afterJob();
				} catch(Exception e) {
					NLogger.debug("Scheduler config Error ~!!");
				}
			}, new CronTrigger(strCronExpression));
		});
	}
	
	public void stopScheduler() {
		threadPoolTaskScheduler.shutdown();
	}
}