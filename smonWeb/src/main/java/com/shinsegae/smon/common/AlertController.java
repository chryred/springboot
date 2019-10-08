package com.shinsegae.smon.common;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.model.AlertVO;
import com.shinsegae.smon.util.CastingJson;
import com.shinsegae.smon.util.SendBlossomTalk;

@Controller
public class AlertController {

	
	@Autowired
	AlertService alertService;
	
	@Autowired
	CastingJson json; //JSON 데이터 형태로 변환하는 인스턴트 객체 생성
	
	@Autowired
	SendBlossomTalk sendBlossomTalk;
	
	public void insertAlertLog(AlertVO vo) throws Exception{
	    try {
		    	alertService.insertAlertHistoryTable(vo);
		        String[] member = {
		                           "129224",  //송시인
		                           "163384"   //임용혁
		                           //"129984",  //김지훈
		                           //"147626",  //김신용
		                           //"129223",  //최병철
		                           //"160619",  //최서진
		                           //"142902",  //이화수
		                           //"151464",  //우미영
		                           //"067828",  //곽승주
		                           //"081405"   //박종혁
		                           };
		        
		  
		        //String contents = setMessage(vo);
		        
		        String message  = "";
		               message += "DB 명 : " + vo.getDbName();
		               //message += "\n체크항목 : " + contents;
		               message += "\n체크항목 : " + vo.getCheckSubItem();
		               message += "\n체크내용 : " + vo.getCheckInfo();
		        
		        //System.out.println("Message : " + message);
		        
		        sendBlossomTalk.sendBlossomTalk("p905z1", "blossom01", "가상기기", member, message);
	        } catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	    
    public String setMessage(AlertVO vo) throws Exception{
        return alertService.selectAlertMessage(vo);
	}
	
	@RequestMapping("/selectAlertLog")
	public @ResponseBody String selectAlertLog(@RequestParam(value="system") String system) {        
	    
	    String data ="";
	    try {
	
	        List<HashMap<String, Object>> list = alertService.selectAlertLog(system);
	        data = json.setJsonData(list);
	        
	    } catch(Exception e){
	        e.printStackTrace();
	        data = null;
	    }
	    
	    return data;
	}
	
	@RequestMapping("/selectHitRatio")
	public @ResponseBody String selectHitRatio(@RequestParam HashMap<String,String> map) {        
	    String data ="";
	    try {
	        List<HashMap<String, Object>> list = alertService.selectHitRatio(map);
	        data = json.setJsonData(list);
	    }catch(Exception e){
	        e.printStackTrace();
	        data = null;
	    }
	    
	    return data;
	}
}
