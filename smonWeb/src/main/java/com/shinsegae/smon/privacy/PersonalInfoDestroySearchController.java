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

@RequestMapping("/personalInfo/destroySearch")
@Controller
public class PersonalInfoDestroySearchController {
	
	@Autowired
	PersonalInfoDestroySearchService personalInfoDestroySearchService;

	@Autowired
    CastingJson json;
	
	
	@RequestMapping("")
    public ModelAndView personalInfoDestroy() {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/personalInfo/personalInfoDestroySearch");
        
        return mv;
    }

	@ResponseBody
	@RequestMapping(value="/search/ajax/list", method=RequestMethod.POST)
	public HashMap<String, Object> list(
			@RequestParam(value="rows") int rows,
			@RequestParam(value="page") int page,
			//@RequestParam(value="ifYmd") String ifYmd,
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner
			) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		//params.put("ifYmd", ifYmd);
		params.put("dbName", dbName);
		params.put("owner", owner);
		int start = (rows * (page-1)) + 1;
        int end = page * rows;
        params.put("start", start);
        params.put("end", end);
		
		List<HashMap<String, Object>> list = personalInfoDestroySearchService.selectDestoryTableList2(params);
		
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
			//@RequestParam(value="ifYmd") String ifYmd,
			@RequestParam(value="dbName") String dbName,
			@RequestParam(value="owner") String owner
			) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();

		//params.put("ifYmd", ifYmd);
		params.put("dbName", dbName);
		params.put("owner", owner);
		
		List<HashMap<String, Object>> list = personalInfoDestroySearchService.selectDestoryColList(params);
		
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
		return personalInfoDestroySearchService.selectComboDBName(params);
	}

	@ResponseBody
	@RequestMapping(value="/search/ajax/list/owner", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboOwner(
			@RequestParam(value="dbName") String dbName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);
		
		return personalInfoDestroySearchService.selectComboOwner(params);
	}
	@ResponseBody
	@RequestMapping(value="/search/ajax/list/TableName", method=RequestMethod.POST)
	public List<HashMap<String, Object>> listComboTableName(
			@RequestParam(value="owner") String owner
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("owner", owner);
		
		return personalInfoDestroySearchService.selectComboTableName(params);
	}

}
