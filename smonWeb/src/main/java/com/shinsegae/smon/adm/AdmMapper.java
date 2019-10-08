package com.shinsegae.smon.adm;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.adm.DsBatchVO;

@Mapper
public interface AdmMapper {
	 
	int getCountlistUser(DsBatchVO paramCondition) throws Exception;
	
	int getCountlistgroup(DsBatchVO paramCondition) throws Exception;
	
	List<DsBatchVO> getSimplelistUser(DsBatchVO paramCondition) throws Exception;

	List<DsBatchVO> getDsMenuList(DsBatchVO paramCondition) throws Exception;
	
	HashMap<String, Object> getMenuDetail(DsBatchVO paramCondition) throws Exception;

	void updateMenuList(DsBatchVO paramCondition) throws Exception;
	
	List<DsBatchVO> getlistAuthGruop(DsBatchVO paramCondition) throws Exception;

	HashMap<String, Object> selectauthGroup(DsBatchVO paramCondition) throws Exception;
	
	int getCountlistGruop(DsBatchVO paramCondition) throws Exception;
	
	void listAuthGruopSave(DsBatchVO paramCondition) throws Exception;
	
	int listAuthGruopDelete(DsBatchVO paramCondition) throws Exception;
	
	int deleteMenuJson(DsBatchVO paramCondition) throws Exception;
	
	List<DsBatchVO> selectGroupId(DsBatchVO paramCondition) throws Exception;
	
	List<DsBatchVO> listMenuGruopJson(DsBatchVO paramCondition) throws Exception;
	
	int listMenuIdcheck(DsBatchVO paramCondition) throws Exception;

	public List<HashMap<String, Object>> selectNavigation(DsBatchVO paramCondition) throws Exception;
	
	public void listMenuGruopSave(DsBatchVO paramCondition) throws Exception;
	
	public void getListUserUpdate(DsBatchVO paramCondition) throws Exception;
	
}
