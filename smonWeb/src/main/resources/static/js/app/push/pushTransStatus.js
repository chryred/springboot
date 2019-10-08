var table;

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
	fn_setDateCombo();
	fn_search();
	fn_search2();
	
	
	
	obj_searchBtnRef = $("#btn_search"); // 저장버튼
	obj_saveBtnRef = $("#btn_save"); // 저장버튼
	
	fn_staticBindEvent(); // 이벤트 바인딩
	
	/*tableRef = $("#dataTables");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	dbNameComboRef = $("#dbName");
	ownerComboRef = $("#owner");
	infoTypeInputRef = $("#infoType");
	exceptFlagComboRef = $("#exceptFlag");
	
	
	initComboDBName();
	initComboOwner();
	initTable();
	var clipboard = new Clipboard('.btn');*/
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_staticBindEvent() {
	//조회버튼 클릭
	obj_searchBtnRef.on("click", function() {
		fn_search();
		fn_search2();
	});
	
	//제외버튼 클릭
	obj_saveBtnRef.on("click", function() {
		fn_save();
	});
	
	$(':radio[name="dateGubun"]').on("click", function () {
		var v_value = $(this).val();
		
		switch (v_value) {
			case "Y" :
				$("#label_month").hide();
				$("#label_day").hide();
				break;
			case "M" :
				$("#label_month").show();
				$("#label_day").hide();
				break;
			case "D" :
				$("#label_month").show();
				$("#label_day").show();
				break;
		}
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

/********************************
 * 1. 전송률 도넛 차트
 ********************************/
function fn_search() {	
	var v_postData = $("#form1").serialize();
	var v_formUrl = contextPath + "/push/getCurrentTransRate.do";
	var v_data, v_jsonData;
	
	$.ajax({
		url : v_formUrl,
		type : "POST",
		data : v_postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : false,
		timeout : 2 * 60 * 60 * 1000, //2 hours,
		beforeSend : function() {
			//통신을 시작할때 처리되는 함수 
		},
		complete : function() {
			//통신이 완료된 후 처리되는 함수
		},
		success : function(result) {
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리되었습니다.");
				v_data = result.data;
				
				var array = new Array();
				
				$.each(result.data, function(index, item) {
					var obj = new Object();
					
					switch (item.STATE_CODE) {
						case "Y" :
							obj.label = "메시지 전송 완료(%)";
							break;
						case "N" :
							obj.label = "메시지 전송 대기(%)";
							break;
						case "E" :
							obj.label = "메시지 전송 실패(%)";
							break;
					}
					
					obj.value = item.TRANS_RATE;
					
					array.push(obj);
				});
				
				v_jsonData = JSON.parse(JSON.stringify(array));
				
				$("#morris-donut-chart").empty();
				
				Morris.Donut({
			        element: 'morris-donut-chart',
			        data: v_jsonData,
			        resize: true
			    });
			    
			    $("div svg text").attr("style", "font-family:돋움;font-weight:bold");
			} else {
				alert("에러가 발생하였습니다.")
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}

/********************************
 * 2. 데이터 테이블, 전송량 Area 차트, 전송률 Bar 차트 
 ********************************/
function fn_search2() {	
	var v_postData = $("#form1").serialize();
	var v_formUrl = contextPath + "/push/getTransStatusDivSystem.do";
	var v_data, v_systemList, v_jsonData;
	
	$.ajax({
		url : v_formUrl,
		type : "POST",
		data : v_postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : false,
		timeout : 2 * 60 * 60 * 1000, //2 hours,
		beforeSend : function() {
			//통신을 시작할때 처리되는 함수 
		},
		complete : function() {
			//통신이 완료된 후 처리되는 함수
		},
		success : function(result) {
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리되었습니다.");
				v_data = result.data; // 데이터
				v_systemList = result.systemList; // 시스템 리스트
				
				fn_dynamicTable(v_data, v_systemList);
				fn_areaChart(v_data, v_systemList);
				fn_barChart(v_data, v_systemList);
			} else {
				alert("에러가 발생하였습니다.")
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}

function fn_dynamicTable(arg1, arg2) {	
	var v_headHtml = "";
	var v_bodyHtml = "";
	var v_headHtml_row1 = "";
	var v_headHtml_row2 = "";
	
	v_headHtml_row1 += "<th rowspan='2'>구분</th>";	
	
	$.each(arg2, function (index, item) {
		v_headHtml_row1 += "<th colspan='2'>" + item.systemName + "</th>";
		v_headHtml_row2 += "<th>" + "건수" + "</th>" + "<th>" + "전송률" + "</th>";
	});
	
	v_headHtml += "<thead>";
	v_headHtml += "<tr>";
	v_headHtml += v_headHtml_row1;
	v_headHtml += "</tr>";
	v_headHtml += "<tr>";
	v_headHtml += v_headHtml_row2;
	v_headHtml += "</tr>";
	v_headHtml += "</thead>";

	$("#dataTable").empty();
	
	$("#dataTable thead").html(v_headHtml);
	
	v_bodyHtml += "<tbody>";
	$.each(arg1, function (index, item) {
		v_bodyHtml += "<tr>";
		v_bodyHtml += "<td class='text-center'>" + item["LABEL"] + "</td>";
		
		$.each(arg2, function (key, data) {
			v_bodyHtml += "<td class='text-right'>" + item[data.aliasCode + "_TOT_CNT"] + "</td>";
			v_bodyHtml += "<td class='text-right'>" + item[data.aliasCode + "_Y_RATE"] + "</td>";
		});
		
		v_bodyHtml += "</tr>";
	}); 
	v_bodyHtml += "</tbody>";
	
	$("#div_dataTable").empty();
	$("#div_dataTable").html('<table width="100%" class="table table-striped table-bordered table-hover" id="dataTable">' + v_headHtml + v_bodyHtml + '</table>');
	//if (table != null) table.clear();
	
	/*
	table = $("#dataTable").dataTable({
		
		data : [{name:"aaaa",name1:"aaaa",name2:"aaaa",name3:"aaaa",name4:"aaaa",name5:"aaaa",name6:"aaaa",name7:"aaaa",name8:"aaaa",name9:"aaaa"},
		        {name:"aaaa",name1:"aaaa",name2:"aaaa",name3:"aaaa",name4:"aaaa",name5:"aaaa",name6:"aaaa",name7:"aaaa",name8:"aaaa",name9:"aaaa"}],
		scrollY: '10vh',
		/*
		columns : [{title: "name", data:"name"},
		           {title: "name1", data:"name1"},
		           {title: "name2", data:"name2"},
		           {title: "name3", data:"name3"},
		           {title: "name4", data:"name4"},
		           {title: "name5", data:"name5"},
		           {title: "name6", data:"name6"},
		           {title: "name7", data:"name7"},
		           {title: "name8", data:"name8"},
		           {title: "name9", data:"name9"}],
		           
        fixHeader : {
        	header : true,  
        },	
		columnDefs : [{targets : [0], width: "30px"},
		              {targets : [1], width: "30px"},
		              {targets : [2], width: "30px"},
		              {targets : [3], width: "30px"},
		              {targets : [4], width: "30px"},
		              {targets : [5], width: "30px"},
		              {targets : [6], width: "30px"},
		              {targets : [7], width: "30px"},
		              {targets : [8], width: "30px"},
		              {targets : [9], width: "30px"}],
		              aoColumnDefs: [
		                               { "bVisible": true, "aTargets": [0] }
		                           ],
		                           
		ordering : false,
		paging : false,
		searching : false,
		bInfo : true,
		pageLength : 1000,
		processiong : true,
		scrollY : true,
		scrollX : true,
		responsive : false,
		destroy : true,
		language : {
            "lengthMenu" : "라인 수&nbsp; _MENU_ ",
            "search" : "검색:",
            "zeroRecords" : "조회내역이 없습니다",
            "info": "<b>총 _TOTAL_건</b>",
            /* "aria" : {
                "sortAscending" : ": activate to sort column ascending",
                "sortDescending" : ": activate to sort column descending"
            }, 
            "paginate" : {
                "first" : "맨 처음",
                "last" : "맨 마지막",
                "next" : "다음",
                "previous" : "이전"
            },
        }
	});
	
	*/
	
	table = $("#dataTable").dataTable({
		ordering : false,
		paging : false,
		searching : false,
		bInfo : true,
		scrollY : true,
		scrollX : true,
		destroy : false
	});
}

/********************************
 * 블라섬 푸시 시스템별 전체 건수
 ********************************/
function fn_areaChart(arg1, arg2) {
	var v_data, v_jsonData;
	
	var array = new Array(); // data array
	var array2 = new Array(); // systemCode array
	var array3 = new Array(); // systemName array
	
	$.each(arg2, function(key, data) {
		array2.push(data.aliasCode);
		array3.push(data.systemName);
	});
	
	$.each(arg1, function(index, item) {
		var obj = new Object();
		
		obj.label = item.LABEL; // 라벨
		
		$.each(arg2, function(key, data) {
			obj[data.aliasCode] = item[data.aliasCode + "_TOT_CNT"];
		});
							
		array.push(obj);
		//array.push(obj);
	});
	
	//v_jsonData = JSON.parse(JSON.stringify(array));
	//v_jsonData = [{"label":"2012-02-24","0004sps":7,"0004ssgstudy":3},{"label":"2012-02-27","0004sps":3,"0004ssgstudy":0},{"label":"2012-02-25","0004ssgstudy":0}];
	//array2 = ["0004sps","0004ssgstudy"];
	//array3 = ["통합구매지원시스템(ssgstudy)","통합구매지원시스템(sps)"];
	
	//alert(JSON.stringify(array));
	//alert(JSON.stringify(array2));
	//alert(JSON.stringify(array3));
	
	$("#morris-area-chart").empty();
	
	Morris.Area({
        element: 'morris-area-chart',
        data: v_jsonData,
        xkey: 'label',
        ykeys: array2,
        labels: array3,
        xLabels:"day",
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });
    
    $("div svg text").attr("style", "font-family:돋움;font-weight:bold");
}

/********************************
 * 블라섬 푸시 시스템별 전체 건수
 ********************************/
function fn_barChart(arg1, arg2) {
	var v_data, v_jsonData;
	
	var array = new Array(); // data array
	var array2 = new Array(); // systemCode array
	var array3 = new Array(); // systemName array
	
	$.each(arg2, function(key, data) {
		array2.push(data.aliasCode);
		array3.push(data.systemName);
	});
	
	$.each(arg1, function(index, item) {
		var obj = new Object();
		
		obj.label = item.LABEL; // 라벨
		
		$.each(arg2, function(key, data) {
			obj[data.aliasCode] = item[data.aliasCode + "_Y_RATE"];
		});
							
		array.push(obj);
	});
	
	v_jsonData = JSON.parse(JSON.stringify(array));
	
	$("#morris-bar-chart").empty();
	
	Morris.Bar({
        element: 'morris-bar-chart',
        data: v_jsonData,
        xkey: 'label',
        ykeys: array2,
        labels: array3,
        hideHover: 'auto',
        resize: true
    });
    
    $("div svg text").attr("style", "font-family:돋움;font-weight:bold");
}

/********************************
 * [공통] 년월일 세팅
 ********************************/
function fn_setDateCombo() {	
	var nowDate = new Date();
	var nowYear = nowDate.getFullYear();
	var nowMonth = eval(nowDate.getMonth()) +1;
	var nowDay = eval(nowDate.getDate());

	/***************
	 * 년 세팅
	 ***************/
	var startYear = nowYear - 10;
	
	for(var i=1; i<=10; i++) {		
	    $("#dateYear").append(new Option(startYear + i, startYear + i));
	}
	
	$("#dateYear").val(nowYear);

	/***************
	 * 월 세팅
	 **************/
	for (var i=0; i<12; i++) {
		$("#dateMonth").append(new Option(i + 1, i + 1));
	}
	
	$("#dateMonth").val(nowMonth);

	/***************************************
	 * 먼저 현재 년과 월을 셋팅
	 * 윤년계산과 월의 마지막 일자를 구하기 위해
	 ***************************************/
	setDay();
	
	
	/********************************************
	 * 일(day)의 select를 생성하고 현재 일자로 초기화
	 ********************************************/
	$("#dateDay").val(nowDay);	
}

/********************************
 * [공통] 일(day) 세팅
 ********************************/
function setDay() {	
	var year = $("#dateYear").val();
	var month = $("#dateMonth").val();
	var day = $("#dateDay").val();    
	var dateDay = $("#dateDay");
	
	var arrayMonth = [31,28,31,30,31,30,31,31,30,31,30,31];
	
	/*******************************************
	 * 윤달 체크 부분
	 * 윤달이면 2월 마지막 일자를 29일로 변경
	 *******************************************/
	if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
	    arrayMonth[1] = 29;
	}
	
	/**************************************
	 * 기존 일(day) select를 모두 삭제한다.
	 **************************************/
	$("#dateDay").find('option').remove().end();
	    
	/*********************************
	 * 일(day) select 옵션 생성
	 *********************************/
	for (var i=1; i<=arrayMonth[month-1]; i++) {
		$("#dateDay").append(new Option(i, i));
	}
	
	/*********************************************
	 * 기존에 선택된 일값 유지
	 * 기존 일값보다 현재 최대일값이 작을 경우
	 * 현재 선택 최대일값으로 세팅
	 ********************************************/
	if( day != null || day != "" ) {
	    if( day > arrayMonth[month-1] ) {
	        $("#dateDay").val(arrayMonth[month-1]);
	    } else {
	        $("#dateDay").val(day);
	    }
	}
	
}


