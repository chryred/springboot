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

@RequestMapping("/personalInfo/dictionary")
@Controller
public class PersonalInfoDictionaryController {
	
	@Autowired
	PersonalInfoDictionaryService personalInfoDictionaryService;
	
	@Autowired
    CastingJson json;
	
	@RequestMapping("")
    public ModelAndView personalInfoDictionary() {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoDictionary");
        
        return mv;
    }
	
	@RequestMapping("/search")
    public ModelAndView personalInfoSearch() {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoSearch");
        
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list", method=RequestMethod.POST)
	public HashMap<String, Object> list(
			@RequestParam(value="rows") int rows,
			@RequestParam(value="page") int page,
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="type") String type,
			@RequestParam(value="personalInfoFlag") String personalInfoFlag,
			@RequestParam(value="status") String status,
			@RequestParam(value="objectName") String objectName
			) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("owner", owner);
		params.put("type", type);
		params.put("personalInfoFlag", personalInfoFlag);
		params.put("status", status);
		params.put("objectName", objectName);
		
		int start = (rows * (page-1)) + 1;
        int end = page * rows;
        params.put("start", start);
        params.put("end", end);
		
		List<HashMap<String, Object>> list = personalInfoDictionaryService.selectDictionaryTableList(params);
		
		int records = list.size() > 0 ? Integer.parseInt(String.valueOf(list.get(0).get("TOTCNT"))) : 0;
    	int total = records%rows == 0 ? records/rows : records/rows + 1;
		
		HashMap<String, Object> jqgridResult = new HashMap<String, Object>();
		jqgridResult.put("page", page);
		jqgridResult.put("records", records);
		jqgridResult.put("total", total);
		jqgridResult.put("rows", list);

		return jqgridResult;
	}
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list2", method=RequestMethod.POST)
	public HashMap<String, Object> list2(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="tableName") String tableName
			) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("owner", owner);
		params.put("tableName", tableName);
		
		List<HashMap<String, Object>> list = personalInfoDictionaryService.selectDictionaryColList(params);
		
		HashMap<String, Object> jqgridResult = new HashMap<String, Object>();
		jqgridResult.put("page", 1);
		jqgridResult.put("records", list.size());
		jqgridResult.put("total", 1);
		jqgridResult.put("rows", list);

		return jqgridResult;
	}
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list/dbName", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboDBName() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		return personalInfoDictionaryService.selectComboDBName(params);
	}

	@ResponseBody
	@RequestMapping(value="/search/ajax/list/owner", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboOwner(
			@RequestParam(value="dbName") String dbName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		
		return personalInfoDictionaryService.selectComboOwner(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/column/save", method=RequestMethod.POST)
	public int saveColumnAddedInfo(
			@RequestParam(value="selColumList") String selColumList
			) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		
		List<HashMap<String, Object>> paramsList = om.readValue(selColumList, new TypeReference<List<HashMap<String, Object>>>(){});
		
		personalInfoDictionaryService.updateColumnAddedInfo(paramsList);
		
		return 0;
	}

	@ResponseBody
	@RequestMapping(value="/search/ajax/add/exception", method=RequestMethod.POST)
	public int addPersonalInfoException(
			@RequestParam(value="exceptionList") String exceptionList
			) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		List<HashMap<String, Object>> paramsList = om.readValue(exceptionList, new TypeReference<List<HashMap<String, Object>>>(){});
		
		personalInfoDictionaryService.insertPersonalInfoException(paramsList);
		
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
			list = personalInfoDictionaryService.selectPersonalInfoProgressGraphList(params);
		} else if(periodType.equals("month")) {
			list = personalInfoDictionaryService.selectPersonalInfoProgressGraphList2(params);
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
		
		List<HashMap<String, Object>> list = personalInfoDictionaryService.selectPersonalInfoDetailList(params);
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result.put("data", list);
		result.put("draw", 1);
		result.put("recordsTotal", list.size());
		result.put("recordsFiltered", list.size());
		
		return result;
	}
}
