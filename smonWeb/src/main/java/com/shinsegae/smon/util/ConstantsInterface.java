package com.shinsegae.smon.util;

public class ConstantsInterface {

	public static final String DB_PROPERTIES_FILE_PATH = "db_connect.properties";
	public static final String MYBATIS_CONFIG_XML = "mybatis-config.xml";

	public static final String LINE = "********************************************";
	public static final String ERROR = "ERROR";

	public static final String LINE_FEED = System.getProperty("line.separator");

	public static final String QUERY_NAME_SPACE = "dbMonitoring.";
	public static final String MTR_QUERY_NAME_SPACE = "dbMonitoring";
	public static final String GATHER_QUERY_NAME_SPACE = "gatherDBInfo.";
	public static final String DAILY_BATCH_QUERY_NAME_SPACE = "dailyBatch.";

	public static final double CPU_CHECK_USAGE = 90;

	// 블라섬톡 상수
	// 모니터링 시스템
	public static final String MONITORING_SYSTEM_CODE = "C6FC612C-F802-4A30-8CF4-3718636C1619";
	public static final String MONITORING_SENDER_ID = "p905z1";

	public static final String CHECK_ITEM_CPU = "C01";
	public static final String CHECK_ITEM_MEMORY = "C02";
	public static final String CHECK_ITEM_OBJECT = "C03";

	public static final String CHECK_SUBITEM_USAGE = "P01";
	public static final String CHECK_SUBITEM_COMPILED = "P03";
	public static final String CHECK_SUBITEM_INVALID = "P02";

	public static final int COMMON_SHEET_ROW = 1;
	public static final int COMMON_COLUMN_INDEX = 1;

	public static final String EMP_LIST = "emp.list";

	public static final Integer DISP_PAGE_ROW_NUM = 10;

	// 로컬
	// public static final String IMG_PATH_DIR = "D:/app/";
	// public static final String IMG_URL = "D:/app/";

	// 운영기
	public static final String IMG_PATH_DIR = "/app/jeus/smon/images/app/wiki/uploads/";
	public static final String IMG_URL = "https://smon.shinsegae.com/images/app/wiki/uploads/";

}