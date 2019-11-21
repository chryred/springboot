package com.shinsegae.smon.schedule.job;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.schedule.job.data.BaseInfoBatchJob;

@Controller
@RequestMapping("/job")
public class BatchController {
	@Autowired
	BaseInfoBatchJob baseInfoBatchJob;
	
	@RequestMapping("/baseInfo.do")
	@ResponseBody
	public Map<String, Object> baseInfoBatchJob() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		baseInfoBatchJob.execute();
		retMap.put("msg", "success");
		
		return retMap;
	}
}
