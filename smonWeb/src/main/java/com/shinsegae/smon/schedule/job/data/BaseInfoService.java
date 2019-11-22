package com.shinsegae.smon.schedule.job.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.util.GenerateDynamicQuery;
import com.shinsegae.smon.util.NLogger;

@Service
public class BaseInfoService {
	
	@Autowired
	@Qualifier("sqlSessionTemplateOnprem")
	SqlSessionTemplate sqlSessionTemplateOnprem;
	
	@Autowired
	@Qualifier("sqlSessionTemplateAuroraBatch")
	SqlSessionTemplate sqlSessionTemplateAuroraBatch;
	
	public void syncBaseInfo() throws Exception {
		NLogger.debug("============ syncBaseInfo Start =============");
		NLogger.debug(Thread.currentThread().getName());
		
		// 기준 정보 테이블 정보 조회
		List<Map<String, Object>> lBaseInfo = sqlSessionTemplateOnprem.getMapper(BaseInfoMapper.class).searchBaseInfo();
		
		for(Map<String, Object> mBaseInfo : lBaseInfo) {
			String strTableName = (String)mBaseInfo.get("TABLE_NAME");
			String strCols = (String)mBaseInfo.get("COLS");
			String strPrCols = (String)mBaseInfo.get("PR_COLS");
			String strSelQry = GenerateDynamicQuery.selectQry(strTableName, strCols);
			
			// 데이터 조회 쿼리 생성
			mBaseInfo.put("QRY", strSelQry);
			List<LinkedHashMap<String, Object>> lTableData = sqlSessionTemplateOnprem.getMapper(BaseInfoMapper.class).searchDynamicBaseInfo(mBaseInfo);
			
			// 기준 데이터 삭제처리
			sqlSessionTemplateAuroraBatch.getMapper(BaseInfoMapper.class).deleteDynamicBaseInfo("TRUNCATE TABLE " + strTableName);
			sqlSessionTemplateAuroraBatch.flushStatements();
			
			int nCnt = 0;
			for(Map<String, Object> mTableData : lTableData) {
				
				String strMergeQry = GenerateDynamicQuery.mergeQry(strTableName, strCols, strPrCols, mTableData);
				// 데이터 머지 쿼리 생성
				mTableData.put("QRY", strMergeQry);
				
				sqlSessionTemplateAuroraBatch.getMapper(BaseInfoMapper.class).mergeDynamicBaseInfo(mTableData);
				nCnt++;
				
				if(nCnt % 5000 == 0) {
					sqlSessionTemplateAuroraBatch.flushStatements();	
				}
				
			}
			sqlSessionTemplateAuroraBatch.flushStatements();
		}

		NLogger.debug("============ syncBaseInfo End =============");
	}
}
