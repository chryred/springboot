package com.shinsegae.smon.incident.contents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shinsegae.smon.model.DefectVO;
import com.shinsegae.smon.model.UserVO;
import com.ssg.blossompush.lib.BlossomPush;
import com.ssg.blossompush.vo.BlossomPushResponseVO;
import com.ssg.blossompush.vo.BlossomPushVO;

/*******************************
 * 장애 등급 측정 서비스
 * 
 * @author 175582
 * @since 2018.02.14
 *******************************/
@Service
public class DefectService {
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	protected static Logger logger = Logger.getLogger(DefectService.class.getName());

	// 장애 등급 측정 결과 메세지 만들기.
	public String makeStr(DefectVO param, String grade) throws Exception {
		logger.info("-----function makeStr-----");

		Date date = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(param
				.getMyLocalDate1());

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		int elapseHour = 0;
		int elapseMinute = ((Long) session.getAttribute("calDate")).intValue();

		// 경과시간 시 분으로 나타내기.
		if (elapseMinute >= 60) {
			elapseHour = elapseMinute / 60;
			elapseMinute = elapseMinute % 60;
		}

		return "[ 아래와 같은 장애가 발생 ]\n\n장애 발생자 : "
				+ userVO.getMgrName() +"("+ userVO.getMgrId()+")"+ "\n장애 등급 　: " + grade
				+ "\n대상 시스템 : " + param.getTargetSystem() + " \n장애 범위 　: "
				+ param.getEffectRange() + "    \n업무 영향도 : "
				+ param.getEffect() + "\n장애경과시간 : " + elapseHour + "시간 "
				+ elapseMinute + "분" + "\n발생 시각 : \n　　　"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}

