package com.shinsegae.smon.util;

import java.util.Map;

import org.springframework.util.StringUtils;

public class GenerateDynamicQuery {
	
	public static String selectQry(String strTableName, String strCols) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT ")
		  .append(strCols)
		  .append(" FROM ")
		  .append(strTableName);
		
		return sb.toString();
	}
	
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
	
}
