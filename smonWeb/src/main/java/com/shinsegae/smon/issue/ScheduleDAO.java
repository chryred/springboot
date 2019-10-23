package com.shinsegae.smon.issue;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDAO {

	public List<HashMap<String, Object>> searchComboData(SqlSessionTemplate session, HashMap<String, String> map)
			throws Exception {

		return session.selectList("main.searchComboData", map);
	}

	public List searchProjectGridData(SqlSessionTemplate session, HashMap<String, String> param) throws Exception {

		return session.selectList("main.searchProjectGridData", param);
	}

	public List searchAllData(SqlSessionTemplate session, HashMap<String, String> param) throws Exception {

		return session.selectList("main.searchAllData", param);
	}

	public void updateRow(SqlSessionTemplate session, HashMap<String, String> param) throws Exception {

		session.update("main.updateRow", param);
	}

}