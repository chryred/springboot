package com.shinsegae.smon.database.longquery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



@Service
public class LongOpQueryDbMonitoringService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

    public String combineSql(List<HashMap<String, Object>> dbDictionaryList2)  throws Exception{

        Iterator<HashMap<String, Object>> longOpQueryIter = dbDictionaryList2.iterator();
        String returnValue = "";

        while(longOpQueryIter.hasNext()){
            returnValue += (String) longOpQueryIter.next().get("SQL_TEXT");
        }

        return returnValue;
    }

    public int selectDbDictionaryTotalCnt() throws Exception {
        // TODO Auto-generated method stub
        return sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongOpQueryListTckTotal();
    }

    public List<HashMap<String, Object>> selectDbDictionaryInfo(HashMap<String, Object> paramMap) throws Exception {
        return sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongOpQueryListTck(paramMap);
    }


     public List<HashMap<String, Object>> selectLongQuery(HashMap<String,String> map) throws Exception {

         return sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongQuery(map);
     }

     /**
     * @Method Name : selectLongQueryDetailList
     * @작성일 : 2018. 3. 23.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long Query 상세 조회 Day
     * @param map
     * @return List
     * @throws Exception
     */
    public List<HashMap<String, Object>> selectLongQueryDetailList(HashMap<String,String> map) throws Exception {

    	 // search 아이디가 존재할 경우
    	 if(!StringUtils.isEmpty(map.get("search[value]"))) {
    		 map.put("sql_id", map.get("search[value]"));
    	 }

         return sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongQueryDetailList(map);
     }

     /**
     * @Method Name : selectLongQueryDetailMonList
     * @작성일 : 2018. 3. 23.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long Query 상세 조회 Month
     * @param map
     * @return List
     * @throws Exception
     */
    public List<HashMap<String, Object>> selectLongQueryDetailMonList(HashMap<String,String> map) throws Exception {
    	 // search 아이디가 존재할 경우
    	 if(!StringUtils.isEmpty(map.get("search[value]"))) {
    		 map.put("sql_id", map.get("search[value]"));
    	 }

         return sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongQueryDetailMonList(map);
     }

     /**
     * @Method Name : selectLongQueryDetailListScnd
     * @작성일 : 2018. 3. 23.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long Query Query 상세 조회
     * @param map
     * @return List
     * @throws Exception
     */
    public List<HashMap<String, Object>> selectLongQueryDetailListScnd(HashMap<String,String> map) throws Exception {
    	List<HashMap<String, Object>> lLongQueryDetail = sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongQueryDetailListScnd(map);

    	if(lLongQueryDetail.size() > 0) {
			List<HashMap<String, Object>> lLongQueryDetailParam = sqlSessionTemplatePrimary.getMapper(LongOpQueryDbMonitoringMapper.class).selectLongQueryDetailListScndParam(lLongQueryDetail.get(0));
			String strLongQueryFullText = (String)lLongQueryDetail.get(0).get("SQL_FULLTEXT");

			for(HashMap<String, Object> mLongQueryDetailParam : lLongQueryDetailParam) {
				strLongQueryFullText = strLongQueryFullText.replaceAll("(?i)" + (String)mLongQueryDetailParam.get("BIND_ID"), "'" + (String)mLongQueryDetailParam.get("VALUE_STRING") + "'" );
			}
			lLongQueryDetail.get(0).put("SQL_FULLTEXT", strLongQueryFullText);
			//System.out.println("sql : " + strLongQueryFullText);
		}
         return lLongQueryDetail;
     }

}
