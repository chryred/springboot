package com.shinsegae.smon.issue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	//return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getCountlistUser(paramCondition);
	
	public List<HashMap<String, Object>> searchComboData(HashMap<String, String> map) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(ScheduleMapper.class).searchComboData(map);
	}

	public List<HashMap<String, Object>> searchProjectGridData(HashMap<String, String> map) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(ScheduleMapper.class).searchProjectGridData(map);
	}

	public List<HashMap<String, Object>> searchAllData(HashMap<String, String> map) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(ScheduleMapper.class).searchAllData(map);
	}

	public void updateGrid(String gridData) throws Exception {

		JSONArray jsonArray = JSONArray.fromObject(JSONSerializer.toJSON(gridData));

		Iterator iter = jsonArray.iterator();

		while (iter.hasNext()) {
			JSONObject jsonObject = (JSONObject) iter.next();

			if (jsonObject.get("CRUD").equals("U")) {
				// dao.updateRow(session, jsonObject);
			}
		}
	}

	public void updateGrid(HashMap<String, String> map) throws Exception {
		sqlSessionTemplatePrimary.getMapper(ScheduleMapper.class).updateRow(map);
	}

}