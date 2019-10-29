package com.shinsegae.smon.online;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface WikiMapper {

	public List<HashMap<String, Object>> selectList(HashMap<String, Object> params);

	public HashMap<String, Object> selectWiki(HashMap<String, Object> params);

	public void addWiki(HashMap<String, Object> params);

	public void editWiki(HashMap<String, Object> params);

	public String selectWikiSeq();

}