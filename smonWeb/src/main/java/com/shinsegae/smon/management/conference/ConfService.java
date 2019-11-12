package com.shinsegae.smon.management.conference;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.model.ConfVO;

/*******************************
 * 기안서 기준 서비스
 * 
 * @author 175582
 * @since 2018.07.04
 *******************************/
@Service
public class ConfService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	protected static Logger logger = Logger.getLogger(ConfService.class.getName());

	public List<HashMap<String, Object>> selectBox() {
		logger.info("ConfService.selectBox");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).selectBox();
	}
	
	// 기안서 기준 조회
	public List<HashMap<String, Object>> selectList() {
		logger.info("ConfService.selectList");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).selectList();
	}
	
	// 기안서 기준 조회
	public List<HashMap<String, Object>> searchAttndList(ConfVO confVO) {
		logger.info("ConfService.searchAttndList");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).searchAttndList(confVO);
	}
	
	// 기안서 기준 조회
	public List<HashMap<String, Object>> selectAttndList(ConfVO confVO) {
		logger.info("ConfService.selectList");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).selectAttndList(confVO);
	}
	
	// 진행자 정보 조회
	public List<HashMap<String, Object>> searchHost(ConfVO confVO) {
		logger.info("ConfService.searchHost");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).searchHost(confVO);
	}
	
	// 진행자 정보 조회
	public String confHostYN(ConfVO confVO) {
		logger.info("ConfService.confHostYN");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).confHostYN(confVO);
	}
	
	

	// 회의 정보 가져오기
	public List<HashMap<String, Object>> selConfName(ConfVO confVO) {
		logger.info("ConfService.selConfName");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).selConfName(confVO);
	}

	// 기안서 저장
	public void insertData(ConfVO confVO) {
		logger.info("ConfService.insertData");
		if(confVO.getREGID().equals("")) {	// 만약 공유회 진행자가 비어있다면 로그인한 아이디로 진행자를 넣는다.
			logger.info(confVO.getLOGINID());
			confVO.setREGID(confVO.getLOGINID().toString());		
		}
		
		String[] splitId = confVO.getREGID().split(",");
		
		String confSeq = sqlSessionTemplatePrimary.getMapper(ConfMapper.class).selectConfSeq();
		confVO.setSEQ(confSeq);
		
		try{
			
			for(int i=0; i<splitId.length; i++) {
				confVO.setREGID(splitId[i]);
				sqlSessionTemplatePrimary.getMapper(ConfMapper.class).insertConf(confVO);
			}

			sqlSessionTemplatePrimary.getMapper(ConfMapper.class).insertAttnd(confVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 기안서 저장
	public void attndChng(ConfVO confVO) {
		logger.info("ConfService.attndChng");
		sqlSessionTemplatePrimary.getMapper(ConfMapper.class).attndChng(confVO);
	}
	
	// 공유회 삭제
	public void deleteConf(ConfVO confVO) {
		logger.info("ConfService.deleteConf");
		sqlSessionTemplatePrimary.getMapper(ConfMapper.class).deleteConf(confVO);
	}
	
	// 공유회 삭제
	public void deleteAttndList(ConfVO confVO) {
		logger.info("ConfService.deleteAttndList");
		sqlSessionTemplatePrimary.getMapper(ConfMapper.class).deleteAttndList(confVO);
	}

	// 기안서 삭제
	public void deleteData(ConfVO confVO) {
		logger.info("ConfService.deleteData");
		sqlSessionTemplatePrimary.getMapper(ConfMapper.class).deleteData(confVO);
	}

	// 기안서 업데이트
	public void updateData(ConfVO confVO) {
		logger.info("ConfService.updateData");
		sqlSessionTemplatePrimary.getMapper(ConfMapper.class).updateData(confVO);
	}
	
	// 참석자 차트 데이터 조회
	public List<HashMap<String, Object>> searchAttndResult(ConfVO confVO) {
		logger.info("ConfService.searchAttndResult");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).searchAttndResult(confVO);
	}
	
	// 진행자 차트 데이터 조회
	public List<HashMap<String, Object>> searchHostResult(ConfVO confVO) {
		logger.info("ConfService.searchHostResult");
		return sqlSessionTemplatePrimary.getMapper(ConfMapper.class).searchHostResult(confVO);
	}
}
