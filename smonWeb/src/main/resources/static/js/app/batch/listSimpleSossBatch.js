
var g_page = 1;
var g_intervalId;
var g_colid;
var g_sorting;

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	fn_start();
	fn_event();
	fn_search();
});

$.ajaxSetup({  
    global: false
}); 


/***********************
 * [공통] 시작
 ***********************/
function fn_start() {
	$("#chk_toggle").bootstrapToggle('off');
	
	$("#baseDay").val(getToday());
	
	$("#toggle").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {
	          text: '10초마다 자동갱신'
	        , title: '자동갱신여부'    
	    }
	});
	
	$("#dataTable thead tr th").addClass("sorting"); 
	
	/*
	$("#dataTable").dataTable({
		responsive : true
	});	
	
	g_dataTable = $("#dataTable");
	*/
}

/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	$("#dataTable thead tr th").click(function () {
		$("#dataTable thead tr th").not(this).removeClass("sorting");
		$("#dataTable thead tr th").not(this).removeClass("sorting_asc");
		$("#dataTable thead tr th").not(this).removeClass("sorting_desc");
		$("#dataTable thead tr th").not(this).addClass("sorting"); 
		
		if ($(this).hasClass("sorting")) {
			g_sorting = "ASC";
			$(this).removeClass("sorting");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_desc")) {
			g_sorting = "ASC";
			$(this).removeClass("sorting_desc");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_asc")) {
			g_sorting = "DESC";
			$(this).removeClass("sorting_asc");
			$(this).addClass("sorting_desc");
		} else {
			g_sorting = "ASC";
			$(this).addClass("sorting_asc");
		}
		
		var v_colidx = $(this).attr("colidx");
		
		switch (v_colidx) {
			case "0" :
				g_colid = "PROJECT_NAME";
				break;
			case "1" :
				g_colid = "JOB_NAME";
				break;
			case "2" :
				g_colid = "FOLDER_PATH";
				break;				
			case "3" :
				g_colid = "STA_DAY||STA_TIME";
				break;
			case "4" :
				g_colid = "END_DAY||END_TIME";
				break;
			case "5" :
				g_colid = "ELAPSE_TIME";
				break;
			case "6" :
				g_colid = "RUN_MAJOR_STATUS";
				break;
			case "7" :
				g_colid = "RUN_MINOR_STATUS";
				break;
			case "8" :
				g_colid = "MASTER_PID";
				break;
			case "9" :
				g_colid = "TOTALCPU";
				break;				
		}
		
		//alert(g_colid + g_sorting);
		
		fn_search();
	}); 
	
	$('#chk_toggle').change(function() {
	  if ($(this).prop('checked')) {
		  g_intervalId = setInterval(function() { 
			  fn_search(); 
		  }, 10000);
	  } else {
		  clearInterval(g_intervalId);
	  };
	}); // does not work	
	
	// 배치잡 엔터키시
	$("#jobName").keypress(function(e){
        if (e.which == 13) {
        	fn_search();
        }
    });
	
	// 배치잡 엔터키시
	$("#folderPath").keypress(function(e){
        if (e.which == 13) {
        	fn_search();
        }
    });
	
	// 조회버튼 클릭
	$("#btn_search").on("click", function() {
		fn_search();
	});
	
	$("#span_prev").on("click", function() {
		var v_baseDay = $("#baseDay").val();
		
		if (v_baseDay.length != 10) {
			alert("날짜 자리수가 맞지 않습니다.");
			return;
		}
		
		var v_obj = getDayObj(v_baseDay);
		
		$("#baseDay").val(v_obj.prevDay);
	});
	
	$("#span_next").on("click", function() {
		var v_baseDay = $("#baseDay").val();
		
		if (v_baseDay.length != 10) {
			alert("날짜 자리수가 맞지 않습니다.");
			return;
		}
		
		var v_obj = getDayObj(v_baseDay);
		
		$("#baseDay").val(v_obj.nextDay);
	});
	
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		//$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
	});
	
	$('#baseDay').datepicker({
		userCurrent: false,
		dateFormat: "yy-mm-dd",
		setDate: ""
	});
}

/***********************
 * [공통] 조회(좌측 트리)
 ***********************/
