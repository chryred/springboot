package com.shinsegae.smon.cpu;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.util.CastingJson;

@Controller
public class CpuUsageDbMonitoringController {
	
		@Autowired
		CastingJson json;
		
		@Autowired
		CpuUsageDbMonitoringService cpuUsageDbMonitoringService;
		
		@RequestMapping("/selectCpuUsage")
		public @ResponseBody String selectCpuUsage(@RequestParam HashMap<String,String> map) {
		    
		    String data ="";
		   
		    try{
		        data = json.setJsonData(cpuUsageDbMonitoringService.selectDbDictionaryInfo(map));
		    }catch(Exception e){
		        e.printStackTrace();
		        data = null;
		    }
		    
		    return data;
		}
		
		@RequestMapping("/selectStatusOfInterlocking")
		public @ResponseBody String selectStatusOfInterlocking() {
		    
		    String data ="";
		   
		    try{
		        data = json.setJsonData(cpuUsageDbMonitoringService.selectStatusOfInterlocking());
		    }catch(Exception e){
		        e.printStackTrace();
		        data = null;
		    }
		    
		    return data;
		}
		
		@RequestMapping(value="/sslInfo")
	    public ModelAndView sslInfoManagement(Locale locale, Model model) {

	        ModelAndView view = new ModelAndView("mtr/detail/ssl/sslInfoManagement");
	        
	        return view;
	    }
		
		
		@RequestMapping("/sslInfoDataTables")
		public @ResponseBody String sslInfoDataTables() {
		    
		    String data ="";
		   
		    try{
		        data = json.setJsonData(cpuUsageDbMonitoringService.sslInfoDataTables());
		    }catch(Exception e){
		        e.printStackTrace();
		        data = null;
		    }
		    
		    return data;
		}

}
