package com.shinsegae.smon.util;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class GenerateDynamicQuery {
	
	/**
	 * select절 조회 (where절 없이)
	 * @param strTableName
	 * @param strCols
	 * @return
	 */
	public static String selectQry(String strTableName, String strCols) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT ")
		  .append(strCols)
		  .append(" FROM ")
		  .append(strTableName);
		
		return sb.toString();
	}
	
	/**
	 * 전체 건수 조회 
	 * @param strTableName
	 * @param strCols
	 * @return
	 */
	public static String selectCountQry(String strTableName) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT COUNT(*) AS CNT")
		  .append(" FROM ")
		  .append(strTableName);
		
		return sb.toString();
	}
	
	/**
	 * select paging 처리
	 * @param strTableName
	 * @param strCols
	 * @return
	 */
	public static String selectPaginQry(String strTableName, String strCols) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *")
		  .append(" FROM (SELECT ROWNUM AS RNUM,")
		  .append(strCols)
		  .append(" FROM ")
		  .append(strTableName)
		  .append(" ORDER BY ROWID) A")
		  .append(" WHERE RNUM < #{END_NUM} AND RNUM >= #{STRT_NUM}")
		  ;
		
		return sb.toString();
	}
	
	/**
	 * 컬럼별 최종값 조회 하기
	 * @param strTableName
	 * @param strCols
	 * @return
	 */
	public static String selectMaxValue(String strTableName, String strColNm) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT MAX(").append(strColNm).append(") AS MAX_VALUE ")
		  .append(" FROM ")
		  .append(strTableName);
		
		return sb.toString();
	}
	
	/**
	 * MAX기준 보다 큰값에 대한 전체 건수 구하기(max값 포함)
	 * @param strTableName
	 * @param strCols
	 * @return
	 */
	public static String selectMaxCountQry(String strTableName, String strColNm) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT COUNT(*) AS CNT")
		  .append(" FROM ")
		  .append(strTableName)
		  .append(" WHERE ").append(strColNm).append(">=").append("#{MAX_VALUE}")
		  ;
		
		return sb.toString();
	}
	
	/**
	 * select Max value paging 처리
	 * @param strTableName
	 * @param strCols
	 * @return
	 */
	public static String selectMaxValuePaginQry(String strTableName, String strCols, String strColNm) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *")
		  .append(" FROM (SELECT ROWNUM AS RNUM,")
		  .append(strCols)
		  .append(" FROM ")
		  .append(strTableName)
		  .append(" WHERE ").append(strColNm).append(">=").append("#{MAX_VALUE}")
		  .append(" ORDER BY ROWID) A")
		  .append(" WHERE RNUM < #{END_NUM} AND RNUM >= #{STRT_NUM}")
		  ;
		
		return sb.toString();
	}
	
	/**
	 * insert문 처리(단, 중복 조건절의 경우는 제외 처리)
	 * @param strTableName
	 * @param strCols
	 * @param strPrCols
	 * @param mTableData
	 * @return
	 */
	public static String mergeQry(String strTableName, String strCols, String strPrCols, Map<String, Object> mTableData) {
		StringBuilder sb = new StringBuilder();
		String[] aryCols = strCols.split(",");
		// primary key 존재시 
		String[] aryPrCols = StringUtils.isEmpty(strPrCols) ? strCols.split(",") : strPrCols.split(",");
		// 예약어 컬럼 `로 감싸기
		String strAuroraCols = strCols.replaceAll("\\b(RANGE|OWNER|TABLE_NAME|SQL)\\b", "`$1`");
		
		sb.append("INSERT INTO ").append(strTableName).append("(").append(strAuroraCols).append(") ")
		  .append("SELECT ")
		;
		
		int nCnt = aryCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx++) {
			sb.append("#{").append(aryCols[nIdx]).append("}").append(" AS ").append("\"").append(aryCols[nIdx]).append("\"");
			
			// 컬럼 마지막의 경운 ,제외 처리
			if(nIdx != (nCnt - 1)) {
				sb.append(", ");
			}
		}
		
		sb.append(" FROM MYSQL_DUAL ").append("WHERE NOT EXISTS (")
		  .append("SELECT 1 ").append("FROM ").append(strTableName).append(" WHERE ");
		 
		nCnt = aryPrCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx++) {
			
			// 예약어 컬럼 감싸기
			sb.append(aryPrCols[nIdx].replaceAll("\\b(RANGE|OWNER|TABLE_NAME|SQL)\\b", "`$1`"));
			
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
	
	/**
	 * ignore문 처리
	 * @param strTableName
	 * @param strCols
	 * @param strPrCols
	 * @param mTableData
	 * @return
	 */
	public static String insertIgnoreQry(String strTableName, String strCols, String strPrCols) {
		StringBuilder sb = new StringBuilder();
		String[] aryCols = strCols.split(",");
		// primary key 존재시 
		String[] aryPrCols = StringUtils.isEmpty(strPrCols) ? strCols.split(",") : strPrCols.split(",");
		// 예약어 컬럼 `로 감싸기
		String strAuroraCols = strCols.replaceAll("\\b(RANGE|OWNER|TABLE_NAME|SQL)\\b", "`$1`");
		
		sb.append("INSERT IGNORE ").append(strTableName).append("(").append(strAuroraCols).append(") ")
		  .append("VALUES( ")
		;
		

		int nCnt = aryCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx++) {
			sb.append("#{").append(aryCols[nIdx]).append("}");
			
			// 컬럼 마지막의 경운 ,제외 처리
			if(nIdx != (nCnt - 1)) {
				sb.append(", ");
			}
		}
		
		sb.append(")");
		
		//NLogger.debug("replaceQry Qry : ", sb.toString());
		return sb.toString();
	}
	
	/**
	 * replace문 처리(단, 중복데이터는 최근데이터로 변경)
	 * @param strTableName
	 * @param strCols
	 * @param strPrCols
	 * @param mTableData
	 * @return
	 */
	public static String replaceQrys(String strTableName, String strCols, String strPrCols, List<Map<String, Object>> lTableDate, Map<String, Object> mRetTableData) {
		StringBuilder sb = new StringBuilder();
		String[] aryCols = strCols.split(",");
		// primary key 존재시 
		String[] aryPrCols = StringUtils.isEmpty(strPrCols) ? strCols.split(",") : strPrCols.split(",");
		// 예약어 컬럼 `로 감싸기
		String strAuroraCols = strCols.replaceAll("\\b(RANGE|OWNER|TABLE_NAME|SQL)\\b", "`$1`");
		
		sb.append("REPLACE INTO ").append(strTableName).append("(").append(strAuroraCols).append(") ")
		  .append("VALUES")
		;
		
		int nCnt = lTableDate.size();
		int nColCnt = aryCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx ++) {
			Map<String, Object> mTableData = lTableDate.get(nIdx);
			
			sb.append("(");
			for(int nColIdx = 0; nColIdx < nColCnt; nColIdx++) {
				String strChgCol = aryCols[nColIdx] + nIdx;
				
				mRetTableData.put(strChgCol, mTableData.get(aryCols[nColIdx]));
				
				sb.append("#{").append(strChgCol).append("}");
				
				// 컬럼 마지막의 경운 ,제외 처리
				if(nColIdx != (nColCnt - 1)) {
					sb.append(", ");
				}
			}
			sb.append(")");
			
			// 마지막 데이터인 경우 제외 처리
			if(nIdx != (nCnt - 1)) {
				sb.append(", ");
			}
		}


