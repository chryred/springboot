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
	/*
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});
	*/
	
	//제외버튼 클릭
	obj_saveBtnRef.on("click", function() {
		fn_save();
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
 * [공통] 저장 
 ***********************/
function fn_save() {	
	var postData = $("#form1").serializeArray();
	var formURL = $("#form1").attr("action");

	if (!confirm("저장하시겠습니까?")) {
		return;
	}
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
		},
		complete : function() {
		},
		success : function(result) {			
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				alert("정상처리 되었습니다.");
				fn_UpdateKorean();
			} else {
				alert("에러가 발생하였습니다.")
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}

/***********************
 * [배치] 한글 갱신 
 ***********************/
function fn_UpdateKorean() {	
	var postData = $("#form1").serializeArray();
	var formURL = $("#form1").attr("action");
	formURL = "/batch/updateCronJobKoreanJson.do";

	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60  * 1000, //2 min,
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
				window.location = contextPath + '/batch/listBatch.do';
			} else {
				alert("에러가 발생하였습니다.");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}