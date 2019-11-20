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

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

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
var arrDivMove = new Array();

/***********************
 * [공통] 초기화 
 ***********************/
function fn_start() {
	$(".div_move").each(function(idx, item) {
		arrDivMove.push($("#" + item.id).position().top);
	});
	
	var offsetCanvas = $("#div_canvas").offset();
	var offset = $("#div_1").offset();
	var position = arrDivMove[0] - 39;
	
    $('#div_canvas').animate({scrollTop : position}, 400);
    
    // Jasypt Select Box
    var arrJasyptAlg = ["PBEWITHMD5ANDDES", "PBEWITHSHA1ANDDESEDE", "PBEWITHSHA1ANDRC2_128", "PBEWITHSHA1ANDRC2_40", "PBEWITHSHA1ANDRC4_128", "PBEWITHSHA1ANDRC4_40"];
    
    $("#sel_jasyptAlg").empty();
    
    for (var idx=0; idx < arrJasyptAlg.length; idx++) {
    	var item = arrJasyptAlg[idx];
    	var option = $("<option value='" + item + "'>" + item + "</option>");
    	
    	$("#sel_jasyptAlg").append(option);
    }
    
    // Message Digest Select Box
    var arrMsgDigestAlg = ["SHA-256", "MD2", "MD5", "SHA-1", "SHA-384", "SHA-512"];
    
    $("#sel_msgDigestAlg").empty();
    
    for (var idx=0; idx < arrMsgDigestAlg.length; idx++) {
    	var item = arrMsgDigestAlg[idx];
    	var option = $("<option value='" + item + "'>" + item + "</option>");
    	
    	$("#sel_msgDigestAlg").append(option);
    }
    	
	
	//fn_setDateCombo();	
	//obj_searchBtnRef = $("#btn_search"); // 저장버튼
	//obj_saveBtnRef = $("#btn_save"); // 저장버튼
	
	fn_event(); // 이벤트 바인딩
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_event() {
	// 저장버튼
	$(".btn_save").click(function() {
		fn_save();
	});
	
	// 처리구분 
	$("input[name='chk_gubn']").click(function() {
		var chkGubn = $("input[name='chk_gubn']:checked").val();
		var offsetCanvas = $("#div_canvas").offset();
		var offset = $("#div_" + chkGubn).offset();
		
		var position = arrDivMove[(Number(chkGubn)-1)] - 39;
		
		$('#div_canvas').animate({scrollTop : position}, 400);
	});
}

/***********************
 * [공통] 실행하기
 ***********************/
function fn_save() {
	var chkGubn = $("input[name='chk_gubn']:checked").val();
	var chkGubn2 = $("input[name='chk_gubn2_" + chkGubn + "']:checked").val();
	var beforeData = $("#txt_before_" + chkGubn).val();
	var afterData = "";
	var charsetName = "";
	var password = "";
	var algorithm = "";
	var salt = "";
	
	// URL
	if (chkGubn == "2") {
		charsetName = $("input[name='chk_charsetName']:checked").val();
	} else if (chkGubn == "3") {
		password = $("#txt_key").val();
		algorithm = $("#sel_jasyptAlg").val();
	} else if (chkGubn == "4") {
		salt = $("#txt_salt").val();
		algorithm = $("#sel_msgDigestAlg").val();
	}
	
	var postData = {
		  chkGubn : chkGubn
		, chkGubn2 : chkGubn2
		, beforeData : beforeData
		, charsetName : charsetName
		, password : password
		, algorithm : algorithm
		, salt : salt
	};
	
	var formURL = contextPath + "getEncDecData.do";
	
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
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				afterData = result.afterData;
				
				$("#txt_after_" + chkGubn).val(afterData);				
			} else {
				alert("에러가 발생하였습니다.");
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
 * [기능] 라디오1 라벨클릭
 ***********************/
function fn_chkGubnLabelClicked(arg1, arg2) {
	debugger;
	$("input:radio[name='" + arg1 + "']:radio[value='" + arg2 + "']").prop("checked", true); 
	
	if (arg1 == "chk_gubn") {
		var chkGubn = $("input[name='chk_gubn']:checked").val();
		var offsetCanvas = $("#div_canvas").offset();
		var offset = $("#div_" + chkGubn).offset();
		
		var position = arrDivMove[(Number(chkGubn)-1)] - 39;
		
	    $('#div_canvas').animate({scrollTop : position}, 400);
	}
}




