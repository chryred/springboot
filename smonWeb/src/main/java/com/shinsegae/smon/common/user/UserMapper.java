package com.shinsegae.smon.common.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shinsegae.smon.model.UserVO;

@Mapper
public interface UserMapper {
	
	
	public HashMap<String, Object> searchUser(HashMap<String, Object> params) throws Exception;
	
	/*******************************
	 * 인증번호 업데이트
	 * @return ModelAndView
	 *******************************/
	public int updateCertNum(HashMap<String, Object> params) throws Exception;
	
	/*******************************
	 * 비밀번호 인증번호 업데이트
	 * @return ModelAndView
	 *******************************/
	public int updatePwdCertNum(HashMap<String, Object> params) throws Exception;

	/*******************************
	 * 사용자 정보 조회
	 * @param HashMap<String, Object> paramVO
	 * @return ModelAndView
	 * @exception Exception
	 *******************************/
	public UserVO getUser(HashMap<String, Object> paramVO) throws Exception;

	/*******************************
	 * 회원등록
	 * @param UserVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void saveMember(UserVO paramUserVO) throws Exception;

	/*******************************
	 * 운영정보 사용자 확인
	 * @param HashMap<String, Object>
	 * @return UserVO
	 * @throws Exception
	 *******************************/
	public UserVO getSossUser(HashMap<String, Object> paramVO) throws Exception;

	/*******************************
	 * 연관된 단어 가져오기
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object> paramVO
	 * @throws Exception
	 *******************************/
	public List<HashMap<String, Object>> getAssociatedWordMap(HashMap<String, Object> paramVO) throws Exception;
	
	/*******************************
	 * 사용자 비밀번호 변경이력 저장
	 * @param UserVO
	 * @return int
	 * @throws Exception
	 ******************************
	public int insertUserPasswordHist(UserVO userVO) throws Exception;
	*/

	/*******************************
	 * 사용자 업데이트
	 * @param HashMap<String, Object>
	 * @return void
	 * @throws Exception
	 *******************************/
	public void updateUser(HashMap<String, Object> paramMap) throws Exception;

	/*******************************
	 * 사용자 로그인 이력 저장
	 * @param UserVO userVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void insertMgrLoginHis(UserVO userVO) throws Exception;

	/*******************************
	 * 운영정보 사용자 확인
	 * @param HashMap<String, Object>
	 * @return UserVO
	 * @throws Exception
	 *******************************/
	public List<UserVO> getSossUsers(HashMap<String, Object> paramVO) throws Exception;

	/*******************************
	 * 사용자 삭제 처리
	 * @param UserVO userVO
	 * @return void
	 * @throws Exception
	 *******************************/
	public void deleteUser(UserVO userVO) throws Exception;

	/*******************************
	 * 사용자 삭제 처리
	 * @param HashMap<String, Object>
	 * @return List<UserVO>
	 * @throws Exception
	 *******************************/
	public List<UserVO> getMgrLoginHis(HashMap<String, Object> paramMap);
	
	public int checkUserPasswordHist(UserVO userVO) throws Exception;
	
	public int deleteUserPasswordHist(UserVO userVO) throws Exception;
	
	public int insertUserPasswordHist(UserVO userVO) throws Exception;
	
	public int updateUserPassword(UserVO userVO) throws Exception;
	
	public HashMap<String, Object> selectUserPasswordInitInfo(UserVO userVO) throws Exception;
	
	public int updateUserPasswordInitInfo(HashMap<String, Object> params) throws Exception;
	
	public String checkUserPassword(UserVO userVO) throws Exception;

	public HashMap<String, Object> selectCurrentUserStat(HashMap<String, Object> paramMap) throws Exception;
	
	public HashMap<String, Object> checkPwdOtpInfo(HashMap<String, Object> paramMap) throws Exception;
}
