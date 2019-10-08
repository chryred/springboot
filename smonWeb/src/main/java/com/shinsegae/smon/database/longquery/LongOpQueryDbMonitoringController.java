package com.shinsegae.smon.database.longquery;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.util.CastingJson;




@Controller
public class LongOpQueryDbMonitoringController {


    @Autowired
    CastingJson json; //JSON

    @Autowired
    LongOpQueryDbMonitoringService longOpQueryDbMonitoringService;

    @RequestMapping("/selectLongOpQuery")
    public @ResponseBody String selectLongOpQuery() {

        String data ="";

        try{
            data = json.setJsonJqGridData(longOpQueryDbMonitoringService.selectDbDictionaryInfo(new HashMap<String, Object>()));
            //System.out.println(data);
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }

        return data;
    }

    @RequestMapping("/selectLongOpQueryPasing")
    public @ResponseBody String selectLongOpQueryPasing(@RequestParam(value="page", required=false) String page,
                                                        @RequestParam(value="rows", required=false) Double rows
                                                       ) {

        String data ="";
        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        try{
            paramMap.put("page", page);
            paramMap.put("rows", rows);


            int records = longOpQueryDbMonitoringService.selectDbDictionaryTotalCnt();

            paramMap.put("records", records);
            data = json.setJsonJqGridData(longOpQueryDbMonitoringService.selectDbDictionaryInfo(paramMap), paramMap);
            //System.out.println(data);
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }

        return data;
    }

    @RequestMapping("/selectLongQuery")
    public @ResponseBody String selectLongQuery(@RequestParam HashMap<String,String> map) {

        String data ="";

        try{
            data = json.setJsonData(longOpQueryDbMonitoringService.selectLongQuery(map));
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }

        return data;
    }

    /**
     * @Method Name : selectLongOpQueryDetail
     * @작성일 : 2018. 3. 22.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long Query 상세 페이지로 이동
     * @param map
     * @return view
     */
    @RequestMapping(value="/selectLongOpQueryDetail")
    public ModelAndView longOpQueryDetail(@RequestParam HashMap<String, String> map) {
    	ModelAndView view = new ModelAndView("mtr/detail/longQpQuery/longOpQueryDetail");
    	view.addObject("long_map", map);
        return view;
    }

    /**
     * @Method Name : selectLongQueryDetailList
     * @작성일 : 2018. 3. 23.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long쿼리 상세 조회 Day
     * @param map
     * @return data
     */
    @RequestMapping("/selectLongQueryDetailList")
    public @ResponseBody String selectLongQueryDetailList(@RequestParam HashMap<String,String> map) {

        String data ="";
//        java.util.Set<Map.Entry<String, String>> entry = map.entrySet();
//        entry.stream().forEach(System.out::println);

        try {
            data = json.setJsonData(longOpQueryDbMonitoringService.selectLongQueryDetailList(map));
        } catch(Exception e){
            data = null;
        }
        return data;
    }

    /**
     * @Method Name : selectLongQueryDetailList
     * @작성일 : 2018. 3. 23.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long쿼리 상세 조회 Month
     * @param map
     * @return data
     */
    @RequestMapping("/selectLongQueryDetailMonList")
    public @ResponseBody String selectLongQueryDetailMonList(@RequestParam HashMap<String,String> map) {
        String data ="";
        try {
            data = json.setJsonData(longOpQueryDbMonitoringService.selectLongQueryDetailMonList(map));
        } catch(Exception e){
        	//e.printStackTrace();
            data = null;
        }

        return data;
    }

    /**
     * @Method Name : selectLongQueryDetailListScnd
     * @작성일 : 2018. 3. 23.
     * @작성자 : 173917
     * @변경이력 :
     * @Method 설명 : Long쿼리 상세 Query 조회
     * @param map
     * @return data
     */
    @RequestMapping("/selectLongQueryDetailListScnd")
    public @ResponseBody String selectLongQueryDetailListScnd(@RequestParam HashMap<String,String> map) {

        String data ="";

        try{
            data = json.setJsonData(longOpQueryDbMonitoringService.selectLongQueryDetailListScnd(map));
        }catch(Exception e) {
        	//e.printStackTrace();
            data = null;
        }

        return data;
    }
}
