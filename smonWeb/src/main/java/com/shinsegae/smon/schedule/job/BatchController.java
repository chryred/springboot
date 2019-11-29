package com.shinsegae.smon.schedule.job;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.schedule.job.data.BaseInfoService;
import com.shinsegae.smon.schedule.job.data.DailyCollectInfoService;
import com.shinsegae.smon.util.CubeoneAPIHandler;
import com.shinsegae.smon.util.RequestMap;

@Controller
@RequestMapping("/job")
public class BatchController {
	@Autowired
	BaseInfoService baseInfoService;
	
	@Autowired
	DailyCollectInfoService dailyCollectInfoService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/baseInfo.do")
	@ResponseBody
	public Map<String, Object> baseInfoBatchJob() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		baseInfoService.syncBaseInfo();
		retMap.put("msg", "success");
		return retMap;
	}
	
	@RequestMapping("/firstCollectInfo.do")
	@ResponseBody
	public Map<String, Object> firstCollectInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestMap param = RequestMap.getRequestMap(request, response);

		Thread thread  = new Thread(() -> {
			try {
				dailyCollectInfoService.firstDailyCollectInfo(param);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		thread.start();
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("msg", "success");
		
		return retMap;
	}
	
	@RequestMapping("/dailyCollectInfo.do")
	@ResponseBody
	public Map<String, Object> dailyCollectInfoBatchJob() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		dailyCollectInfoService.syncDailyCollectInfo();
		retMap.put("msg", "success");
		
		return retMap;
	}
}
