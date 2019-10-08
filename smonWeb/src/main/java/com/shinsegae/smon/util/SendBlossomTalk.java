package com.shinsegae.smon.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SendBlossomTalk {

    static final String TALK_URL ="http://blossom.shinsegae.com/Website/Custom/Mobile/SPNSRequestPushSrv_Multi.asmx/RequestPush";
    
    public void sendBlossomTalk(String sno, String pwd, String deviceId, String[] memberList, String text) throws Exception{
         String member = "";
         
         try {
             String token  = loginBlossom(sno, pwd, deviceId);
             
             for(int i =0; i< memberList.length; i++){
                 member = memberList[i];
                 String chatId = createChat(token, member);
                 sendMsg(chatId, token, text);
             }
             LogOut(token);
                 
         } catch (Exception e) {
             e.printStackTrace();
             throw e;
         }
     }
    
    // �α���
    public String loginBlossom(String sno, String pwd, String deviceId) throws Exception {
       
       OutputStreamWriter wr = null;
       BufferedReader rd = null;
        
       String result = "";
       String resultTk="";
       Map<String, Object> mResult = null;
        
        try{
            
            URL url = new URL(TALK_URL+"/user/v3/login");    
            URLConnection conn = url.openConnection();
            
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" ) ;
            conn.setDoOutput(true);
            
            String data = "sno="+sno;
            data += "&pwd="+pwd;
            data += "&deviceId="+deviceId;
 
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
    
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            result = rd.readLine();
            
            mResult = (new ObjectMapper()).readValue(result.trim(), new TypeReference<Map<String, Object>>() {});
            Map<String, String> mLgnData = (Map<String, String>)mResult.get("data");
            resultTk  = mLgnData.get("token");
            
        } catch (Exception e) {
            throw e;
            
        } finally {
            try {
                 if (wr != null)    wr.close();             
                 if (rd != null)    rd.close();             
            } catch (Exception e) {
                 e.printStackTrace();
            }
       }        
        return resultTk;
    }
    
    
    public  String createChat(String token, String member) throws Exception {
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
            
        String result = "";
        String ChatId = "";
        Map<String, Object> mResult = null;
        
        try{
            URL url = new URL(TALK_URL+"/chat?members[0]="+member);    
            URLConnection conn = url.openConnection();

            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" ) ;
            conn.setDoOutput(true);
            conn.setRequestProperty("x-api-token",token);

            String data = "HEADER="+token;
                    
            wr =  new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            result = rd.readLine();
            
            mResult = (new ObjectMapper()).readValue(result.trim(), new TypeReference<Map<String, Object>>() {});
            Map<String, String> mLgnData = (Map<String, String>)mResult.get("data");
            ChatId  = mLgnData.get("chatId");
                                
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ChatId;        
    }
    
    public void sendMsg(String chatId, String token, String Text) throws Exception {
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
      
         String result = "";
         String ChatId = "";
         
          try{
              // Send data
              URL url = new URL(TALK_URL+"/chat/"+chatId+"/talks");    
              URLConnection conn = url.openConnection();

              conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" ) ;
              conn.setDoOutput(true);
              conn.setRequestProperty("x-api-token",token);

              String data = "HEADER="+token;
                     data += "&charId in url="+chatId;
                     data += "&text="+URLEncoder.encode(Text,"UTF-8");
                 
              wr =  new OutputStreamWriter(conn.getOutputStream());
              wr.write(data);
              wr.flush();
              
              rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

              result = rd.readLine();
              //System.out.println(result);
                                  
          } catch (Exception e) {
              e.printStackTrace();
              throw e;
          }         
      }
    
    public void LogOut(String token) throws Exception {
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
      
         String result = "";
         String ChatId = "";
         
          try{
              // Send data
              URL url = new URL(TALK_URL+"/user/logout");    
              URLConnection conn = url.openConnection();

              conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" ) ;
              conn.setDoOutput(true);
              conn.setRequestProperty("x-api-token",token);

              String data = "HEADER="+token;
                 
              wr =  new OutputStreamWriter(conn.getOutputStream());
              wr.write(data);
              wr.flush();
              
              rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

              result = rd.readLine();
              //System.out.println(result);
                                  
          } catch (Exception e) {
              e.printStackTrace();
              throw e;
          }         
      }
}
