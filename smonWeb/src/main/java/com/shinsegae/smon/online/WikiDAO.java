package com.shinsegae.smon.online;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class WikiDAO {

	@Autowired
	@Qualifier("sqlSessionTemplateSOSS")
	private SqlSessionTemplate session;

	public List<HashMap<String, Object>> selectList(HashMap<String, Object> params) {
		return session.selectList("WikiQuery.selectList", params);
	}

	public HashMap<String, Object> selectWiki(HashMap<String, Object> params) {
		return session.selectOne("WikiQuery.selectWiki", params);
	}

	public void addWiki(HashMap<String, Object> params) {
		session.insert("WikiQuery.addWiki", params);
	}

	public void editWiki(HashMap<String, Object> params) {
		session.update("WikiQuery.editWiki", params);
	}

	public String selectSequence() {
		// TODO Auto-generated method stub
		return session.selectOne("WikiQuery.selectWikiSeq");
	}

//	public List<HashMap<String, Object>> selectSystemComboList(HashMap<String, Object> params) {
//		return session.selectList("dbMonitoringCommon.selectSystemComboList", params);
//	}
//	
//	public List<HashMap<String, Object>> selectSaveTypeComboList(HashMap<String, Object> params) {
//		return session.selectList("dbMonitoringCommon.selectSaveTypeComboList", params);
//	}
//	
//	public List<HashMap<String, Object>> selectObjectTypeComboList(HashMap<String, Object> params) {
//		return session.selectList("dbMonitoringCommon.selectObjectTypeComboList", params);
//	}
//	
//	public List<HashMap<String, Object>> selectPersonalInfoTypeComboList(HashMap<String, Object> params) {
//		return session.selectList("dbMonitoringCommon.selectPersonalInfoTypeComboList", params);
//	}
//	
//	public List<HashMap<String, Object>> selectDataTypeComboList(HashMap<String, Object> params) {
//		return session.selectList("dbMonitoringCommon.selectDataTypeComboList", params);
//	}
//	
//	public List<HashMap<String, Object>> selectActionTypeComboList(HashMap<String, Object> params) {
//		return session.selectList("dbMonitoringCommon.selectActionTypeComboList", params);
//	}

}