package com.shinsegae.smon.common.user;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.NLogger;

/*******************************
 * 사용자 서비스
 * @author 김성일 담당
 * @since 2018.01.17
 *******************************/
@Service
public class UserService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	/*******************************
	 * 사용자 인증번호 업데이트
	 * @param HashMap<String, Object> params
	 * @return void
	 * @throws Exception
	 *******************************/
	public void updateCertNum(HashMap<String, Object> params) throws Exception {
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).updateCertNum(params);
	}
	
	/*******************************
	 * 사용자 비밀번호 변경용 인증번호 업데이트
	 * @param HashMap<String, Object> params
	 * @return void
	 * @throws Exception
	 *******************************/
	public void updatePwdCertNum(HashMap<String, Object> params) throws Exception {
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).updatePwdCertNum(params);
	}

	/*******************************
	 * 사용자 정보 조회
	 * @param HashMap<String, Object> paramVO
	 * @return UserVO
	 * @throws Exception
	 *******************************/
	public UserVO getUser(HashMap<String, Object> paramVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).getUser(paramVO); // 사용자 VO
	}

	/*******************************
	 * 회원등록
	 * @param UserVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void saveMember(UserVO paramUserVO) throws Exception {
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).insertUserPasswordHist(paramUserVO);
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).saveMember(paramUserVO);
	}

	/*******************************
	 * 운영정보 사용자 확인
	 * @param HashMap<String, Object>
	 * @return UserVO
	 * @throws Exception
	 *******************************/
	public UserVO getSossUser(HashMap<String, Object> paramVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).getSossUser(paramVO); // 사용자 VO
	}

	/*******************************
	 * 패스워드 정합성 확인
	 * @param HashMap<String, Object>
	 * @return UserVO
	 * @throws Exception
	 *******************************/
	public HashMap<String, Object> confirmPwd(HashMap<String, Object> paramMap) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String varResultCode = "0000";
		String varResultMsg = "정상처리되었습니다.";
		
		HashMap<String, Object> varChkCase1 = (HashMap<String, Object>) paramMap.get("PASSWORD_DIFFERENCE"); // 동일 PASSWORD인지 확인 
		HashMap<String, Object> varChkCase2 = (HashMap<String, Object>) paramMap.get("PASSWORD_COMBINATION"); // 비밀번호 생성규칙
		HashMap<String, Object> varChkCase3 = (HashMap<String, Object>) paramMap.get("PASSWORD_ASSOCIATED"); // 연관된 정보
		HashMap<String, Object> varChkCase4 = (HashMap<String, Object>) paramMap.get("PASSWORD_DIFFERENCE_BEFORE"); // 연관된 정보
		
		/***************************
		 * 1. 패스워드 동일여부 파라미터 세팅
		 **************************/
		if (varChkCase1 != null) {
			String varPassword = (String) varChkCase1.get("password");
			String varPassword2 = (String) varChkCase1.get("password2");
			
			if (!varPassword.equals(varPassword2) && !varResultCode.equals("0000")) {
				varResultCode = "0100";
				varResultMsg = "두개의 비밀번호가 서로 일치하지 않습니다.";
			}
		}
		
		/***************************
		 * 2. 비밀번호 생성규칙(정규식)
		 * - 3가지 조합 8자리 이상
		 * - 연속된 3개문자 
		 * - 연속된 숫자, 문자
		 **************************/
		if (varChkCase2 != null) {
			String varPassword = (String) varChkCase2.get("password");
			//String regexString = "/^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$/g"; // 정규식(3가지 조합 8~20자리)
			String regexString = "^(?=.*[0-9]+)(?=.*[~`!@#$%\\^&*()-]).{8,20}$"; // 정규식(3가지 조합 8~20자리)
			String regexString3 = "^(\\w)\\1\\1$"; // 동일문자 3번이상
			
			/***************************
			 * 2-1. 3가지 조합 8~20자리
			 **************************/
			if (!varPassword.matches(regexString) && varResultCode.equals("0000")) {
				varResultCode = "0210";
				varResultMsg = "비밀번호는 3가지 조합 8~20자리로 구성되어야합니다.";
			}
			
			/***************************
			 * 2-2. 연속된 3개문자 
			 **************************/
			int incCount = 0;
			int decCount = 0;
			int repCount = 0;
			
			char[] msgChars = varPassword.toCharArray();
			
			char chr1;
			char chr2;
			char chr3;
			
			for (int idx = 0; idx < msgChars.length-2; idx++) {
				chr1 = msgChars[idx];
				chr2 = msgChars[idx+1];
				chr3 = msgChars[idx+2];
				
				if (chr1 - chr2 == 1 && chr2 - chr3 == 1) incCount++;
				if (chr1 - chr2 == -1 && chr2 - chr3 == -1) decCount++;
				if (chr1 - chr2 == 0 && chr2 - chr3 == 0) repCount++;
			}
			
			if ((incCount != 0 || decCount != 0) && varResultCode.equals("0000")) {
				varResultCode = "0220";
				varResultMsg = "비밀번호는 연속된 문자가 3번이상 반복될 수 없습니다.";
			}
			
			/***************************
			 * 2-3. 동일문자 3번이상 
			 **************************/
			if (repCount != 0 && varResultCode.equals("0000")) {
				varResultCode = "0230";
				varResultMsg = "비밀번호는 동일문자가 3번이상 반복될 수 없습니다.";
			}
		}
		
		/***************************
		 * 3. 비밀번호 생성규칙
		 *    - 사용자 연관된 정보(생일, 전화번호, 아이디, 사번, 이메일ID 등 연관된 비밀번호)
		 **************************/
		if (varChkCase3 != null) {
			String varPassword = (String) varChkCase3.get("password");
			List<HashMap<String, Object>> wordMaps = (List<HashMap<String, Object>>) varChkCase3.get("wordMaps");
			
			Integer varSeqno = null;
			String varChkClause = "";
			String varChkValue = "";
			String varMsg = "";
			
			for (HashMap<String, Object> map : wordMaps) {
				varSeqno = (Integer) map.get("seqno"); // 항목 번호
				varChkClause = (String) map.get("chkClause"); // 항목
				varChkValue = (String) map.get("chkValue") == null ? "" : (String) map.get("chkValue"); // 값
				
				if (varPassword.indexOf(varChkValue) != -1 && !varChkValue.equals("")) {
					varMsg += "* [" + varSeqno + "] " + varChkClause + "(이)가 비밀번호에 포함될 수 없습니다.\n";				
				}
			}
			
			if (!varMsg.equals("")  && varResultCode.equals("0000")) {
				varResultCode = "0300";
				varResultMsg = varMsg;
			}
		}
		
		resultMap.put("resultCode", varResultCode);
		resultMap.put("resultMsg", varResultMsg);
		
		return resultMap;
	}

	/*******************************
	 * 연관된 단어 가져오기
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object> paramVO
	 * @throws Exception
	 *******************************/
	public List<HashMap<String, Object>> getAssociatedWordMap(HashMap<String, Object> paramVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).getAssociatedWordMap(paramVO); // 연관된 단어
	}

	/*******************************
	 * 사용자 업데이트
	 * @param HashMap<String, Object>
	 * @return void
	 * @throws Exception
	 *******************************/
	public void updateUser(HashMap<String, Object> paramMap) throws Exception {
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).updateUser(paramMap);
	}

	/*******************************
	 * 사용자 로그인 이력 저장
	 * @param UserVO userVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void insertMgrLoginHis(UserVO userVO) throws Exception {
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).insertMgrLoginHis(userVO);
	}

	/*******************************
	 * 퇴직자 처리
	 * @param void
	 * @return void
	 * @throws Exception
	 *******************************/
	public void saveRetireUser() throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("retireGubun", "1");
		
		List<UserVO> retireUserVOs = sqlSessionTemplatePrimary.getMapper(UserMapper.class).getSossUsers(paramMap); 
		
		if (retireUserVOs != null) {
			for (UserVO userVO : retireUserVOs) {
				sqlSessionTemplatePrimary.getMapper(UserMapper.class).deleteUser(userVO); // 삭제
				NLogger.debug(userVO.getMgrId() + "-퇴직자 처리");
			}
		}
	}

	/*******************************
	 * 미사용자 처리
	 * @param void
	 * @return void
	 * @throws Exception
	 *******************************/
	public void saveUnusedUser() throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HashMap<String, Object> paramMap2 = new HashMap<String, Object>();
		
		paramMap.put("unusedGubun", "Y");
		
		List<UserVO> unusedUserVO = sqlSessionTemplatePrimary.getMapper(UserMapper.class).getMgrLoginHis(paramMap); 
		
		if (unusedUserVO != null) {
			for (UserVO userVO : unusedUserVO) {
				paramMap2.put("mgrId", userVO.getMgrId());
				paramMap2.put("mgrStateCd", "N");
				
				sqlSessionTemplatePrimary.getMapper(UserMapper.class).updateUser(paramMap2); // 업데이트 처리
				NLogger.debug(userVO.getMgrId() + "-미사용자 처리");
			}
		}
		
	}

	/*******************************
	 * 비밀번호 이력 확인
	 * @param UserVO
	 * @return int
	 * @throws Exception
	 *******************************/
	public int checkUserPasswordHist(UserVO userVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).checkUserPasswordHist(userVO); // 사용자 VO
	}
	
	/*******************************
	 * 비밀번호 수정
	 * @param UserVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void updateUserPassword(UserVO userVO) throws Exception {
		sqlSessionTemplatePrimary.getMapper(UserMapper.class).updateUserPassword(userVO); // 사용자 VO
	}
	
	/*******************************
	 * 비밀번호 이력 저장
	 * @param UserVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void modifyUserPasswordHist(UserVO userVO, int passwordHistCnt, String mode) throws Exception {
		
		if(mode.equals("D")) {
			// 비밀번호 3회 이상의 경우, 오래된 이력 삭제
			if(passwordHistCnt > 3) {
				sqlSessionTemplatePrimary.getMapper(UserMapper.class).deleteUserPasswordHist(userVO);
			}
		}else if(mode.equals("I")) {
			// 이력 INSERT
			sqlSessionTemplatePrimary.getMapper(UserMapper.class).insertUserPasswordHist(userVO);
		}
	}
	
	/*******************************
	 * 초기화를 위한 사용자 정보 조회
	 * @param UserVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public HashMap<String,Object> selectUserPasswordInitInfo(UserVO userVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).selectUserPasswordInitInfo(userVO); // 사용자 VO
	}
	
	/*******************************
	 * 임시 비밀번호 수정
	 * @param UserVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public int updateUserPasswordInitInfo(HashMap<String, Object> params) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).updateUserPasswordInitInfo(params); // 사용자 VO
	}
	
	/*******************************
	 * 기존 비밀번호 체크
	 * @param UserVO
	 * @return String
	 * @throws Exception
	 *******************************/
	public String checkUserPassword(UserVO userVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).checkUserPassword(userVO); // 사용자 VO
	}

	/*******************************
	 * 중복로그인 체크
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 *******************************/
	public HashMap<String, Object> selectCurrentUserStat(HashMap<String, Object> paramMap) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).selectCurrentUserStat(paramMap); // 사용자 VO;
	}
	
	/*******************************
	 * 비밀번호 변경 OTP 체크
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 *******************************/
	public HashMap<String, Object> checkPwdOtpInfo(HashMap<String, Object> paramMap) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserMapper.class).checkPwdOtpInfo(paramMap); // 사용자 VO;
	}
}
