package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.util.CastingJson;
import com.shinsegae.smon.util.NLogger;

@RequestMapping("/personalInfo/destroyManage")
@Controller
public class PersonalInfoDestroyManageController {

	@Autowired
	PersonalInfoDestroyManageService personalInfoDestroyManageService;
	
	@Autowired
    CastingJson json;
	
	@RequestMapping("")
    public ModelAndView personalInfoDictionary() {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoDestroyManage");
        
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list", method=RequestMethod.POST)
	public HashMap<String, Object> list(
			@RequestParam(value="rows") int rows,
			@RequestParam(value="page") int page,
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="tableName") String tableName
			) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("dbName", dbName);
		params.put("owner", owner);
		
		if(!"".equals(tableName) && tableName != null){
			tableName = tableName.toUpperCase();
		}
		params.put("tableName", tableName);
		
		int start = (rows * (page-1)) + 1;
        int end = page * rows;
        params.put("start", start);
        params.put("end", end);
		
		List<HashMap<String, Object>> list = personalInfoDestroyManageService.selectDestroyTableList2(params);
		
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
	@RequestMapping(value="/search/ajax/list/dbName", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboDBName() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		return personalInfoDestroyManageService.selectComboDBName(params);
	}

	@ResponseBody
	@RequestMapping(value="/search/ajax/list/owner", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboOwner(
			@RequestParam(value="dbName") String dbName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		
		return personalInfoDestroyManageService.selectComboOwner(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list/tbName", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboTbname(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		params.put("owner", owner);
		return personalInfoDestroyManageService.selectComboTbname(params);
	}
	
	@RequestMapping(value="/search/ajax/list/save", method=RequestMethod.POST)
	public @ResponseBody int saveRowAddedInfo(@RequestParam HashMap<String, Object> param) {
		
		int result =0;
		
		try{
			result = personalInfoDestroyManageService.updateRowAddedInfo(param);
        }catch(Exception e){
			System.out.println(e.toString());
			//logger.error(e.toString());
			NLogger.error(e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/search/ajax/list/delete", method=RequestMethod.POST)
	public int deleteRowAddedInfo(
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner,
			@RequestParam(value="tableName") String tableName,
			@RequestParam(value="sqlData") String sqlData
			) {
		
		if((!"".equals(dbName) && dbName != null) && (!"".equals(owner) && owner != null) && (!"".equals(tableName) && tableName != null) && (!"".equals(sqlData) && sqlData != null)){
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("dbName", dbName);
			params.put("owner", owner);
			params.put("tableName", tableName);
			params.put("sqlData", sqlData);
			
			personalInfoDestroyManageService.deleteRowAddedInfo(params);
		}
		
		
		
		return 0;
	}

}
