package com.shinsegae.smon.privacy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.shinsegae.smon.util.NLogger;

@RequestMapping("/changeObjectMonitor")
@Controller
public class ChangeObjectMonitorController {

	@Autowired
	ChangeObjectMonitorService changeObjectMonitorService;
	
	@Autowired
    CastingJson json;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView listView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("changeObjectMonitor/listChangeObject");
        
        return mv;
    }
	
	
	@ResponseBody
	@RequestMapping(value="/ajax/list", method=RequestMethod.POST)
	public List<HashMap<String, Object>> selectList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="objectType") String objectType,
			@RequestParam(value="objectName") String objectName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		params.put("owner", owner);
		params.put("objectType", objectType);
		params.put("objectName", objectName);
		params.put("empNo", (String)request.getSession().getAttribute("id"));
		
		List<HashMap<String, Object>> list = changeObjectMonitorService.selectList(params);
		
		return list;
        
    }
	
	@ResponseBody
	@RequestMapping(value="/ajax/column/list", method=RequestMethod.POST)
	public List<HashMap<String, Object>> selectColumnList(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="tableName") String tableName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		params.put("owner", owner);
		params.put("tableName", tableName);
		
		List<HashMap<String, Object>> list = changeObjectMonitorService.selectColumnList(params);
		
		return list;
        
    }
	
	@ResponseBody
	@RequestMapping(value="/ajax/save", method=RequestMethod.POST)
	public String selectColumnList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="authedDatas") String authedDatas
			) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		List<HashMap<String, Object>> paramsList = om.readValue(authedDatas, new TypeReference<List<HashMap<String, Object>>>(){});
		changeObjectMonitorService.updateAuthedData(paramsList, (String)request.getSession().getAttribute("id"));
		
		return "1";
        
    }
}
