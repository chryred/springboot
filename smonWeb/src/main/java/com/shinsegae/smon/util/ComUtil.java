package com.shinsegae.smon.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class ComUtil {

   
    /**
     * 캐릭터셋 변환 (ISO8859 -> KSC5601)
     * @param 이전 문자열
     * @return 변경된 문자열
     */
    public static String toKSC5601(String s){ 
        
        if(s==null){ 
            return null; 
        } 
        
        try{ 
            return new String(s.getBytes("8859_1"),"KSC5601"); 
        }catch(Exception e){
            return s;
        } 
    } 
    
    /**
     * 캐릭터셋 변환 (ISO8859 -> UTF-8)
     * @param 이전 문자열
     * @return 변경된 문자열
     */
    public static String toUTF8(String s){ 
        
        if(s==null){ 
            return null; 
        } 
        
        try{ 
            return new String(s.getBytes("8859_1"),"UTF-8"); 
        }catch(Exception e){
            return s;
        } 
    } 
    
    /**
     * 캐릭터셋 변환 (KSC5601 -> 8859_1)
     * @param 이전 문자열
     * @return 변경된 문자열
     */
    public static String to8859_1(String s){ 
        
        if(s==null){ 
            return null; 
        } 
        
        try{ 
            return new String(s.getBytes("KSC5601"),"8859_1"); 
        }catch(Exception e){
            return s;
        } 
    } 
    
    /**
     * clob 문자열로 전환
     * @param 이전 문자열
     * @return 변경된 문자열
     */
    public static String clobToString(Reader input) throws IOException{
        
        StringBuffer output = new StringBuffer();
        char[] buffer = new char[1024];
        int byteRead;

        while((byteRead=input.read(buffer,0, 1024))!= -1){
            output.append(buffer, 0, byteRead);
        }

        input.close();
        
        return output.toString();
    }
    
    
     
     /**
      * NVL처리 
      * @param value
      * @param replacer
      * @return
     */
    public static String nvl(String value, String replacer){
        if (value == null){
           return replacer;
        }else{
           return value;
        }
     }
     
     /** 파일이름변경 */
 	public static File fileRename(String asFilePath, String fixStr) throws IOException
 	{
 		return fileRename(new File(asFilePath), fixStr);
 	}
 	
 	
 	/** 파일이름변경 */
 	public static File fileRename(String filePath, String fileName, String fixStr) throws IOException
 	{
 		return fileRename(new File(filePath + fileName), fixStr);
 	}
 	
 	
 	/** 파일이름변경 */
 	public static File fileRename(File file, String fixStr) throws IOException
 	{
 		File returnFile = file;
 		String fileName = file.getName();
 		String fileExt = getFileExt(fileName).toLowerCase();
 		if (file.exists())
 		{
 			// -&/%=?:@#$(),.+;~
 			// fileName = fileName.replace("." + fileExt, "").replace(" ", "_")
 			// + "_" + System.currentTimeMillis() + "." + fileExt;
 			fileName = fileName.replace("-", "_");
 			fileName = fileName.replace("&", "_");
 			fileName = fileName.replace("/", "_");
 			fileName = fileName.replace("%", "_");
 			fileName = fileName.replace("!", "_");
 			fileName = fileName.replace("@", "_");
 			fileName = fileName.replace("#", "_");
 			fileName = fileName.replace("$", "_");
 			fileName = fileName.replace("^", "_");
 			fileName = fileName.replace("*", "_");
 			fileName = fileName.replace("(", "_");
 			fileName = fileName.replace(")", "_");
 			fileName = fileName.replace("+", "_");
 			fileName = fileName.replace("<", "_");
 			fileName = fileName.replace(">", "_");
 			// fileName = fileName.replace(".", "_");
 			fileName = fileName.replace(";", "_");
 			fileName = fileName.replace("'", "_");
 			fileName = fileName.replace("}", "_");
 			fileName = fileName.replace("{", "_");
 			fileName = fileName.replace("~", "_");
 			fileName = fileName.replace("[", "_");
 			fileName = fileName.replace("]", "_");
 			returnFile = new File(file.getAbsolutePath().replace(file.getName(), fileName));
 			file.renameTo(returnFile);
 		}
 		return returnFile;
 	}

 	/** String 타입의 파일명에 확장자를 반환 */
	public static String getFileExt(String filename)
	{
		String ext = "";
		if (filename.lastIndexOf(".") > 0)
		{
			ext = filename.substring(filename.lastIndexOf(".") + 1);
		}
		return ext;
	}
	
	/** null 체크후 기본값으로 리턴 */
	public static Object nvlObject(Object obj)
	{
		if (obj == null)
			return "";
		else
			return obj;
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	public static String nvl(Object obj)
	{
		if (obj == null)
			return "";
		else
			return nvl(obj.toString());
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	public static String nvl(String val)
	{
		return nvl(val, "");
	}
	
	/** null 체크후 기본값으로 리턴 */
	private static String[] nvl(String values[])
	{
		for (int i = 0; i < values.length; i++)
		{
			values[i] = nvl(values[i]);
		}
		return values;
	}
	
	/** unicode를 html 형식에 맞게 변환 */
	public static String validFormValueNC(String val)
	{
		val = nvl(val);
		// val = val.replace("#", "&#35;");
		// val = val.replace("&", "&#38;");
		val = val.replace("&lt;", "<");
		val = val.replace("&gt;", ">");
		val = val.replace("&#39;", "\'");
		val = val.replace("&quot;", "\"");
		val = val.replace("&#40;", "(");
		val = val.replace("&#41;", ")");
		val = val.replace("&#43;", "+");
		val = val.replace("&#166;", "|");
		val = val.replace("&#45;", "-");
		val = val.replace("&#42;", "*");
		// val = val.replace("/", "&#47;");
		val = replaceXSS(val);
		
		return val;
	}
	
	/** XSS 처리 */
	public static String replaceXSS(String str)
	{
		str = str.replaceAll("<(?i)script(.*?)>", "&lt;script&gt;");
		str = str.replaceAll("<(/)(?i)script>", "&lt;/script&gt;");
		str = str.replaceAll("&lt;(?i)script(.*?)&gt;", "&lt;script&gt;");
		str = str.replaceAll("&lt;(/)(?i)script&gt;", "&lt;/script&gt;");
		
		return str;
	}

}


