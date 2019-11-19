package com.shinsegae.smon.tool;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.adm.ToolMapper;
import com.shinsegae.smon.model.tool.EncDecVO;
import com.shinsegae.smon.model.tool.SystemCodeVO;

/*******************************
 * 개발도구 서비스
 * @author 153712
 * @since 2018.03.19
 *******************************/
@Service
public class ToolService {
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	/*******************************
	 * 시스템 코드 가져오기
	 * @param SystemCodeVO paramVO
	 * @return List<SystemCodeVO>
	 * @throws Exception
	 *******************************/
	public List<SystemCodeVO> getSystemCodes(SystemCodeVO paramVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(ToolMapper.class).getSystemCodes(paramVO);
	}

	/*******************************
	 * 암복호화 결과
	 * @param EncDecVO paramCondition
	 * @return String
	 * @throws Exception
	 *******************************/
	public String getEncDecData(EncDecVO paramCondition) throws Exception {
		String chkGubn = paramCondition.getChkGubn();
		String chkGubn2 = paramCondition.getChkGubn2();
		String charsetName = paramCondition.getCharsetName(); 
		String password = paramCondition.getPassword();
		String algorithm = paramCondition.getAlgorithm();
		String salt = paramCondition.getSalt();
		String beforeData = paramCondition.getBeforeData();
		String afterData = "";
		
		// Base64
		if (chkGubn.equals("1")) {
			if (chkGubn2.equals("1")) {
				afterData = getEncodeBase64(beforeData);
			} else if (chkGubn2.equals("2")) {
				afterData = getDecodeBase64(beforeData);
			}
		// URL
		} else if (chkGubn.equals("2")) {
			if (chkGubn2.equals("1")) {
				afterData = getEncodeUrl(beforeData, charsetName);
			} else if (chkGubn2.equals("2")) {
				afterData = getDecodeUrl(beforeData, charsetName);
			}
		// Jasypt
		} else if (chkGubn.equals("3")) {
			if (chkGubn2.equals("1")) {
				afterData = getEncryptJasypt(beforeData, password, algorithm);
			} else if (chkGubn2.equals("2")) {
				afterData = getDecryptJasypt(beforeData, password, algorithm);
			}
		} else if (chkGubn.equals("4")) {
			afterData = getMessageDigestHash(beforeData, salt, algorithm);
		}
		
		return afterData;
	}	
	
	/*******************************
	 * Base64 인코딩
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getEncodeBase64(String param) throws Exception {
		String returnStr;
		
		returnStr = new String(Base64.encodeBase64(param.getBytes()));
		
		return returnStr;
	}
	
	/*******************************
	 * Base64 디코딩
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getDecodeBase64(String param) throws Exception {
		String returnStr;
		
		returnStr = new String(Base64.decodeBase64(param.getBytes()));
		
		return returnStr;
	}	
	
	/*******************************
	 * Url 인코딩
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getEncodeUrl(String beforeData, String charsetName) throws Exception {
		String returnStr;
		
		returnStr = URLEncoder.encode(beforeData, charsetName);
		
		return returnStr;
	}
	
	/*******************************
	 * Url 디코딩
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getDecodeUrl(String beforeData, String charsetName) throws Exception {
		String returnStr;
		
		returnStr = URLDecoder.decode(beforeData, charsetName);
		
		return returnStr;
	}	
	
	/*******************************
	 * Jasypt 암호화
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getEncryptJasypt(String beforeData, String password, String algorithm) throws Exception {
		String returnStr;
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		
		encryptor.setPassword(password);
		encryptor.setAlgorithm(algorithm);
		
		returnStr = encryptor.encrypt(beforeData);
		
		return returnStr;
	}	
	
	/*******************************
	 * Jasypt 복호화
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getDecryptJasypt(String beforeData, String password, String algorithm) throws Exception {
		String returnStr;
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		
		encryptor.setPassword(password);
		encryptor.setAlgorithm(algorithm);
		
		returnStr = encryptor.decrypt(beforeData);
		
		return returnStr;
	}	
	
	/*******************************
	 * MessageDigest Hash
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String getMessageDigestHash(String beforeData, String salt, String algorithm) throws Exception {
		String returnStr;
		
		MessageDigest md = MessageDigest.getInstance(algorithm);
	    
	    md.update(salt.getBytes("UTF-8")); // salt 첨가
        md.update(beforeData.getBytes("UTF-8")); // 데이터 추가
        
		returnStr = byteToHexString(md.digest()); // 해시처리 후 헥사값 변환
		
		return returnStr;
	}	
	
	/*******************************
	 * 바이트 TO 헥스
	 * @param String
	 * @return String
	 * @throws Exception
	 *******************************/
	private String byteToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();

		for (byte b : data) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}
