package com.shinsegae.smon.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;


public class OracleTypeHandler implements TypeHandler<String> {
	
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		String s = rs.getString(columnName);
		return decodeUS7ASCII(s);
	}
	
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		String s = rs.getString(columnIndex);
		return decodeUS7ASCII(s);
	}

	public String getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String s = cs.getString(columnIndex);
	
		return decodeUS7ASCII(s);
	}
	
	public void setParameter(PreparedStatement ps, int i, String str, JdbcType jdbcType) throws SQLException {
	    ps.setString(i, encodeUS7ASCII(str));
	}


    public static String encodeUS7ASCII(String value) {
    	if(value == null) {
    		return "";
    	}
    	String str = "";
    	try {
    	    //KSC5601
    		str = new String(value.getBytes("KSC5601") ,"8859_1");
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
    	return str;
    }

    public static String decodeUS7ASCII(String value) {
    	if(value== null) {
    		return "";
    	}
    	String str = "";
    	try {
    		str = new String(value.getBytes("8859_1"), "KSC5601");
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return str;
    }
	
}
