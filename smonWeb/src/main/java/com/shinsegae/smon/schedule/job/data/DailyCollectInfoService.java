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
import com.shinsegae.smon.util.RequestMap;

@Service
public class DailyCollectInfoService {
	
	@Autowired
	@Qualifier("sqlSessionTemplateOnprem")
	SqlSessionTemplate sqlSessionTemplateOnprem;
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	@Autowired
	@Qualifier("sqlSessionTemplateAuroraBatch")
	SqlSessionTemplate sqlSessionTemplateAuroraBatch;
	
	public void firstDailyCollectInfo(RequestMap param) throws Exception {
		NLogger.info("============ firstDailyCollectInfo Start =============");
		NLogger.info(Thread.currentThread().getName(), " : ", param);
			
		// 테이블 정보 조회
		List<Map<String, Object>> lDailyCollectInfo = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDailyCollectInfoByThread(param);
		
		for(Map<String, Object> mDailyCollectInfo : lDailyCollectInfo) {
			String strTableName = (String)mDailyCollectInfo.get("TABLE_NAME");
			String strCols = (String)mDailyCollectInfo.get("COLS");
			String strPrCols = (String)mDailyCollectInfo.get("PR_COLS");
			String strTrtmCnt = mDailyCollectInfo.get("TRTM_CNT").toString();
			
			mDailyCollectInfo.put("QRY", GenerateDynamicQuery.selectCountQry(strTableName));
				
			int nMaxCnt = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDynamicCountInfo(mDailyCollectInfo);

			NLogger.info("nMaxCnt : ", nMaxCnt);
			
			// 기준 데이터 삭제처리
			sqlSessionTemplateAuroraBatch.getMapper(DailyCollectInfoMapper.class).deleteDynamicBaseInfo("TRUNCATE TABLE " + strTableName);
			sqlSessionTemplateAuroraBatch.flushStatements();
			
			// 데이터 조회 쿼리 생성
			mDailyCollectInfo.put("QRY", GenerateDynamicQuery.selectPaginQry(strTableName, strCols));
			int nStrtNum = 0, nEndNum = 0;
			int nCntPerPaging = Integer.parseInt(strTrtmCnt);
			int nPagingCnt = (int)Math.ceil((double)nMaxCnt/nCntPerPaging);
			
			for(int nIdx = 0; nIdx < nPagingCnt; nIdx ++) {
				nEndNum = nStrtNum + nCntPerPaging;
				
				mDailyCollectInfo.put("STRT_NUM", nStrtNum);
				mDailyCollectInfo.put("END_NUM", nEndNum);
				
				
				List<Map<String, Object>> lTableData = null;
				if("TCKLOQ".equals(strTableName)) {
					lTableData = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchTckLoqCollectInfo(mDailyCollectInfo);
				} else {
					lTableData = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDynamicCollectInfo(mDailyCollectInfo);
				}
				
				//NLogger.info("value_string : ", new String((byte[])lTableData.get(0).get("VALUE_STRING")));
				
				nStrtNum = nStrtNum + nCntPerPaging;
				
				Map<String, Object> mTableData = new LinkedHashMap<String, Object>();
				// 데이터 머지 쿼리 생성
				mTableData.put("QRY", GenerateDynamicQuery.insertIgnoreQrys(strTableName, strCols, strPrCols, lTableData, mTableData));
				sqlSessionTemplateAuroraBatch.getMapper(DailyCollectInfoMapper.class).insertIgnoreDynamicBaseInfo(mTableData);
				sqlSessionTemplateAuroraBatch.flushStatements();
				
				NLogger.info("commit : ", Thread.currentThread().getName());
//				lTableData.clear();
			}
		}
		
		NLogger.info("============ firstDailyCollectInfo End =============");
	}
	
	public void syncDailyCollectInfo() throws Exception {
		NLogger.info("============ syncDailyCollectInfo Start =============");
		NLogger.info(Thread.currentThread().getName());
			
		// 테이블 정보 조회
		List<Map<String, Object>> lDailyCollectInfo = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDailyCollectInfo();
		
		for(Map<String, Object> mDailyCollectInfo : lDailyCollectInfo) {
			String strTableName = (String)mDailyCollectInfo.get("TABLE_NAME");
			String strCols = (String)mDailyCollectInfo.get("COLS");
			String strPrCols = (String)mDailyCollectInfo.get("PR_COLS");
			String strColNm = (String)mDailyCollectInfo.get("COL_NM");
			String strTrtmCnt = mDailyCollectInfo.get("TRTM_CNT").toString();
			
			// MAX값 구하기(aurora)
			mDailyCollectInfo.put("QRY", GenerateDynamicQuery.selectMaxValue(strTableName, strColNm));
			Map<String, Object> mMaxValue = sqlSessionTemplatePrimary.getMapper(DailyCollectInfoMapper.class).searchDynamicMaxValue(mDailyCollectInfo);
			
			if(mMaxValue == null) {
				NLogger.info("기존 데이터가 존재하지 않습니다. 테이블명 : ", strTableName);
				continue;
			}
			mDailyCollectInfo.putAll(mMaxValue);
			
			// MAX값을 통한 건수 구하기
			mDailyCollectInfo.put("QRY", GenerateDynamicQuery.selectMaxCountQry(strTableName, strColNm));
			int nMaxCnt = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDynamicCountInfo(mDailyCollectInfo);
			NLogger.info("nMaxCnt : ", nMaxCnt);
			
			if(nMaxCnt == 0) {
				NLogger.info("변경된 값 미존재 : ", strTableName);
				continue;
			}
			
			// 데이터 조회 쿼리 생성
			mDailyCollectInfo.put("QRY", GenerateDynamicQuery.selectMaxValuePaginQry(strTableName, strCols, strColNm));
			int nStrtNum = 0, nEndNum = 0;
			int nCntPerPaging = Integer.parseInt(strTrtmCnt);
			int nPagingCnt = (int)Math.ceil((double)nMaxCnt/nCntPerPaging);
			
			for(int nIdx = 0; nIdx < nPagingCnt; nIdx ++) {
				nEndNum = nStrtNum + nCntPerPaging;
				
				mDailyCollectInfo.put("STRT_NUM", nStrtNum);
				mDailyCollectInfo.put("END_NUM", nEndNum);
				
				List<Map<String, Object>> lTableData = null;
				
				if("TCKLOQ".equals(strTableName)) {
					lTableData = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDailyTckLoqCollectInfo(mDailyCollectInfo);
				} else {
					lTableData = sqlSessionTemplateOnprem.getMapper(DailyCollectInfoMapper.class).searchDynamicCollectInfo(mDailyCollectInfo);
				}
				
				nStrtNum = nStrtNum + nCntPerPaging;
				
				Map<String, Object> mTableData = new LinkedHashMap<String, Object>();
				// 데이터 머지 쿼리 생성
				mTableData.put("QRY", GenerateDynamicQuery.replaceQrys(strTableName, strCols, strPrCols, lTableData, mTableData));
				sqlSessionTemplateAuroraBatch.getMapper(DailyCollectInfoMapper.class).insertIgnoreDynamicBaseInfo(mTableData);
				sqlSessionTemplateAuroraBatch.flushStatements();
				
				
				lTableData.clear();
			}
		}
		
		NLogger.info("============ syncDailyCollectInfo End =============");
	}
}
