var g_page = 1;
var g_page2 = 1;
var g_intervalId;
var g_colid;
var g_colid2;
var g_sorting;
var g_sorting2;
var g_chkRank = 1;
var g_masterData;
var g_arrParam;
var table;

var obj_searchBtnRef;
var obj_saveBtnRef;

$(document).ready(function() {
	fn_start();
});

$.ajaxSetup({  
    global: false
}); 

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 290;

var tableTemplateHtml = null;
var tableRef = null;
var dataTable = null;
var searchBtnRef = null;
var obj_saveBtnRef = null;
var dbNameComboRef = null;
var ownerComboRef = null;
var infoTypeInputRef = null;
var exceptFlagComboRef = null;

/***********************
 * [공통] 초기화 
 ***********************/
function fn_start() {
	//fn_setDateCombo();	
	obj_searchBtnRef = $("#btn_search"); // 저장버튼
	obj_saveBtnRef = $("#btn_save"); // 저장버튼
	
	fn_event(); // 이벤트 바인딩	
	fn_search();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_event() {
	// 토글버튼
	$('#chk_toggle').change(function() {
		if ($(this).prop('checked')) {
			g_chkRank = 1;
		} else {
			g_chkRank = null;
		};
		fn_search(); 
	});
	
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
				g_colid = "SYSTEM_NAME";
				break;
			case "1" :
				g_colid = "PROJECT_NAME";
				break;
			case "2" :
				g_colid = "CHK_YMD";
				break;				
			case "3" :
				g_colid = "CHK_DGR";
				break;
			case "4" :
				g_colid = "PMD_CNT";
				break;
		}
		
		fn_search();
	}); 
	
	$("#dataTable2 thead tr th").click(function () {
		$("#dataTable2 thead tr th").not(this).removeClass("sorting");
		$("#dataTable2 thead tr th").not(this).removeClass("sorting_asc");
		$("#dataTable2 thead tr th").not(this).removeClass("sorting_desc");
		$("#dataTable2 thead tr th").not(this).addClass("sorting"); 
		
		if ($(this).hasClass("sorting")) {
			g_sorting2 = "ASC";
			$(this).removeClass("sorting");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_desc")) {
			g_sorting2 = "ASC";
			$(this).removeClass("sorting_desc");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_asc")) {
			g_sorting2 = "DESC";
			$(this).removeClass("sorting_asc");
			$(this).addClass("sorting_desc");
		} else {
			g_sorting2 = "ASC";
			$(this).addClass("sorting_asc");
		}
		
		var v_colidx = $(this).attr("colidx");
		
		switch (v_colidx) {
			case "0" :
				g_colid2 = "PRO_NO";
				break;
			case "1" :
				g_colid2 = "PACK_PATH";
				break;
			case "2" :
				g_colid2 = "FILE_PATH";
				break;				
			case "3" :
				g_colid2 = "PRIOR_NO";
				break;
			case "4" :
				g_colid2 = "LINE_NO";
				break;
			case "5" :
				g_colid2 = "PMD_DESC";
				break;
			case "6" :
				g_colid2 = "RULE_SET";
				break;
			case "7" :
				g_colid2 = "RULE";
				break;				
		}		
		
		fn_search2();
	}); 	
	

	
	//조회버튼 클릭
	obj_searchBtnRef.on("click", function() {
		fn_search();
		return;
		fn_search2();
	});
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
	});
}

/***********************
 * [공통] 조회
 ***********************/