//		NLogger.debug("insertIgnoreQrys Qry : ", sb.toString());
		return sb.toString();
	}
	
	/**
	 * ignore문 처리(단, 중복데이터는 무시)
	 * @param strTableName
	 * @param strCols
	 * @param strPrCols
	 * @param mTableData
	 * @return
	 */
	public static String insertIgnoreQrys(String strTableName, String strCols, String strPrCols, List<Map<String, Object>> lTableDate, Map<String, Object> mRetTableData) {
		StringBuilder sb = new StringBuilder();
		String[] aryCols = strCols.split(",");
		// primary key 존재시 
		String[] aryPrCols = StringUtils.isEmpty(strPrCols) ? strCols.split(",") : strPrCols.split(",");
		// 예약어 컬럼 `로 감싸기
		String strAuroraCols = strCols.replaceAll("\\b(RANGE|OWNER|TABLE_NAME|SQL)\\b", "`$1`");
		
		sb.append("INSERT IGNORE ").append(strTableName).append("(").append(strAuroraCols).append(") ")
		  .append("VALUES")
		;
		
		int nCnt = lTableDate.size();
		int nColCnt = aryCols.length;
		for(int nIdx = 0; nIdx < nCnt; nIdx ++) {
			Map<String, Object> mTableData = lTableDate.get(nIdx);
			
			sb.append("(");
			for(int nColIdx = 0; nColIdx < nColCnt; nColIdx++) {
				String strChgCol = aryCols[nColIdx] + nIdx;
				
				mRetTableData.put(strChgCol, mTableData.get(aryCols[nColIdx]));
				
				sb.append("#{").append(strChgCol).append("}");
				
				// 컬럼 마지막의 경운 ,제외 처리
				if(nColIdx != (nColCnt - 1)) {
					sb.append(", ");
				}
			}
			sb.append(")");
			
			// 마지막 데이터인 경우 제외 처리
			if(nIdx != (nCnt - 1)) {
				sb.append(", ");
			}
		}


//		NLogger.debug("insertIgnoreQrys Qry : ", sb.toString());
		return sb.toString();
	}
}
