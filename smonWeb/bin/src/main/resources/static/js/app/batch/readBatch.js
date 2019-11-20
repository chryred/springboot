$(document).ready(function() {
	fn_start();
});

$.ajaxSetup({  
    global: false
}); 

/*
 * private decalre
 */
var obj_saveBtnRef = null;
var obj_executeBtnRef = null;
var obj_deleteBtnRef = null;

/***********************
 * [공통] 초기화 
 ***********************/
function fn_start() {
	obj_saveBtnRef = $("#btn_save"); // 저장버튼
	obj_executeBtnRef = $("#btn_execute");
	obj_deleteBtnRef = $("#btn_delete");
	fn_staticBindEvent(); // 이벤트 바인딩
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_staticBindEvent() {	
	//저장
	obj_saveBtnRef.on("click", function() {
		fn_save();
	});
	
	//실행
	obj_executeBtnRef.on("click", function() {
		fn_execute();
	});
	
	obj_deleteBtnRef.on("click", function() {
		fn_delete();
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
			var v_chkResult = result.chkResult; //v_chkResult 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리되었습니다.");
				fn_UpdateKorean();
				
				alert("정상처리 되었습니다.");
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
 * [공통] 실행 
 ***********************/
function fn_execute() {
	var postData = $("#form1").serializeArray();
	var formURL = "/batch/executeSimpleJobJson.do";
	
	if (!confirm("저장하시겠습니까?")) {
		return;
	}
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		success : function(result) {				
			var v_chkResult = result.chkResult; //v_chkResult 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				alert("정상처리되었습니다.");
			} else {
				alert("에러가 발생하였습니다.");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}

/***********************
 * [공통] 삭제 
 ***********************/
function fn_delete() {
	var postData = $("#form1").serializeArray();
	var formURL = "/batch/removeJobJson.do";
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		success : function(result) {				
			var v_chkResult = result.chkResult; //v_chkResult 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				alert("정상처리되었습니다.");
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