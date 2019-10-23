package com.shinsegae.smon.online;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.online.WikiDAO;
import com.shinsegae.smon.util.ConstantsInterface;

@Service
public class WikiService {

	@Autowired
	WikiDAO wikiDao;

	public List<HashMap<String, Object>> selectList(HashMap<String, Object> params) {
		return wikiDao.selectList(params);
	}

	public HashMap<String, Object> selectWiki(HashMap<String, Object> params) {
		return wikiDao.selectWiki(params);
	}

	public void save(HashMap<String, Object> params) {
		if (String.valueOf(params.get("isNew")).equals("1")) {
			wikiDao.addWiki(params);
		} else if (String.valueOf(params.get("isNew")).equals("0")) {
			wikiDao.editWiki(params);
		} else {
			System.out.println("isNew is empty...");
		}
	}

	public String selectSequence() {

		return wikiDao.selectSequence();
	}

	public ArrayList<HashMap<String, String>> selectFileList(String seq) {
		String path = ConstantsInterface.IMG_PATH_DIR + seq + "/";
		String url = ConstantsInterface.IMG_URL + seq + "/";

		ArrayList<HashMap<String, String>> fileArrayList = new ArrayList<HashMap<String, String>>();
		File dirFile = new File(path);

		if (dirFile != null) {
			File[] fileList = dirFile.listFiles();
			if (fileList != null) {
				for (File tempFile : fileList) {
					if (tempFile.isFile()) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("PATH", url);
						map.put("FILEN_NAME", tempFile.getName());
						fileArrayList.add(map);
					}
				}
			}
		}

		return fileArrayList;

	}

//	public List<HashMap<String, Object>> selectSystemComboList(HashMap<String, Object> params) {
//		return commonDao.selectSystemComboList(params);
//	}
//	
//	public List<HashMap<String, Object>> selectSaveTypeComboList(HashMap<String, Object> params) {
//		return commonDao.selectSaveTypeComboList(params);
//	}
//	
//	public List<HashMap<String, Object>> selectObjectTypeComboList(HashMap<String, Object> params) {
//		return commonDao.selectObjectTypeComboList(params);
//	}
//	
//	public List<HashMap<String, Object>> selectPersonalInfoTypeComboList(HashMap<String, Object> params) {
//		return commonDao.selectPersonalInfoTypeComboList(params);
//	}
//	
//	public List<HashMap<String, Object>> selectDataTypeComboList(HashMap<String, Object> params) {
//		return commonDao.selectDataTypeComboList(params);
//	}
//	
//	public List<HashMap<String, Object>> selectActionTypeComboList(HashMap<String, Object> params) {
//		return commonDao.selectActionTypeComboList(params);
//	}

}
