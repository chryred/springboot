package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.util.CastingJson;

@RequestMapping("/common")
@Controller
public class MtrCommonController {
	
	@Autowired
	MtrCommonService mtrCommonService;
	
	@Autowired
    CastingJson json;
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/team", method=RequestMethod.POST)
	public List<HashMap<String, Object>> teamComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectTeamComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/system", method=RequestMethod.POST)
	public List<HashMap<String, Object>> systemComboList(
				@RequestParam(value="teamCode") String groupCode
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("groupCode", groupCode);

		return mtrCommonService.selectSystemComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/owner", method=RequestMethod.POST)
	public List<HashMap<String, Object>> ownerComboList(
				@RequestParam(value="dbName") String dbName
			) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("dbName", dbName);

		return mtrCommonService.selectOwnerComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/saveType", method=RequestMethod.POST)
	public List<HashMap<String, Object>> saveTypeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectSaveTypeComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/objectType", method=RequestMethod.POST)
	public List<HashMap<String, Object>> objectTypeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectObjectTypeComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/personalInfoType", method=RequestMethod.POST)
	public List<HashMap<String, Object>> personalInfoTypeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectPersonalInfoTypeComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/dataType", method=RequestMethod.POST)
	public List<HashMap<String, Object>> dataTypeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectDataTypeComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/actionType", method=RequestMethod.POST)
	public List<HashMap<String, Object>> actionTypeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectActionTypeComboList(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/combo/ajax/work", method=RequestMethod.POST)
	public List<HashMap<String, Object>> workComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectWorkComboList(params);
	}
	
	// effectComboList
	@ResponseBody
	@RequestMapping(value="/combo/ajax/effect", method=RequestMethod.POST)
	public List<HashMap<String, Object>> effectComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		//System.out.println("combo/ajax/effect");
		return mtrCommonService.selectEffectComboList(params);
	}
	
	// effectRangeComboList
	@ResponseBody
	@RequestMapping(value="/combo/ajax/effectRange", method=RequestMethod.POST)
	public List<HashMap<String, Object>> effectRangeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectEffectRangeComboList(params);
	}
	
	// targetSystemComboList
	@ResponseBody
	@RequestMapping(value="/combo/ajax/targetSystem", method=RequestMethod.POST)
	public List<HashMap<String, Object>> targetSystemComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectTargetSystemComboList(params);
	}
	
	// statusComboList
	@ResponseBody
	@RequestMapping(value="/combo/ajax/status", method=RequestMethod.POST)
	public List<HashMap<String, Object>> statusComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectStatusComboList(params);
	}
	
	// defectGradeComboList
	@ResponseBody
	@RequestMapping(value="/combo/ajax/defectGrade", method=RequestMethod.POST)
	public List<HashMap<String, Object>> defectGradeComboList() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		return mtrCommonService.selectDefectGradeComboList(params);
	}
}