function fn_search() {
	var v_totCnt = 0;
	var v_okCnt  = 0;
	var v_failCnt = 0;
	var v_rate = 0;
	
	//var postData = $("#form1").serializeArray();
	var postData = {
		  baseDay : $("#baseDay").val().replace(/-/gi, "")
		, projectName : $("#projectName").val()
		, jobName : $("#jobName").val()
		, folderPath : $("#folderPath").val()
		, successYn : $("#successYn").val()
		, contextPath : contextPath
		, pageScale : $("#pageScale").val()
		, curPage : g_page
		, colid : g_colid
		, sorting : g_sorting
	};
	var formURL = contextPath + "/batch/listSimpleSossBatchJson.do";
	//$("#form1").attr("action");
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend: function (xhr, settings) { 
			xhr.setRequestHeader(header, token); 
			$.blockUI({ css: {color: '#fff'} });
		},
		complete : function() {
			$.unblockUI();
			$("#span_totCnt").html(v_totCnt);
			$("#span_okCnt").html(v_okCnt);
			$("#span_failCnt").html(v_failCnt);
			$("#span_rate").html(v_rate);
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리 되었습니다.");
				//alert(JSON.stringify(result.rstList));
				
				var v_result = result.rstList;
				var v_pageVO = result.pageVO;
				var v_paramCondition = result.paramCondition;
				
				
				pageDiv(result); // 페이지 처리
				
				var v_str = "";
				
				$.each(v_result, function (index, data) {
					if (index==0) {
						v_totCnt = comma(data.totCnt);
						v_okCnt = comma(data.totOkCnt);
						v_failCnt = comma(data.totCnt - data.totOkCnt);
						v_rate = data.totRate;
					}
					
					v_str += '<tr>';
					v_str += '<td class="text-center">' + data.projectName + '</td>';
					v_str += '<td class="text-left">' + data.jobName + '</td>';
					v_str += '<td class="text-left">' + data.folderPath + '</td>';
					v_str += '<td class="text-center">' + data.staDay + " " + data.staTime + '</td>';
					v_str += '<td class="text-center">' + data.endDay + " " + data.endTime + '</td>';
					v_str += '<td class="text-center">' + data.elapseTime + '</td>';
					v_str += '<td class="text-center">' + data.runMajorStatus + '</td>';
					v_str += '<td class="text-center" style="font-weight:bold;color:'+ getColorStatus(data.runMinorStatus) +'">' + data.runMinorStatus + '</td>';
					v_str += '<td class="text-center">' + data.masterPid + '</td>';
					v_str += '<td class="text-center">' + data.totalCpu + '</td>';
					v_str += '</tr>';
				});
				
				$("#table_tbody").html(v_str);
			} else {
				alert("에러가 발생하였습니다.")
			}
			$.unblockUI();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			$.unblockUI();
		}
	});
}

function getToday() {
	var v_date = new Date();
	
	return getFommattedDate(v_date);
}

function getDayObj(p_str) {
	var v_from = p_str.split("-");
	
	var v_inputDay = new Date(v_from[0], v_from[1] - 1, v_from[2]);
	var v_prevDay = new Date();
	var v_nextDay = new Date();
	
	v_prevDay.setTime(v_inputDay.getTime() - (24 * 60 * 60 * 1000)); // 이전날짜
	v_nextDay.setTime(v_inputDay.getTime() + (24 * 60 * 60 * 1000)); // 이후날짜
	
	var v_obj = new Object();
	
	v_obj.today = getFommattedDate(v_inputDay);
	v_obj.prevDay = getFommattedDate(v_prevDay);
	v_obj.nextDay = getFommattedDate(v_nextDay);
	
	return v_obj;
}

function getFommattedDate(p_date) {
	var v_year = p_date.getFullYear();
	var v_month = p_date.getMonth() + 1; // 0부터 시작하므로 1더함 더함
	var v_day = p_date.getDate();

	if (("" + v_month).length == 1) {
		v_month = "0" + v_month;
	}
	if (("" + v_day).length == 1) {
		v_day = "0" + v_day;
	}
	
	return v_year + "-" + v_month + "-" + v_day;
}

function getColorStatus(p_str) {
	var v_color = "";
	
	switch (p_str) {
		case "FOK" :
			v_color = "#0100FF";
			break;
		case "FWW" :
			v_color = "#FF0000";
			break;
		default :
			v_color = "#8C8C8C";
			break;
	}
	
	return v_color;
}

function list(p_page) {
	g_page = p_page;
	fn_search();
}

function pageDiv(p_result) {
	var p_obj = p_result.pageVO;
	var p_paramCondition = p_result.paramCondition;
	var p_count = p_result.count;
	
	var html = '';
	html += '<nav aria-label="Page navigation example">';
	html += '<ul class="pagination justify-content-center">';
	html += '<!-- **처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'1\')">처음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">처음</a></li>';
	}
	html += '<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.prevPage + '\')">이전</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">이전</a></li>';
	}
	html += '<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->'
	
	for (var idx=p_obj.blockBegin;idx<=p_obj.blockEnd;idx++) {
		if (p_obj.curPage == idx) {
			html += '<!-- **현재페이지이면 하이퍼링크 제거 -->';
			html += '<li class="page-item"><a class="page-link" style="background-color:#EAEAEA;"href="#"><span style="color:red;">' + idx + '</span></a></li>';
		} else {
			html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + idx + '\')">' + idx + '</a></li>';
		}
	}

	html += '<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\')">다음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\')">다음</a></li>';
	}
	
	html += '<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\')">끝</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\')">끝</a></li>';
	}
	
	html += '</ul>';
	html += '</nav>';
	
	$("#div_page").html(html);
	$("#div_count").html('<span>Showing ' + p_paramCondition.start + ' to ' + p_paramCondition.end + ' of ' + p_count + ' entries</span>');
}

function comma(num){
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

