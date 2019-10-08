package com.shinsegae.smon.util;

import java.util.ArrayList;
import java.util.List;

import com.ssg.blossompush.lib.BlossomPush;
import com.ssg.blossompush.vo.BlossomPushResponseVO;
import com.ssg.blossompush.vo.BlossomPushVO;

/* 2017.04.23 김혜민 담당
 * 블라썸 푸쉬 전송
 * 블라썸 API jar 파일을 활용한 푸쉬 전송 스태틱 메써드
 * 
 * 성공/실패에 대한 리턴값 없음
 */

public class ActionBlossomPush {
	
	public static void actionBlossomPush(String receiver, String message) throws Exception{
		if( receiver != null && receiver.length() != 0 ){
			String msg = message.replaceAll("<br>", "\r\n");
			
			BlossomPushVO blossomPushVO = new BlossomPushVO();
			String[] receiverArray;
			List<String> receiverList = new ArrayList<String>();
			String rcv = receiver.replaceAll(" ", "");
			receiverArray = rcv.split(",");
			
			for(int i=0; i<receiverArray.length; i++){
				receiverList.add(receiverArray[i]);
			}
			
			blossomPushVO.setSystemCode("C6FC612C-F802-4A30-8CF4-3718636C1619");
			blossomPushVO.setReciverIds(receiverList); 
			blossomPushVO.setHeader("시스템 VOC 알림"); //분류: 알림, 공지, 경고 등
			blossomPushVO.setMessage(""); //제목
			blossomPushVO.setMessageBody("[시스템 VOC] : "+ msg +"\n (문의 번호 : 1544-0000)" ); //본문
			blossomPushVO.setSenderId("p905z1"); //발신자 사번
			
			blossomPushVO.setConnectionTimeout(5);
			
			BlossomPush blossomPush = new BlossomPush();
			BlossomPushResponseVO blossomPushResponseVO = null;
			try {
				blossomPushResponseVO = blossomPush.sendPush(blossomPushVO);
				NLogger.debug(blossomPushResponseVO.getStatusCode()); 
				NLogger.debug(blossomPushResponseVO.getReason()); 
				NLogger.debug(blossomPushResponseVO.getContent()); 
			} catch (java.net.SocketTimeoutException e1) {
				NLogger.error("connection timeout...");
			} catch (Exception e2) {
				NLogger.error("Blossom push error occured...");
			}
		
		
		}
	}
	
	public static void actionBlossomPush(String receiver, String message, String randomNum) throws Exception{
		
		if( receiver != null && receiver.length() != 0 ){
			
			String msg = message.replaceAll("<br>", "\r\n");
			
			BlossomPushVO blossomPushVO = new BlossomPushVO();
			String[] receiverArray;
			List<String> receiverList = new ArrayList<String>();
			String rcv = receiver.replaceAll(" ", "");
			receiverArray = rcv.split(",");
			
			for(int i=0; i<receiverArray.length; i++){
				receiverList.add(receiverArray[i]);
			}
			
			blossomPushVO.setSystemCode("C6FC612C-F802-4A30-8CF4-3718636C1619");
			blossomPushVO.setReciverIds(receiverList); 
			
			if (randomNum == null) {
				blossomPushVO.setHeader("시스템 알림"); //분류: 알림, 공지, 경고 등
			} else {
				blossomPushVO.setHeader("시스템 알림 [" + randomNum + "]"); //분류: 알림, 공지, 경고 등
			}
			blossomPushVO.setMessage(""); //제목
			blossomPushVO.setMessageBody("[시스템] : "+ msg +"\n (문의 번호 : 1544-0000)" ); //본문
			blossomPushVO.setSenderId("p905z1"); //발신자 사번
			
			blossomPushVO.setConnectionTimeout(5);
			
			BlossomPush blossomPush = new BlossomPush();
			BlossomPushResponseVO blossomPushResponseVO = null;
			try {
				blossomPushResponseVO = blossomPush.sendPush(blossomPushVO);
				NLogger.debug(blossomPushResponseVO.getStatusCode()); 
				NLogger.debug(blossomPushResponseVO.getReason()); 
				NLogger.debug(blossomPushResponseVO.getContent()); 
			} catch (java.net.SocketTimeoutException e1) {
				NLogger.error("connection timeout...");
			} catch (Exception e2) {
				NLogger.error("Blossom push error occured...");
			}
		
		
		}
	}

}
