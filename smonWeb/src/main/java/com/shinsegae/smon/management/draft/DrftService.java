package com.shinsegae.smon.management.draft;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.model.DrftVO;

/*******************************
 * 기안서 기준 서비스
 * 
 * @author 175582
 * @since 2018.07.04
 *******************************/
@Service
public class DrftService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	protected static Logger logger = Logger.getLogger(DrftService.class.getName());

	public List<HashMap<String, Object>> selectBox() {
		logger.info("DrftService.selectBox");
		return sqlSessionTemplatePrimary.getMapper(DrftMapper.class).selectBox();
	}
	
	// 기안서 기준 조회
	public List<HashMap<String, Object>> selectList() {
		logger.info("DrftService.selectList");
		return sqlSessionTemplatePrimary.getMapper(DrftMapper.class).selectList();
	}

	// 기안서 저장
	public void insertData(DrftVO drftVO) {
		logger.info("DrftService.insertData");
		sqlSessionTemplatePrimary.getMapper(DrftMapper.class).insertData(drftVO);
	}

	// 기안서 삭제
	public void deleteData(DrftVO drftVO) {
		logger.info("DrftService.deleteData");
		sqlSessionTemplatePrimary.getMapper(DrftMapper.class).deleteData(drftVO);
	}

	// 기안서 업데이트
	public void updateData(DrftVO drftVO) {
		logger.info("DrftService.updateData");
		sqlSessionTemplatePrimary.getMapper(DrftMapper.class).updateData(drftVO);
	}
}
