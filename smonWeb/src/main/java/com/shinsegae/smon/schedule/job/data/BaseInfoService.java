package com.shinsegae.smon.schedule.job.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shinsegae.smon.util.NLogger;

@Service
public class BaseInfoService {
	
	@Autowired
	@Qualifier("sqlSessionTemplateOnprem")
	SqlSessionTemplate sqlSessionTemplateOnprem;
	
	@Autowired
	@Qualifier("sqlSessionTemplateAuroraBatch")
	SqlSessionTemplate sqlSessionTemplateAuroraBatch;
	
	public void syncBaseInfo() {
		NLogger.debug("============ syncBaseInfo Start =============");
		NLogger.debug(Thread.currentThread().getName());
		
		// 기준 정보 테이블 정보 조회
		List<Map<String, Object>> lBaseInfo = sqlSessionTemplateOnprem.getMapper(BaseInfoMapper.class).searchBaseInfo();
		
		for(Map<String, Object> mBaseInfo : lBaseInfo) {
			String strTableName = (String)mBaseInfo.get("TABLE_NAME");
			String strCols = (String)mBaseInfo.get("COLS");
			String strPrCols = (String)mBaseInfo.get("PR_COLS");
			String strSelQry = selectQry(strTableName, strCols);
			
			// 데이터 조회 쿼리 생성
			mBaseInfo.put("QRY", strSelQry);
			List<LinkedHashMap<String, Object>> lTableData = sqlSessionTemplateOnprem.getMapper(BaseInfoMapper.class).searchDynamicBaseInfo(mBaseInfo);
			
			// 기준 데이터 삭제처리
			sqlSessionTemplateAuroraBatch.getMapper(BaseInfoMapper.class).deleteDynamicBaseInfo("TRUNCATE TABLE " + strTableName);
			sqlSessionTemplateAuroraBatch.flushStatements();
			
			int nCnt = 0;
			for(Map<String, Object> mTableData : lTableData) {
				
				String strMergeQry = mergeQry(strTableName, strCols, strPrCols, mTableData);
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
	
	public String selectQry(String strTableName, String strCols) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT ")
		  .append(strCols)
		  .append(" FROM ")
		  .append(strTableName);
		
		return sb.toString();
	}
	
	public String mergeQry(String strTableName, String strCols, String strPrCols, Map<String, Object> mTableData) {
		StringBuilder sb = new StringBuilder();
		String[] aryCols = strCols.split(",");
		// primary key 존재시 
		String[] aryPrCols = StringUtils.isEmpty(strPrCols) ? strCols.split(",") : strPrCols.split(",");
		
		sb.append("INSERT INTO ").append(strTableName).append("(").append(strCols).append(") ")
		  .append("SELECT ")
		;
		
		int nCnt = aryCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx++) {
			sb.append("#{").append(aryCols[nIdx]).append("}").append(" AS ").append(aryCols[nIdx]);
			
			// 컬럼 마지막의 경운 ,제외 처리
			if(nIdx != (nCnt - 1)) {
				sb.append(", ");
			}
		}
		
		sb.append(" FROM MYSQL_DUAL ").append("WHERE NOT EXISTS (")
		  .append("SELECT 1 ").append("FROM ").append(strTableName).append(" WHERE ");
		 
		nCnt = aryPrCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx++) {
			sb.append(aryPrCols[nIdx]);
			
			// 해당 데이터가 빈값일 경우엔 NULL 처리
			if(StringUtils.isEmpty(mTableData.get(aryPrCols[nIdx]))) {
				sb.append(" IS NULL");
			} else {
				sb.append("=").append("#{").append(aryPrCols[nIdx]).append("}");
			}
			
			// 컬럼 마지막의 경운 ,제외 처리
			if(nIdx != (nCnt - 1)) {
				sb.append(" AND ");
			}
		}
		
		sb.append(")");
		
//		NLogger.debug("merge Qry : ", sb.toString());
		return sb.toString();
	}
	
	
}
