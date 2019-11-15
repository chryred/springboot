package com.shinsegae.smon.util;

import com.cubeone.CubeOneAPI;

public class CubeoneAPIUtil {
	

	public static String cohashsalt(String msg) {
		byte[] errbyte = new byte[8];
		return  CubeOneAPI.cohashsalt(msg, "", 256, errbyte);
	}
	
	
	
	/**
	 * 큐브원 AP 암호화 함수
	 * @param strMsg 암호화할 문자열
	 * @param nCrudLog 로그 정보(10:select, 11:insert, 12(update), 13(delete)
	 * @param strItemCd 아이템코드 (PWD, CARD, ...)
	 * @param strTableNm (TABLE명)
	 * @param strColNm (컬럼명)
	 * @return String 암호화된 문자열
	 */
	public static String coencbyte(String strMsg, int nCrudLog, String strItemCd, String strTableNm, String strColNm) {
		byte[] errbyte = new byte[8];
		return  CubeOneAPI.coencbyte(strMsg.getBytes(), strItemCd, nCrudLog ,strTableNm, strColNm, errbyte);
	}
	
	/**
	 * 큐브원 AP 복호화 함수
	 * @param strEncMsg 복호화할 문자열
	 * @param strCrudLog
	 * @param strItemCd 아이템코드 (PWD, CARD, ...)
	 * @param strTableNm (TABLE명)
	 * @param strColNm (컬럼명)
	 * @return String 복호화된 문자열
	 */
	public static String codecchar(String strEncMsg, int strCrudLog, String strItemCd, String strTableNm, String strColNm) {
		byte[] errbyte = new byte[8];
		return  CubeOneAPI.codecchar(strEncMsg, strItemCd, 10 ,strTableNm, strColNm, errbyte);
	}
	
	
	/**
	 * 큐브원 AP 인덱스 암호화 함수
	 * @param strMsg
	 * @param strItemCd
	 * @param strTableNm
	 * @param strColNm
	 * @return
	 */
	public static String coindexchar(String strMsg, String strItemCd, String strTableNm, String strColNm) {
		byte[] errbyte = new byte[8];
		return  CubeOneAPI.coindexchar(strMsg, strItemCd, strTableNm, strColNm, errbyte);
	}
	
	/**
	 * 큐브원 AP 인덱스 복호화 함수
	 * @param strEncMsg
	 * @param strItemCd
	 * @param strTableNm
	 * @param strColNm
	 * @return
	 */
	public static String coindexcharsel(String strEncMsg, String strItemCd, String strTableNm, String strColNm) {
		byte[] errbyte = new byte[8];
		return  CubeOneAPI.coindexchar(strEncMsg, strItemCd, strTableNm, strColNm, errbyte);
	}
	
}