package com.shinsegae.smon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UrlStatisticsDTO {
	private String url;
	private String personalYN;
	private String avgExecTime;
	private String recExecTime;
	private String reqCnt;
	
	/** 조회조건 **/
	private String colid;
	private String sorting;
	
	/** 페이지 처리 */
	int start;
	int end;
	int curPage;
	int pageScale;
	
	public UrlStatisticsDTO() {
		super();
	}
	public UrlStatisticsDTO(String url, String personalYN, String avgExecTime,
			String recExecTime, String reqCnt, String colid, String sorting,
			int start, int end, int curPage, int pageScale) {
		super();
		this.url = url;
		this.personalYN = personalYN;
		this.avgExecTime = avgExecTime;
		this.recExecTime = recExecTime;
		this.reqCnt = reqCnt;
		this.colid = colid;
		this.sorting = sorting;
		this.start = start;
		this.end = end;
		this.curPage = curPage;
		this.pageScale = pageScale;
	}	
}