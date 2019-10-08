package com.shinsegae.smon.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @FileName : NLogger.java
 * @Date : 2018. 2. 27.
 * @작성자 : 173917
 * @변경이력 :
 * @프로그램 설명 :
 */
public class NLogger {

	private static Logger logger;

	static {
		logger = LoggerFactory.getLogger(NLogger.class);
	}


	/**
	 * @Method Name : isLogging
	 * @작성일 : 2018. 2. 27.
	 * @작성자 : 173917
	 * @변경이력 :
	 * @Method 설명 : 로그 가능 여부 리턴
	 * @return boolean
	 */
	public static boolean isLogging() {
		if(logger == null) {
			return false;
		}

		if(!logger.isDebugEnabled()) {
			logger.debug("Not Debug Mode~~!!!!");
			return false;
		}

		return true;
	}

	/**
	 * @Method Name : getString
	 * @작성일 : 2018. 2. 27.
	 * @작성자 : 173917
	 * @변경이력 :
	 * @Method 설명 : object객체를 합쳐주는 메소드
	 * @param args
	 * @return StringBuilder
	 */
	public static String getString(Object ... args) {
		StringBuilder sb = new StringBuilder();

		List<String> lTrace = getStackTrace(3);

		sb.append("[").append(lTrace.get(0))
			.append(":")
			.append(lTrace.get(1))
			.append(":")
			.append(lTrace.get(2))
			.append("] ");

		for(Object object : args) {
			if(object == null) {
				continue;
			}

			sb.append(object.toString());
		}

		return sb.toString();
	}

	/**
	 * @Method Name : debug
	 * @작성일 : 2018. 2. 27.
	 * @작성자 : 173917
	 * @변경이력 :
	 * @Method 설명 : debug모드
	 * @param args
	 */
	public static void debug(Object ... args ) {
		if(!isLogging()) {
			return;
		}
		logger.debug(getString(args).replaceAll("[\r\n]", ""));
	}


	/**
	 * @Method Name : info
	 * @작성일 : 2018. 2. 27.
	 * @작성자 : 173917
	 * @변경이력 :
	 * @Method 설명 : info모드
	 * @param args
	 */
	public static void info(Object ... args ) {
		logger.info(getString(args).replaceAll("[\r\n]", ""));
	}

	/**
	 * @Method Name : error
	 * @작성일 : 2018. 2. 27.
	 * @작성자 : 173917
	 * @변경이력 :
	 * @Method 설명 : error모드
	 * @param args
	 */
	public static void error(Object ... args ) {
		if(!logger.isErrorEnabled()) {
			return;
		}

		logger.error(getString(args).replaceAll("[\r\n]", ""));
	}

	/**
	 * @Method Name : warn
	 * @작성일 : 2018. 2. 27.
	 * @작성자 : 173917
	 * @변경이력 :
	 * @Method 설명 : warn모드
	 * @param args
	 */
	public static void warn(Object ... args ) {
		logger.warn(getString().replaceAll("[\r\n]", ""));
	}

	public static List<String> getStackTrace(int nIdx) {
		StackTraceElement[] stackTrace = null;
		List<String> listResult = new ArrayList<String>();
		String strFileNm = null;
		String strMethodNm = null;
		String strLineNum = null;

		try {
			throw new Exception("Trace Exception~~!!");
		} catch(Exception e) {
			stackTrace = e.getStackTrace();
		}

		if(stackTrace.length > nIdx - 1) {
			strFileNm = stackTrace[nIdx].getFileName().lastIndexOf(".") > -1 ? stackTrace[nIdx].getFileName().substring(0, stackTrace[nIdx].getFileName().lastIndexOf(".") ) : stackTrace[nIdx].getFileName();
			strMethodNm = stackTrace[nIdx].getMethodName();
			strLineNum = String.valueOf(stackTrace[nIdx].getLineNumber());
		}
		listResult.add(strFileNm);
		listResult.add(strMethodNm);
		listResult.add(strLineNum);

		return listResult;
	}
}