	// 등급 도출.
	public String checkRating(DefectVO param) throws Exception {
		logger.info("-----function checkRating-----");

		String result = "N";
		String resultOne = "N";
		String resultTwo = "N";
		String effectTime = "N";

		String effect = param.getEffect();
		String effectRange = param.getEffectRange();
		String targetSystem = param.getTargetSystem();
		String myLocalDate1 = param.getMyLocalDate1();
		String myLocalDate2 = param.getMyLocalDate2();

		Date date = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(myLocalDate1);
		Date date1 = new SimpleDateFormat("yyyy-MM-ddHH:mm")
				.parse(myLocalDate2);

		// 현재시간 - 장애시간 = 분 단위로 만드는 작업
		long calDate = date.getTime() - date1.getTime();
		calDate = calDate / (60 * 1000);
		calDate = Math.abs(calDate);

		logger.info("calDate : " + calDate);
		
		// 세션에 calDate값 넣기.
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();

		session.setAttribute("calDate", calDate);

		// 시스템 등급 도출
		if (targetSystem.equals("대고객시스템") || targetSystem.equals("POS시스템")
				|| targetSystem.equals("영업시스템")) {
			targetSystem = "A";
		} else if (targetSystem.equals("영업지원시스템")) {
			targetSystem = "B";
		} else if (targetSystem.equals("비영업시스템")) {
			targetSystem = "C";
		}

		// 영향 등급 도출
		if (effect.equals("중단, 사용불가")) {
			effect = "A";
		} else if (effect.equals("지연, 처리오류, 제한적사용")) {
			effect = "B";
		} else if (effect.equals("경미한영향, 불편")) {
			effect = "C";
		} else if (effect.equals("영향없음")) {
			effect = "D";
		}

		// 관계사 등급 도출
		if (effectRange.equals("전관계사") || effectRange.equals("전사업장")) {
			effectRange = "B";
		} else if (effectRange.equals("일부관계사") || effectRange.equals("일부사업장")) {
			effectRange = "C";
		} else if (effectRange.equals("단일관계사") || effectRange.equals("단일사업장")) {
			effectRange = "D";
		}

		// 장애시간 등급 도출
		if (calDate > 60) {
			effectTime = "B";
		} else if (20 < calDate && calDate <= 60) {
			effectTime = "C";
		} else if (20 >= calDate) {
			effectTime = "D";
		}

		// 대상 시스템과 업무영향의 상관 관계에서 나온 최종 결과 값 추출
		if (targetSystem.equals("A") && effect.equals("A")) {
			resultOne = "A";
		} else if (targetSystem.equals("A") && effect.equals("B")
				|| targetSystem.equals("B") && effect.equals("A")
				|| targetSystem.equals("B") && effect.equals("B")) {
			resultOne = "B";
		} else if (targetSystem.equals("A") && effect.equals("C")
				|| targetSystem.equals("B") && effect.equals("C")
				|| targetSystem.equals("C") && effect.equals("A")
				|| targetSystem.equals("C") && effect.equals("B")) {
			resultOne = "C";
		} else if (targetSystem.equals("A") && effect.equals("D")
				|| targetSystem.equals("B") && effect.equals("D")
				|| targetSystem.equals("C") && effect.equals("C")
				|| targetSystem.equals("C") && effect.equals("D")) {
			resultOne = "D";
		}

		// 장애범위와 장애시간의 상관관계에서 나온 결과 값 도출
		if (effectRange.equals("B") && effectTime.equals("B")
				|| effectRange.equals("B") && effectTime.equals("C")
				|| effectRange.equals("C") && effectTime.equals("B")) {
			resultTwo = "B";
		} else if (effectRange.equals("B") && effectTime.equals("D")
				|| effectRange.equals("C") && effectTime.equals("C")
				|| effectRange.equals("C") && effectTime.equals("D")
				|| effectRange.equals("D") && effectTime.equals("B")
				|| effectRange.equals("D") && effectTime.equals("C")) {
			resultTwo = "C";
		} else if (effectRange.equals("D") && effectTime.equals("D")) {
			resultTwo = "D";
		}

		// 종합적인 등급 결과 도출
		if (resultOne.equals("A")
				&& (resultTwo.equals("B") || resultTwo.equals("C"))
				|| resultOne.equals("B") && resultTwo.equals("B")) {
			result = "A";
		} else if (resultOne.equals("A") && resultTwo.equals("D")
				|| resultOne.equals("B") && resultTwo.equals("C")
				|| resultOne.equals("C") && resultTwo.equals("B")) {
			result = "B";
		} else if (resultOne.equals("B") && resultTwo.equals("D")
				|| resultOne.equals("C") && resultTwo.equals("C")
				|| resultOne.equals("D") && resultTwo.equals("B")) {
			result = "C";
		} else if (resultOne.equals("C") && resultTwo.equals("D")
				|| resultOne.equals("D")
				&& (resultTwo.equals("C") || resultTwo.equals("D"))) {
			result = "D";
		}

		return result;
	}

	// 장애 등급에 해당하는 상황전파대상 선정.
	public String findEmployee(String grade) throws Exception {

		if (grade.equals("A")) {
			return "175582,175585";
		} else if (grade.equals("B")) {
			return "175582,175585";
		} else if (grade.equals("C")) {
			return "175582,175585";
		} else if (grade.equals("D")) {
			return "175582,175585";
		}

		return "Not";
	}

