package com.shinsegae.smon.online;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.online.WikiService;
import com.ssg.blossompush.lib.BlossomPush;
import com.ssg.blossompush.vo.BlossomPushResponseVO;
import com.ssg.blossompush.vo.BlossomPushVO;
import com.shinsegae.smon.util.CastingJson;
import com.shinsegae.smon.util.ConstantsInterface;
import com.shinsegae.smon.util.FileDTO;

import sun.util.logging.resources.logging;

@RequestMapping("/wiki")
@Controller
public class WikiController {

	@Autowired
	WikiService wikiService;

	@Autowired
	CastingJson json;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wiki/listWiki");

		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/list", method = RequestMethod.POST)
	public List<HashMap<String, Object>> selectList(@RequestParam(value = "workCode") String workCode,
			@RequestParam(value = "keywordTitle") String keywordTitle,
			@RequestParam(value = "keywordContents") String keywordContents) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("workCode", workCode);
		params.put("keywordTitle", keywordTitle);
		params.put("keywordContents", keywordContents);

		List<HashMap<String, Object>> list = wikiService.selectList(params);

		return list;

	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detailView(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("seq", req.getParameter("seq"));
		mv.addObject("isNew", req.getParameter("isNew"));
		mv.setViewName("wiki/detailWiki");

		return mv;
	}

	@RequestMapping(value = "/detailWithoutTop", method = RequestMethod.GET)
	public ModelAndView detailWidoutTopView(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("seq", req.getParameter("seq"));
		mv.addObject("isNew", req.getParameter("isNew"));
		mv.setViewName("wiki/detailWikiWithoutTop");

		return mv;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editView(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("seq", req.getParameter("seq"));
		mv.addObject("isNew", req.getParameter("isNew"));
		mv.addObject("empno", (String) req.getSession().getAttribute("id"));
		mv.setViewName("wiki/editWiki");

		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/edit/upload", method = RequestMethod.POST)
	public String editorUpload(HttpServletRequest req, HttpServletResponse res, FileDTO dto) throws IOException {

		MultipartFile uploadfile = dto.getUploadfile();
		String imgDirPath = ConstantsInterface.IMG_PATH_DIR + dto.getSeq() + "/";

		File dir = new File(imgDirPath);

		// 해당되는 디렉토리가 없다면 디렉토리 생성하자
		if (!dir.isDirectory()) {
			if (!dir.mkdirs()) {
				System.out.println("디렉토리 생성 권한 없음..");
			}
		}

		String fileName = "";

		// 객체가 존재한다면
		if (uploadfile != null) {

			Date date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String currentTimeToString = null;
			// 이미지라면..
			if (dto.getImageGubn().equals("1")) {
				currentTimeToString = transFormat.format(date);
				fileName = currentTimeToString + "_" + uploadfile.getOriginalFilename();
			} else {// 파일이라면
				fileName = uploadfile.getOriginalFilename();
			}
			dto.setFileName(fileName);

			try {
				File file = new File(imgDirPath + fileName);
				uploadfile.transferTo(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ConstantsInterface.IMG_URL + dto.getSeq() + "/" + fileName;
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/edit/select", method = RequestMethod.POST)
	public HashMap<String, Object> selectWiki(@RequestParam(value = "seq") String seq) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("seq", seq);

		HashMap<String, Object> data = wikiService.selectWiki(params);

		ArrayList<HashMap<String, String>> list = wikiService.selectFileList(seq);
		// System.out.println("22222");

		if (list != null) {
			data.put("FILE_LIST", wikiService.selectFileList(seq));
		}

		return data;

	}

	@ResponseBody
	@RequestMapping(value = "/ajax/edit/save", method = RequestMethod.POST)
	public String saveWiki(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "isNew") String isNew, @RequestParam(value = "title") String title,
			@RequestParam(value = "workCode") String workCode, @RequestParam(value = "contentsH") String contentsH,
			@RequestParam(value = "contentsS") String contentsS,
			@RequestParam(value = "seq", required = false) String seq) throws UnsupportedEncodingException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("isNew", isNew);
		params.put("title", title);
		params.put("workCode", workCode);
		params.put("contentsH", contentsH);
		params.put("contentsS", contentsS);
		params.put("seq", seq);
		params.put("empno", (String) request.getSession().getAttribute("id"));

		wikiService.save(params);

		return "1";

	}

	@ResponseBody
	@RequestMapping(value = "/ajax/detail/select", method = RequestMethod.POST)
	public HashMap<String, Object> selectWikiDetail(@RequestParam(value = "seq") String seq) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("seq", seq);

		HashMap<String, Object> data = wikiService.selectWiki(params);

		return data;

	}

	@ResponseBody
	@RequestMapping(value = "/ajax/detail/share", method = RequestMethod.POST)
	public String saveWiki(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "receivers") String receivers, @RequestParam(value = "shareTitle") String shareTitle,
			@RequestParam(value = "seq") String seq) throws UnsupportedEncodingException {
		BlossomPushVO blossomPushVO = new BlossomPushVO();

		StringBuilder sb = new StringBuilder();
		sb.append("\n다음 링크는 내부망에서 접속 가능합니다.\n");

		sb.append("http://" + req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath()
				+ "/wiki/detail.do?seq=" + seq);

		blossomPushVO.setSystemCode("C6FC612C-F802-4A30-8CF4-3718636C1619"); // 연동 시스템 코드. 블라썸팀에 요청하여 발급.
		blossomPushVO.setReceiverId(receivers); // 단일수신자(사번) 지정
		blossomPushVO.setHeader("[업무가이드 공유]\n"); // 헤더
		blossomPushVO.setMessage("\n* 공유제목 : " + shareTitle); // 제목
		blossomPushVO.setMessageBody(sb.toString()); // 본문
		blossomPushVO.setSenderId("p905z1"); // 발신자 사번

		// connection timeout setting
		blossomPushVO.setConnectionTimeout(5); // 5 seconds (default 10 seconds)

		BlossomPush blossomPush = new BlossomPush();
		BlossomPushResponseVO blossomPushResponseVO = null;
		try {
			blossomPushResponseVO = blossomPush.sendPush(blossomPushVO);

			System.out.println(blossomPushResponseVO.getStatusCode()); // http response status
			System.out.println(blossomPushResponseVO.getReason()); // http response reason
			System.out.println(blossomPushResponseVO.getContent()); // http response content
		} catch (java.net.SocketTimeoutException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}

		return "1";

	}

	@ResponseBody
	@RequestMapping(value = "/ajax/detail/selectSequence", method = RequestMethod.POST)
	public String selectWikiSequence() {
		return wikiService.selectSequence();
	}

	@RequestMapping(value = "/ajax/detail/callDownload")
	public ModelAndView callDownload(@RequestParam String sequence, @RequestParam String fileName,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		File downloadFile = new File(ConstantsInterface.IMG_PATH_DIR + sequence + '/' + fileName);

		if (!downloadFile.canRead()) {
			throw new Exception(
					"Fil e can't read(파일을 찾을 수 없습니다)\n" + ConstantsInterface.IMG_PATH_DIR + sequence + '/' + fileName);
		}

		return new ModelAndView("fileDownloadView", "downloadFile", downloadFile);

	}

}