function fn_search() {
	//var postData = $("#form1").serializeArray();	
	$('#dataTable2').dataTable().fnClearTable();
    $('#dataTable2').dataTable().fnDestroy();
    $('#div_count_1').empty();
    $('#div_page_1').empty();
	
	var postData = {
          groupCode : $("#groupCode").val().replace(/-/gi, "")
		, systemCode : $("#systemCode").val().replace(/-/gi, "")
		, chkRank : g_chkRank
		, pageScale : $("#pageScale").val()
		, curPage : g_page
		, colid : g_colid
		, sorting : g_sorting
	};
	var formURL = contextPath + "/tool/getPmdAggList.do";
	//$("#form1").attr("action");
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
			$.blockUI({ css: {color: '#fff'} });
		},
		complete : function() {
			$.unblockUI();
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리 되었습니다.");
				//alert(JSON.stringify(result.rstList));
				var v_result = result.rstList;
				var v_distinctSysList = result.distinctSysList;
				var v_pageVO = result.pageVO;
				var v_paramCondition = result.paramCondition;
				
				fn_pageDiv(result, 0); // 페이지 처리
				
				var v_str = "";
				var v_param = "";
				
				$.each(v_result, function (index, data) {
					v_param = "";
					
					v_param += data.groupCode;
					v_param += "^" + data.systemCode;
					v_param += "^" + data.projectName;
					v_param += "^" + data.chkYmd;
					v_param += "^" + data.chkDgr;
					
					v_str += '<tr>';
					v_str += '<td class="text-left"><b><font color="blue"><a href="#" onClick="fn_search2(\'' + v_param + '\');">' + data.systemName + '</a></font></b></td>';
					v_str += '<td class="text-left"><b><font color="blue"><a href="#" onClick="fn_search2(\'' + v_param + '\');">' + data.projectName + '</a></font></b></td>';
					v_str += '<td class="text-center"><b><font color="blue"><a href="#" onClick="fn_search2(\'' + v_param + '\');">' + data.chkYmd + '</a></font></b></td>';
					v_str += '<td class="text-center"><b><font color="blue"><a href="#" onClick="fn_search2(\'' + v_param + '\');">' + data.chkDgr + '</a></font></b></td>';
					v_str += '<td class="text-center"><b><font color="blue"><a href="#" onClick="fn_search2(\'' + v_param + '\');">' + comma(data.pmdCnt) + '</a></font></b></td>';
					v_str += '</tr>';
				});
				
				$("#table_tbody").html(v_str);
				
				fn_lineChart(v_result, v_distinctSysList);
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

/********************************
 * [차트] 선 그래프 작성
 ********************************/
function fn_lineChart(arg1, arg2) {
	debugger;
	var v_data, v_jsonData;
	
	var array = new Array(); // data array
	var array2 = new Array(); // systemCode array
	var array3 = new Array(); // systemName array
	
	$.each(arg2, function(key, data) {
		array2.push(data.groupCode + "_" +  data.systemCode + "_" +  data.projectName);
		array3.push(data.systemName + "(" +  data.projectName + ")");
	});
	
	$.each(arg1, function(index, item) {
		var obj = new Object();
		
		obj.label = item.chkYmd; // 라벨
		obj[item.groupCode + "_" +  item.systemCode + "_" +  item.projectName] = item.pmdCnt; // Y축 데이터
		
		array.push(obj);
	});
	
	v_jsonData = JSON.parse(JSON.stringify(array));
	
	//alert(JSON.stringify(array));
	//alert(JSON.stringify(array2));
	//alert(JSON.stringify(array3));
	
	//$("#morris-line-chart").empty();
	
	$("#morris-line-chart").remove();
	$("#morris").append("<div id='morris-line-chart'></div>");
	//return;
	
	Morris.Line({
        element: 'morris-line-chart',
        data: v_jsonData,
        xkey: 'label',
        ykeys: array2,
        labels: array3,
        pointSize: 2,
        //hideHover: 'auto',
        //resize: false
    });
    
    //$("div svg text").attr("style", "font-family:돋움;font-weight:bold");
}

/***********************
 * [공통] 상세 조회
 ***********************/
function fn_search2(p_args) {
	$('#dataTable2').dataTable().fnClearTable();
    $('#dataTable2').dataTable().fnDestroy();
    
    if (p_args != undefined) {
		g_arrParam = p_args.split("^");
	}
	
	//var postData = $("#form1").serializeArray();
	var postData = {
		  groupCode : g_arrParam[0].replace(/-/gi, "")
		, systemCode : g_arrParam[1].replace(/-/gi, "")
		, projectName : g_arrParam[2].replace(/-/gi, "")
		, chkYmd : g_arrParam[3].replace(/-/gi, "")
		, chkDgr : g_arrParam[4].replace(/-/gi, "")
		, pageScale : $("#pageScale2").val()
		, curPage : g_page2
		, colid : g_colid2
		, sorting : g_sorting2
	};
	var formURL = contextPath + "/tool/getPmdDetailList.do";
	//$("#form1").attr("action");
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
			$.blockUI({ css: {color: '#fff'} });
		},
		complete : function() {
			$.unblockUI();
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
				
				fn_pageDiv(result, 1); // 페이지 처리
				
				var v_str = "";
				
				$.each(v_result, function (index, data) {
					v_str += '<tr>';
					if (data.articleNo == -1) {
						v_str += '<td class="text-center"><b>' + data.rule + '</font></b></td>';
					} else {
						v_str += '<td class="text-center"><b><a href="#" onClick="openWiki(' + data.articleNo + ');">' + data.rule + '</a></font></b></td>';
					}
					v_str += '<td class="text-center"><b>' + data.proNo + '</a></b></td>';
					v_str += '<td class="text-center"><b><textarea rows=1 style="width:100%">' + data.packPath + '</textarea></b></td>';
					v_str += '<td class="text-center"><b><textarea rows=1 style="width:100%">' + data.filePath + '</textarea></b></td>';
					v_str += '<td class="text-center"><b>' + data.priorNo + '</b></td>';
					v_str += '<td class="text-center"><b>' + data.lineNo + '</b></td>';
					v_str += '<td class="text-center"><b><textarea rows=1 style="width:100%">' + data.pmdDesc + '</textarea></b></td>';
					v_str += '<td class="text-center"><b><textarea rows=1 style="width:100%">' + data.ruleSet + '</textarea></b></td>';
					//v_str += '<td class="text-center"><b><font color="blue"><a href="#" onClick="alert();">' + comma(data.pmdCnt) + '</a></font></b></td>';
					v_str += '</tr>';
				});
				
				$("#table_tbody2").html(v_str);
			} else {
				alert("에러가 발생하였습니다.")
			}
			$.unblockUI();
			
			$('#dataTable2').DataTable({
				bInfo : false,
		        bSort: false,
		        bPaginate: false,
		        bLengthChange: false,
		        bAutoWidth: false,
		        processing: false,
		        ordering: false,
		        order : [],
		        bServerSide: false,
		        searching: false,
		        dom: 'Bfrtip',
		        buttons: [
		            'copy', 'excel'
		        ]
		    });
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			$.unblockUI();
		}
	});
}	

