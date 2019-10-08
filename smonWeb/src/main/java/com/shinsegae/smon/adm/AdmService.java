package com.shinsegae.smon.adm;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.model.adm.DsBatchVO;

/*******************************
 * 배치 서비스
 * @author 153712 김성일
 * @since 2017.04.29
 *******************************/
@Service
public class AdmService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public int getCountlistUser(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getCountlistUser(paramCondition);
	}
	
	public int getCountlistgroup(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getCountlistgroup(paramCondition);
	}
	/*******************************
	 *  
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/
	public List<DsBatchVO> getSimplelistUser(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getSimplelistUser(paramCondition);
	}

	public int getCountlistUser2(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getCountlistUser(paramCondition);
	} 
	/*******************************
	 *  
	 * @param DsBatchVO
	 * @return List<DsBatchVO>
	 * @throws Exception
	 *******************************/	
	public List<DsBatchVO> getDsMenuList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getDsMenuList(paramCondition);
	}
	
	public  HashMap<String, Object> getMenuDetail(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getMenuDetail(paramCondition);
	}

	public void updateMenuList(DsBatchVO paramCondition)throws Exception {
		sqlSessionTemplatePrimary.getMapper(AdmMapper.class).updateMenuList(paramCondition);
	}
	
	/*******************************
	 *  권한그룹조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/
	public List<DsBatchVO> getlistAuthGruop(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getlistAuthGruop(paramCondition);
	}
	
	public  HashMap<String, Object> selectauthGroup(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).selectauthGroup(paramCondition);
	}
	public int getCountlistGruop(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getCountlistGruop(paramCondition);
	}

	public void listAuthGruopSave(DsBatchVO paramCondition)throws Exception {
	    sqlSessionTemplatePrimary.getMapper(AdmMapper.class).listAuthGruopSave(paramCondition);
	}
	
	public int listAuthGruopDelete(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).listAuthGruopDelete(paramCondition);
	}	 
	
	public int deleteMenuJson(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).deleteMenuJson(paramCondition);
	}	
	
	public List<DsBatchVO> selectGroupId(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).selectGroupId(paramCondition);
	}
	public List<DsBatchVO> listMenuGruopJson(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).listMenuGruopJson(paramCondition);
	}
	public int listMenuIdcheck(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).listMenuIdcheck(paramCondition);
	} 
	
	public List<HashMap<String, Object>> selectNavigation(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AdmMapper.class).selectNavigation(paramCondition);
	}
	
	public void listMenuGruopSave(DsBatchVO paramCondition)throws Exception {
	    sqlSessionTemplatePrimary.getMapper(AdmMapper.class).listMenuGruopSave(paramCondition);
	}

	/*******************************
	 * @param @RequestBody 
	 * @return HashMap<String, Object>
	 * @throws Exception
	 *******************************/
	public HashMap<String, Object> getlistUserUpdate(DsBatchVO paramCondition) throws Exception {
	 HashMap<String, Object> returnMap = new HashMap<String, Object>();
	 //System.out.println("##################### getlistUserUpdate_START");
	 sqlSessionTemplatePrimary.getMapper(AdmMapper.class).getListUserUpdate(paramCondition);
	 returnMap.put("chkResult", "OK");
	 returnMap.put("errorMsg", "");
	 
	 return returnMap;
	} 

}
