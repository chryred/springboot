package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MtrCommonService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectTeamComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectTeamComboList(params);
	}
	
	public List<HashMap<String, Object>> selectSystemComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectSystemComboList(params);
	}

	public List<HashMap<String, Object>> selectOwnerComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectOwnerComboList(params);
	}
	
	public List<HashMap<String, Object>> selectSaveTypeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectSaveTypeComboList(params);
	}
	
	public List<HashMap<String, Object>> selectObjectTypeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectObjectTypeComboList(params);
	}
	
	public List<HashMap<String, Object>> selectPersonalInfoTypeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectPersonalInfoTypeComboList(params);
	}
	
	public List<HashMap<String, Object>> selectDataTypeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectDataTypeComboList(params);
	}
	
	public List<HashMap<String, Object>> selectActionTypeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectActionTypeComboList(params);
	}
	
	public List<HashMap<String, Object>> selectWorkComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectWorkComboList(params);
	}
	
	public List<HashMap<String, Object>> selectEffectComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectEffectComboList(params);
	}
	
	public List<HashMap<String, Object>> selectEffectRangeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectEffectRangeComboList(params);
	}
	
	public List<HashMap<String, Object>> selectTargetSystemComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectTargetSystemComboList(params);
	}
	
	public List<HashMap<String, Object>> selectStatusComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectStatusComboList(params);
	}
	
	public List<HashMap<String, Object>> selectDefectGradeComboList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(MtrCommonMapper.class).selectDefectGradeComboList(params);
	}
}
