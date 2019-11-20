
var g_treeObj;
var g_setting; // zTree 세팅
var g_folderPath="";
var g_rstList;
var g_position = "";
var scales = ["hours", "days"];
var settings = {
          source: null,
          itemsPerPage: 7,
          months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
          dow: ["일", "월", "화", "수", "목", "금", "토"],
          startPos: new Date(),
          navigate: "buttons",
          scale: "days",
          useCookie: false,
          maxScale: "months",
          minScale: "hours",
          waitText: "Please wait...",
          onItemClick: function (data) { return; },
          onAddClick: function (data) { return; },
          onRender: function() { return; },
          scrollToToday: true,
          hours : ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"],
          //minutes : ["00", "10", "20", "30", "40", "50"],
          minutes : ["00","30"],
          labelWidthPx : 300,
          heightPx : 25,
          widthPx : 60,
          jobCount : 500
      };

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
	$("#baseDay").val(getToday());
	//$("#baseDay").val('2017-08-31');
	
	fn_zTreeInit(); // zTree 초기세팅
	fn_tableInit(); // 테이블 초기세팅
}

/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	// 배치잡 엔터키시
	$("#jobName").keypress(function(e){
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
	//var postData = $("#form1").serializeArray();
	var postData = {
		  baseDay : $("#baseDay").val().replace(/-/gi, "")
		, projectName : $("#projectName").val()
		, contextPath : contextPath
	};
	var formURL = contextPath + "/batch/listFolderJson.do";
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
			
			if (g_folderPath != "") {
				fn_searchDetail(g_folderPath); // 기존 폴더가 있을경우 검색
				
				var v_node = g_treeObj.getNodeByParam("id", g_folderPath);
				
				g_treeObj.selectNode(v_node);	
			} else {
				if ($("#jobName").val() != "") {
					fn_searchDetail(""); // 배치잡명있을경우					
				}
			}
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리 되었습니다.");
				//alert(JSON.stringify(result.rstList));
				
				g_treeObj = $.fn.zTree.init($("#treeDemo"), g_setting, result.rstList);
				
				//var v_node = g_treeObj.getNodeByParam("id", "\\Jobs\\기준정보\\DMS동기화");
				
				//g_treeObj.selectNode(v_node);				
				
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

/***********************
 * [공통] zTree 초기화 
 ***********************/
function fn_zTreeInit() {
	g_setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};
	/** 클릭 이전 이벤트 */
	function beforeClick(treeId, treeNode, clickFlag) {
		
	}
	/** 클릭 이벤트 */
	function onClick(event, treeId, treeNode, clickFlag) {
		g_folderPath = treeNode.id; // 폴더 path
		var v_lev = treeNode.lev;
		
		if (v_lev == 1 && $("#subYn").val()) {
			if (!confirm("검색이 오래걸릴 수 있습니다.\n조회하시겠습니까?")) {
				return;
			}
		}
		
		fn_searchDetail(g_folderPath);
	}
}

/***********************
 * [조회] 테이블 및 차트 그리기
 ***********************/
function fn_searchDetail(p_folderPath, p_index) {
	//var postData = $("#form1").serializeArray();
	//$("#form1").attr("action");
	
	var formURL = contextPath + "/batch/listDsBatchJson.do";
	
	if (p_index != null) {
		p_folderPath = g_rstList[p_index].subFolderPath;
	}
	
	if ($("#jobName").val() != "") {
		p_folderPath = "";
	}
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : {
			      folderPath : p_folderPath
			    , baseDay : $("#baseDay").val().replace(/-/gi, "")
			    , projectName : $("#projectName").val()
			    , jobName : $("#jobName").val()
			    , successYn : $("#successYn").val()
			    , subYn : $("#subYn").val()
			    , displaySub : $("#displaySub").val()
		       },
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
			$("#span_header").html("");
			$.blockUI({ css: { color: '#fff'} });
		},
		complete : function() {
			$.unblockUI();
			$("#jobName").val("");
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리 되었습니다.");
				//alert(JSON.stringify(result.rstList));
				
				g_rstList = result.rstList;
				
				fn_renderTable(g_rstList);
				g_folderPath = p_folderPath;
				var v_node = g_treeObj.getNodeByParam("id", g_folderPath);
				
				g_treeObj.selectNode(v_node); // 위치지정
				
				if ($("#jobName").val() == "") {
					$("#span_header").html("검색일자[" + $("#baseDay").val() + "], 조회경로 검색조건[" + p_folderPath + "]");
				} else {
					$("#span_header").html("검색일자[" + $("#baseDay").val() + "], 배치잡명 검색조건[" + $("#jobName").val() + "]");
				}
				
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


/***********************
 * [조회] 테이블 및 차트 초기화 
 ***********************/
function fn_tableInit() {
	$("#tableContents").css("width", settings.hours.length * settings.minutes.length * settings.widthPx + settings.labelWidthPx); // 테이블영역 세팅
	
	var v_jobThead = '<tr><th class="textCenter verticalCenter" style="width:' + (settings.labelWidthPx - 50) + 'px;">구분</th><th class="textCenter verticalCenter" style="width:50px;">성공률</th></tr>';
	
	$("#jobTable thead").html(v_jobThead);
	$("#jobTable").css("width", settings.labelWidthPx);
	$("#jobTable thead th.label_gubun").css("width", settings.labelWidthPx);
	$("#jobTable thead th").css("height", settings.heightPx * 3); // 헤더 전체 칸 	
	
	// thead 생성
	var thead = '';
	var theadRow1 = '<tr><th id="headRow1Td_' + "00" + '" class="textLeft verticalCenter" colspan="' + settings.hours.length * settings.minutes.length + '"><b><span id="span_header"></span></b></th></tr>';
	var theadRow2 = '';
	var theadRow3 = '';
	
	theadRow2 += '<tr>';
	for (var i = 0; i < settings.hours.length; i++) {
		theadRow2 += '<th id="headRow2Td_' + settings.hours[i] + '" class="textCenter verticalCenter" colspan="' + settings.minutes.length + '">' + settings.hours[i] + '시</th>';
	}
	theadRow2 += '</tr>';
	
	theadRow3 += '<tr>';
	for (var i = 0; i < settings.hours.length; i++) {
		for (var j = 0; j < settings.minutes.length; j++) {
			theadRow3 += '<th id="headRow3Td_' + settings.hours[i] + settings.minutes[j] + '" class="textCenter verticalCenter" style="width:20px;">' + settings.minutes[j] + '</th>';
		}
	}
	theadRow3 += '</tr>';
	
	thead += theadRow1;
	thead += theadRow2;
	thead += theadRow3;
	
	$("#chartTable thead").html(thead);
	$("#chartTable").css("width", settings.hours.length * settings.minutes.length * settings.widthPx);
	$("#chartTable thead th.label_gubun").css("width", settings.labelWidthPx);
	$("#chartTable thead [id^='headRow1Td']").css("width", settings.hours.length * settings.minutes.length * settings.widthPx); //헤더 첫째
	$("#chartTable thead [id^='headRow2Td']").css("width", settings.minutes.length * settings.widthPx); // 헤더 둘째
	$("#chartTable thead [id^='headRow3Td']").css("width", settings.widthPx); // 헤더 셋째
	$("#chartTable thead [id^='headRow']").css("height", settings.heightPx); // 헤더 전체 칸 
}

/***********************
 * [조회] 테이블 및 차트 그리기 
 ***********************/
function fn_renderTable(p_obj) {
	// tbody 생성
	var v_jobCount = 0;
	var v_jobTbody = '';
	var tbody = '';
	var v_jobIdRank, v_tempJobIdRank;
	var v_topPx = 0;
	var v_leftPx = 0;
	var v_lengthPx = 0;
	
	$("div [id^='jobBar_']").remove(); // 초기화
	$("div [id^='hlBar_']").remove(); // 초기화

	for (var k = 0; k < p_obj.length; k++) {
		v_tempJobIdRank = p_obj[k].jobIdRank; // 일감 ID 랭킹
		v_topPx = (settings.heightPx * 3) + ((v_tempJobIdRank - 1) * settings.heightPx); // top 위치 Px 
		v_leftPx = p_obj[k].leftPx; // left 위치 Px
		v_lengthPx = p_obj[k].lengthPx == 0 ? 2 : p_obj[k].lengthPx; // 길이 Px
		
		// job 그래프 표시
		$("#chartDiv").append('<div id="jobBar_' + k  + '" style="width:' + v_lengthPx + 'px;height:' + (settings.heightPx - 12) + 'px;top:' + (v_topPx + 6) + 'px;left:' + v_leftPx + 'px;background-color:' + getColorStatus(p_obj[k].runMinorStatus) + ';position:absolute;z-index:3;border-radius:3px;"></div>');
		
		$("#jobBar_" + k).qtip({ // Grab some elements to apply the tooltip to
		    id: p_obj[k].id,
		    content: {
		          text: '상&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;태 : ' + p_obj[k].runMinorStatus 
		              + '<br>시작일시 : ' + p_obj[k].staDate
		              + '<br>종료일시 : ' + p_obj[k].endDate
		              
		        , title: p_obj[k].jobName    
		    }
		});
		
		$("#jobBar_" + k).qtip();
		
		// jobIdRank별 Row 생성
		if (v_jobIdRank != v_tempJobIdRank) { 
			v_FOK_cnt = 0; // FOK 개수
			v_FIN_cnt = 0; // FIN 개수
			
			v_jobCount++;
			
			// 하이라이트
			$("#tableContents").append('<div class="displayNone" id="hlBar_' + v_jobCount  + '" style="width:' + '1310' + 'px;height:' + settings.heightPx + 'px;top:' + (settings.heightPx * v_jobCount + 50) + 'px;left:' + '0px;' + 'px;background-color:' + '#FFFFC6' + ';opacity:1;position:absolute;z-index:1;"></div>');
			
			if (p_obj[k].subFolderPath == "") {
				v_jobTbody += '<tr><td style="width:' + settings.labelWidthPx + 'px;height:' + settings.heightPx + 'px;">' + p_obj[k].jobName
				+ '</td><td class="textRight">' + (p_obj[k].totCnt == 0 ? 0 : Math.round(p_obj[k].fokCnt / p_obj[k].totCnt * 100)) + '%</td></tr>';
				
			} else {
				if ($("#displaySub").val() == "Y") {
					v_jobTbody += '<tr><td style="width:' + settings.labelWidthPx + 'px;height:' + settings.heightPx + 'px;"><a href="#" onClick="fn_searchDetail(\'\', ' + k + ');">' + p_obj[k].jobName + '(하:' + p_obj[k].subJobCnt + ')</a>' 
					   + '</td><td class="textRight">' + (p_obj[k].totCnt == 0 ? 0 : Math.round(p_obj[k].fokCnt / p_obj[k].totCnt * 100)) + '%</td></tr>';
				} else {
					v_jobTbody += '<tr><td style="width:' + settings.labelWidthPx + 'px;height:' + settings.heightPx + 'px;"><a href="#" onClick="fn_searchDetail(\'\', ' + k + ');">' + p_obj[k].jobName + '</a>' 
					   + '</td><td class="textRight">' + (p_obj[k].totCnt == 0 ? 0 : Math.round(p_obj[k].fokCnt / p_obj[k].totCnt * 100)) + '%</td></tr>';
				}
				
			}
			tbody += '<tr>';
			//tbody += '<td style="width:' + settings.labelWidthPx + 'px;height:' + settings.heightPx + 'px;">' + p_obj[k].jobName + '_' + p_obj[k].jobIdRank + '</td>';
			
			for (var i = 0; i < settings.hours.length; i++) {
				for (var j = 0; j < settings.minutes.length; j++) {
					if (k==0) {
						//tbody += '<td id="bodyRowTd_' + settings.hours[i] + settings.minutes[j] + '" class="textCenter" style="width:' + settings.widthPx + 'px;" rowspan="' + settings.jobCount + '"></td>';
						tbody += '<td id="bodyRowTd_' + settings.hours[i] + settings.minutes[j] + '" class="textCenter" style="width:' + settings.widthPx + 'px;"></td>';
					}
				}
			}
		
			tbody += '</tr>';
			
			v_jobIdRank = v_tempJobIdRank; // jobIdRank 세팅(임시 -> 세팅) 
		}
	}
	
	$("#jobTable tbody").html(v_jobTbody);
	$("#chartTable tbody").html(tbody);
	$("#chartTable tbody [id^='bodyRowTd_']").css("height", settings.heightPx * v_jobCount); // 바디 전체 칸 Height 세팅
	$("#chartTable tbody [id^='bodyRowTd_']").css("height", settings.heightPx * v_jobCount); // 바디 전체 칸 Height 세팅
	$("#chartDiv").css("height", 108 + settings.heightPx * v_jobCount); // 잡 개수 만큼 height 변경
	$("#jobDiv").css("height", 108 + settings.heightPx * v_jobCount); // 잡 개수 만큼 height 변경
	
	// 하이라이트 이벤트
	$("div [id^='jobBar_']").on("click", function () {
		var v_index = this.id.split("_")[1]; // 인덱스 가져오기
		
		if (g_position != "") $("#hlBar_" + g_position).addClass("displayNone");
		
		g_position = g_rstList[v_index].jobIdRank; // 위치 가져오기
		
		$("#hlBar_" + g_position).removeClass("displayNone");
	}); // 초기화

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
