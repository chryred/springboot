package com.shinsegae.smon.bat;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.model.bat.DsBatchVO;

/*******************************
 * 배치 서비스
 * @author 153712 김성일
 * @since 2017.04.29
 *******************************/
@Service
public class BatService {
	@Autowired
	@Qualifier("sqlSessionTemplate_EXA")
	SqlSessionTemplate sqlSessionTemplate_EXA;
	
	@Autowired
	@Qualifier("sqlSessionTemplate_SHUB")
	SqlSessionTemplate sqlSessionTemplate_SHUB;

	/*******************************
	 * 운영정보 배치 리스트 조회
	 * @param DsBatchVO
	 * @return List<DsBatchVO>
	 * @throws Exception
	 *******************************/
	public List<DsBatchVO> getSossBatchList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_EXA.getMapper(BatExaMapper.class).getSossBatchList(paramCondition);
	}

	/*******************************
	 * 운영정보 배치확인(DataStage)-폴더구조 조회
	 * @param DsBatchVO
	 * @return List<DsBatchVO>
	 * @throws Exception
	 *******************************/	
	public List<DsBatchVO> getDsFolderList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_EXA.getMapper(BatExaMapper.class).getDsFolderList(paramCondition);
	}

	/*******************************
	 * DataStage-배치 실행 조회
	 * @param DsBatchVO
	 * @return List<DsBatchVO>
	 * @throws Exception
	 *******************************/	
	public List<DsBatchVO> getDsBatchList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_EXA.getMapper(BatExaMapper.class).getDsBatchList(paramCondition);
	}

	/*******************************
	 * 운영정보 배치 리스트(표) 조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/
	public List<DsBatchVO> getSimpleSossBatchList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_EXA.getMapper(BatExaMapper.class).getDsBatchList(paramCondition);
	}

	/*******************************
	 * 운영정보 배치 리스트(표) 조회 총 개수
	 * @param DsBatchVO
	 * @return int
	 * @throws Exception
	 *******************************/
	public int getCountSimpleSossBatchList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_EXA.getMapper(BatExaMapper.class).getCountSimpleSossBatchList(paramCondition);
	}
	
	/*******************************
	 * 스마트허브 배치확인(DataStage)-폴더구조 조회
	 * @param DsBatchVO
	 * @return List<DsBatchVO>
	 * @throws Exception
	 *******************************/	
	public List<DsBatchVO> getShubBatchFolderList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_SHUB.getMapper(BatShubMapper.class).getShubBatchFolderList(paramCondition);
	}

	/*******************************
	 * 스마트허브-배치 실행 조회
	 * @param DsBatchVO
	 * @return List<DsBatchVO>
	 * @throws Exception
	 *******************************/
	public List<DsBatchVO> getShubBatchList(DsBatchVO paramCondition) throws Exception {
		return sqlSessionTemplate_SHUB.getMapper(BatShubMapper.class).getShubBatchList(paramCondition);
	}
}
