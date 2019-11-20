package com.shinsegae.smon.privacy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinsegae.smon.util.CastingJson;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@RequestMapping("/personalInfo")
@Controller
public class PersonalInfoMonitoringController {

	@Autowired
	PersonalInfoMonitoringService personalInfoMonitoringService;
	
	@Autowired
    CastingJson json;
	
	@RequestMapping("/search")
    public ModelAndView personalInfoSearch() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoSearch");
        
        return mv;
    }

	@ResponseBody
	@RequestMapping(value="/search/ajax/list", method=RequestMethod.POST)
	public String list(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="infoType") String infoType,
			@RequestParam(value="exceptFlag") String exceptFlag
			) {
		//System.out.println("monitoring list controller");
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("owner", owner);
		params.put("infoType", infoType);
		params.put("exceptFlag", exceptFlag);
		
		List<HashMap<String, Object>> list = personalInfoMonitoringService.selectPersonalInfoList(params);
		
		HashMap<String, Object> jqgridResult = new HashMap<String, Object>();
		jqgridResult.put("page", 1);
		jqgridResult.put("records", list.size());
		jqgridResult.put("total", 1);
		jqgridResult.put("rows", list);

		return JSONObject.fromObject(JSONSerializer.toJSON(jqgridResult)).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list/dbName", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboDBName() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		return personalInfoMonitoringService.selectComboDBName(params);
	}

	@ResponseBody
	@RequestMapping(value="/search/ajax/list/owner", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboOwner(
			@RequestParam(value="dbName") String dbName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		
		return personalInfoMonitoringService.selectComboOwner(params);
	}

	@ResponseBody
	@RequestMapping(value="/search/ajax/add/exception", method=RequestMethod.POST)
	public int addPersonalInfoException(
			@RequestParam(value="exceptionList") String exceptionList
			) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		List<HashMap<String, Object>> paramsList = om.readValue(exceptionList, new TypeReference<List<HashMap<String, Object>>>(){});
		
		personalInfoMonitoringService.updatePersonalInfoException(paramsList);
		
		return 0;
	}
	
	@RequestMapping("/progress")
    public ModelAndView personalInfoProgress() {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoProgress");
        
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/progress/ajax/list/graph", method=RequestMethod.POST)
	public HashMap<String, Object> progressGraphList(
			@RequestParam(value="checkStartDate") String checkStartDate,
			@RequestParam(value="checkEndDate") String checkEndDate,
			@RequestParam(value="periodType") String periodType
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("checkStartDate", checkStartDate);
		params.put("checkEndDate", checkEndDate);
		
		List<HashMap<String, Object>> list = null;
		if(periodType.equals("day")) {
			list = personalInfoMonitoringService.selectPersonalInfoProgressGraphList(params);
		} else if(periodType.equals("month")) {
			list = personalInfoMonitoringService.selectPersonalInfoProgressGraphList2(params);
		}
		
		List<HashMap<String, Object>> emailList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, Object>> telnoList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, Object>> juminList = new ArrayList<HashMap<String,Object>>();
		
		Iterator<HashMap<String, Object>> itor = list.iterator();
		while(itor.hasNext()) {
			HashMap<String, Object> data = itor.next();
			String checkGubn = String.valueOf(data.get("CHECK_GUBN"));
			if(checkGubn.equals("Email")) {
				emailList.add(data);
			} else if(checkGubn.equals("Telno")) {
				telnoList.add(data);
			} else if(checkGubn.equals("Jumin")) {
				juminList.add(data);
			}
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("emailList", emailList);
		result.put("telnoList", telnoList);
		result.put("juminList", juminList);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/progress/ajax/list/detail", method=RequestMethod.POST)
	public HashMap<String, Object> detail(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="infoType") String infoType,
			@RequestParam(value="startCheckDate") String startCheckDate,
			@RequestParam(value="endCheckDate") String endCheckDate,
			@RequestParam(value="periodType") String periodType
			) {
		
		//System.out.println("[dbName] " + dbName + "  [infoType] " + infoType + "  [startCheckDate] " + startCheckDate + "  [endCheckDate] " + endCheckDate + "  [periodType] " + periodType);
		
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("infoType", infoType);
		params.put("startCheckDate", startCheckDate);
		params.put("endCheckDate", endCheckDate);
		params.put("periodType", periodType);
		
		List<HashMap<String, Object>> list = personalInfoMonitoringService.selectPersonalInfoDetailList(params);
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result.put("data", list);
		result.put("draw", 1);
		result.put("recordsTotal", list.size());
		result.put("recordsFiltered", list.size());
		
		return result;
	}
}
