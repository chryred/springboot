package com.shinsegae.smon.cpu;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CpuUsageDbMonitoringService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
    
    
    public HashMap<String, Object> checkDbInfo(List<HashMap<String, Object>> dbDictionaryList) throws Exception {
        
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        Boolean alertFlag = false;
        
        returnMap.put("alertFlag", alertFlag);
        
        return returnMap;
    }
    
    public List<HashMap<String, Object>> selectDbDictionaryInfo(HashMap<String, String> map) throws Exception {
        
        return sqlSessionTemplatePrimary.getMapper(CpuUsageDbMonitoringMapper.class).selectCpuUsageList(map);
    }

	public List<HashMap<String, Object>> selectStatusOfInterlocking() throws Exception{
		// TODO Auto-generated method stub
		return sqlSessionTemplatePrimary.getMapper(CpuUsageDbMonitoringMapper.class).selectStatusOfInterlocking();
	}

	public List<HashMap<String, Object>> sslInfoDataTables() throws Exception{
		// TODO Auto-generated method stub
		return sqlSessionTemplatePrimary.getMapper(CpuUsageDbMonitoringMapper.class).sslInfoDataTables();
	}
}