/********************************
 * [기능] 위키 오픈
 ********************************/
function openWiki(p_articleNo) {
	var myWin = window.open("/wiki/detailWithoutTop.do?isNew=0&seq=" + p_articleNo, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=10,left=10,width=1280,height=800");
}

/********************************
 * [공통] 페이지변경
 ********************************/
function list(p_page, p_idx) {
	if (p_idx == 0) {
		g_page = p_page;
		fn_search();
	} else {
		g_page2 = p_page;
		fn_search2();
	}
}

/********************************
 * [공통] 페이징
 ********************************/
function fn_pageDiv(p_result, p_idx) {
	var p_obj = p_result.pageVO;
	var p_paramCondition = p_result.paramCondition;
	var p_count = p_result.count;
	
	var html = '';
	html += '<nav aria-label="Page navigation example">';
	html += '<ul class="pagination justify-content-center">';
	html += '<!-- **처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'1\',' + p_idx + ')">처음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">처음</a></li>';
	}
	html += '<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.prevPage + '\',' + p_idx + ')">이전</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">이전</a></li>';
	}
	html += '<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->'
	
	for (var idx=p_obj.blockBegin;idx<=p_obj.blockEnd;idx++) {
		if (p_obj.curPage == idx) {
			html += '<!-- **현재페이지이면 하이퍼링크 제거 -->';
			html += '<li class="page-item"><a class="page-link" style="background-color:#EAEAEA;"href="#"><span style="color:red;">' + idx + '</span></a></li>';
		} else {
			html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + idx + '\',' + p_idx + ')">' + idx + '</a></li>';
		}
	}

	html += '<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\',' + p_idx + ')">다음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\',' + p_idx + ')">다음</a></li>';
	}
	
	html += '<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\',' + p_idx + ')">끝</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\',' + p_idx + ')">끝</a></li>';
	}
	
	html += '</ul>';
	html += '</nav>';
	
	$("#div_page_" + p_idx).html(html);
	$("#div_count_" + p_idx).html('<span>Showing ' + p_paramCondition.start + ' to ' + p_paramCondition.end + ' of ' + p_count + ' entries</span>');
}

function comma(num){
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