	// 장애 결과 블라썸 톡 알림에 푸시.
	@SuppressWarnings("null")
	public void actionBlossomPush(String receiver, String message)
			throws Exception {
		if (receiver != null && !receiver.equals("")) {

			BlossomPushVO blossomPushVO = new BlossomPushVO();
			String[] receiverArray;

			List<String> receiverList = new ArrayList<String>();

			receiverArray = receiver.replaceAll(" ", "").split(",");

			for (int i = 0; i < receiverArray.length; i++) {
				receiverList.add(receiverArray[i]);
			}

			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
			HttpSession session = request.getSession();

			blossomPushVO.setSystemCode("C6FC612C-F802-4A30-8CF4-3718636C1619"); // 시스템
																					// 코드
			blossomPushVO.setReciverIds(receiverList); // 보고대상자 사번 리스트
			blossomPushVO.setSenderId((String) session.getAttribute("id")); // 발신자
																			// 사번
			blossomPushVO.setHeader("장애 발생 알림"); // 분류 : 알림, 공지, 경고 등
			blossomPushVO.setMessage("* 　사번 : " + blossomPushVO.getSenderId()
					+ "\n"); // 제목
			blossomPushVO.setMessageBody(message.replaceAll("<br>", "\r\n")); // 본문
			blossomPushVO.setConnectionTimeout(5);

			BlossomPush blossomPush = new BlossomPush();
			BlossomPushResponseVO blossomPushResponseVO = null;

			try {
				blossomPushResponseVO = blossomPush.sendPush(blossomPushVO);

				logger.info("" + blossomPushResponseVO.getStatusCode());
				logger.info(blossomPushResponseVO.getReason());
				logger.info(blossomPushResponseVO.getContent());
			} catch (java.net.SocketTimeoutException e1) {
				logger.info("connection timeout...");
			} catch (Exception e2) {
				logger.info("Blossom push error occured...");
			}
		}
	}

	public List<HashMap<String, Object>> selectList(
			HashMap<String, Object> params) throws Exception{
		return sqlSessionTemplatePrimary.getMapper(DefectMapper.class).selectList(params);
	}

	public void insertData(HashMap<String, Object> params) {
		sqlSessionTemplatePrimary.getMapper(DefectMapper.class).insertData(params);
	}

	public void updateStatus(HashMap<String, Object> params) {
		sqlSessionTemplatePrimary.getMapper(DefectMapper.class).updateStatus(params);
	}

	// DefectVO의 데이터를 코드화하는 함수
	public DefectVO changeCode(DefectVO defect) throws ParseException {

		// 장애영향
		if (defect.getEffect().equals("중단, 사용불가")) {
			defect.setEffect("1");
		} else if (defect.getEffect().equals("지연, 처리오류, 제한적사용")) {
			defect.setEffect("2");
		} else if (defect.getEffect().equals("경미한영향, 불편")) {
			defect.setEffect("3");
		} else if (defect.getEffect().equals("영향없음")) {
			defect.setEffect("4");
		}

		// 대상시스템
		if (defect.getTargetSystem().equals("대고객시스템")) {
			defect.setTargetSystem("1");
		} else if (defect.getTargetSystem().equals("POS시스템")) {
			defect.setTargetSystem("2");
		} else if (defect.getTargetSystem().equals("영업시스템")) {
			defect.setTargetSystem("3");
		} else if (defect.getTargetSystem().equals("영업지원시스템")) {
			defect.setTargetSystem("4");
		} else if (defect.getTargetSystem().equals("비영업시스템")) {
			defect.setTargetSystem("5");
		}

		// 장애시스템 범위
		if (defect.getEffectRange().equals("전관계사")) {
			defect.setEffectRange("1");
		} else if (defect.getEffectRange().equals("일부관계사")) {
			defect.setEffectRange("2");
		} else if (defect.getEffectRange().equals("단일관계사")) {
			defect.setEffectRange("3");
		} else if (defect.getEffectRange().equals("전사업장")) {
			defect.setEffectRange("4");
		} else if (defect.getEffectRange().equals("일부사업장")) {
			defect.setEffectRange("5");
		} else if (defect.getEffectRange().equals("단일사업장")) {
			defect.setEffectRange("6");
		}

		// 장애시스템 종류
		if (defect.getSelBox().equals("그룹공통시스템")) {
			defect.setSelBox("1");
		}
		if (defect.getSelBox().equals("관계사시스템")) {
			defect.setSelBox("2");
		}

		return defect;
	}

	// 현재 상태 코드화하는 함수
	public String changeStatusToCode(String status) {
		String stat = null;

		if (status.equals("처리완료")) {
			stat = "2";
		}
		if (status.equals("미처리종료")) {
			stat = "3";
		}

		return stat;
	}

}
