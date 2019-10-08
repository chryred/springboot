package com.shinsegae.smon.database.tablespace;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableSpaceDbMonitoringService {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectDbDictionaryInfoDashboard(HashMap<String, Object> map) throws Exception {
        return sqlSessionTemplatePrimary.getMapper(TableSpaceDbMonitoringMapper.class).selectUndoTableSpaceState(map);
    }

    public HashMap<String, Object> checkDbInfo(List<HashMap<String, Object>> dbDictionaryList) throws Exception {
        
        Iterator<HashMap<String, Object>> iter = dbDictionaryList.iterator();
        while(iter.hasNext()){
            HashMap<String, Object> paramMap = iter.next();
            sqlSessionTemplatePrimary.getMapper(TableSpaceDbMonitoringMapper.class).insertUndoTablespaceHistoryTable(paramMap);
        }
        
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        
        Boolean alertFlag = false;
        
        returnMap.put("alertFlag", alertFlag);
        
        return returnMap;
    }

    public List<HashMap<String, Object>> selectDbSpaceState(HashMap<String, String> map) throws Exception{
        return sqlSessionTemplatePrimary.getMapper(TableSpaceDbMonitoringMapper.class).selectTablespaceState(map);
    }

    public List<HashMap<String, Object>> selectDbDictionaryInfo(HashMap<String, Object> paramMap) throws Exception {
        return sqlSessionTemplatePrimary.getMapper(TableSpaceDbMonitoringMapper.class).selectTablespaceSizeTck(paramMap);
    }
    
    public int selectDbDictionaryTotalCnt(HashMap<String, Object> paramMap) {
        return sqlSessionTemplatePrimary.getMapper(TableSpaceDbMonitoringMapper.class).selectTablespaceSizeTckTotal(paramMap);
    }
}
