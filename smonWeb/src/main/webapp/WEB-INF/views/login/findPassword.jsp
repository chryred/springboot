<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
	<title>비밀번호 재설정</title>
</head>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/app/common.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" type="text/css">


<script src="<%=request.getContextPath()%>/resources/bootstrap/bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-ui.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>

<style>
	#certiNumBox {
		display: none;
	}
</style>

<script>
var g_baseTime = new Date(1990, 01, 01, 00, 00, 00); 
var g_changeTime = new Date(1990, 01, 01, 00, 03, 00);
var g_interval;

/* 문서 시작 */
$(document).ready(function() { 
	fn_start(); // 시작
}); 

/* 시작함수 */
function fn_start() {
	var v_userName = "${userName}"; // 사용자 이름
	var v_userId   = "${userId}";   // 사용자 ID
	
	/* 사용자 정보 세팅 */
	$('#userName').val(v_userName);
	$('#userId').val(v_userId);
	
	if(v_userName == null || v_userName == "" || v_userId == "" || v_userId == null){
		alert("사용자 정보가 없습니다.\n로그인 창으로 이동합니다.");
		window.location = "<%=request.getContextPath()%>/index.do";
	}
	
	fn_getVersionIe(); // IE 확인
	
	$("#div_btnCertNum").removeClass();        // 인증번호 받기 버튼 
	$("#div_btnUpdate").removeClass();         // 로그인 버튼
	$("#div_inputCertNum").removeClass();      // 인증번호 입력
	$("#div_labelTime").removeClass();         // 시간 라벨
	$("#div_btnUpdate").addClass("b_none");    // 로그인 버튼 감추기
	$("#div_inputCertNum").addClass("b_none"); // 인증번호 입력 감추기
	$("#div_labelTime").addClass("b_none");    // 시간 라벨 감추기
	$("#div_labelTime").addClass("text-right");// 시간 라벨 감추기
}

function fn_getVersionIe() { 
     var word; 
     var version = "N/A"; 
     var agent = navigator.userAgent.toLowerCase(); 
     var name = navigator.appName; 

     // IE old version ( IE 10 or Lower ) 
     if ( name == "Microsoft Internet Explorer" ) {
    	 word = "msie "; 
     } else { 
         if ( agent.search("trident") > -1 ) word = "trident/.*rv:"; // IE 11 
         else if ( agent.search("edge/") > -1 ) word = "edge/"; // Microsoft Edge 
     } 

     var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" ); 

     if (reg.exec( agent ) != null) version = RegExp.$1 + RegExp.$2; 

     if (version < 9) {
		alert("IE8 이하 버전은 정상작동하지 않습니다.\n\n크롬이나 IE9 이상 버전을 사용하세요.")
     }
     
     return version; 
} 

/************************
* 인증번호 받기
* - 비밀번호 변경을 위한 OTP 인증번호 받기
*************************/
function fn_getCertNum() {
	
	var v_userId  = $('#userId').val();
	var v_userPwd = $('#userName').val();
	
	$.ajax({
		  type: "POST"
		, url: "<%=request.getContextPath()%>/sendPwdOtpNumber.do"
		, data: {mgrId: v_userId, mgrName : v_userPwd}
		, async : false
		, success: function(data) {
			var v_chkResult = data.chkResultCode; // 결과값
			var v_errorMsg = data.errorMsg; // 오류내용
			if (v_chkResult == "OK") {
				g_changeTime = new Date(1990, 01, 01, 00, 03, 00); // 초기화
				g_interval = setInterval(function() {fn_countDown();}, 1000);
				
				$("#div_btnCertNum").removeClass();          // 인증번호 받기 버튼 
				$("#div_btnUpdate").removeClass();           // 로그인 버튼
				$("#div_inputCertNum").removeClass("b_none");// 인증번호 입력
				$("#div_labelTime").removeClass("b_none");   // 시간 라벨
				$("#div_btnCertNum").addClass("b_none");     // 인증번호 받기 버튼 감추기
				
			} else {
				alert(data.errorMsg);	
			}
		} 
	});	
}

/************************
* 인증번호 카운트 다운
*************************/
function fn_countDown() { 
	g_changeTime.setSeconds(g_changeTime.getSeconds() - 1);
	
	// 원하는 날짜, 시간 정확하게 초단위까지 기입.
	var v_diffDay = Math.floor((g_changeTime - g_baseTime) / 1000 / 60 / 60 / 24);
	var v_diffHour = Math.floor((g_changeTime - g_baseTime) / 1000 / 60 / 60 - (24 * v_diffDay));
	var v_diffMin = Math.floor((g_changeTime - g_baseTime) / 1000 /60 - (24 * 60 * v_diffDay) - (60 * v_diffHour));
	var v_diffSeconds = Math.round((g_changeTime - g_baseTime) / 1000 - (24 * 60 * 60 * v_diffDay) - (60 * 60 * v_diffHour) - (60 * v_diffMin));
	
	if (parseInt(v_diffSeconds) <= 9) {
		v_diffSeconds = "0" + v_diffSeconds;
	}

	$("#span_time").html("[" + v_diffMin + ":" + v_diffSeconds + "]");	
	
	if (parseInt(v_diffMin) == 0 && parseInt(v_diffSeconds) == 0) {
		alert("인증번호 입력시간이 초과하였습니다.");
		clearInterval(g_interval);
		window.location = "<%=request.getContextPath()%>/index.do";
	}
} 

/************************
* 인증번호 검증
*************************/
function fn_confirmCertNum(){
	var v_userId     = $("#userId").val();    // 사번
	var v_newMgrPwd  = $("#newMgrPwd").val(); // 신규비밀번호
	var v_newMgrPwd2 = $("#newMgrPwd2").val();// 신규비밀번호확인
	var v_certNum    = $("#certNum").val();   // 인증번호
	
	/** 입력값 체크 */
	if(v_newMgrPwd == '' || v_newMgrPwd == null){
		alert("신규 비밀번호를 입력해주세요.");
		$("#newMgrPwd").focus();
		return;
	}else if(v_newMgrPwd2 == '' || v_newMgrPwd2 == null){
		alert("신규 비밀번호 확인을 입력해주세요.");
		$("#newMgrPwd2").focus();
		return;
	}
	
	if(v_newMgrPwd != v_newMgrPwd2){
		alert("새 비밀번호가 일치하지 않습니다.\n확인 후 다시 입력하십시오.");
		$("#newMgrPwd").focus();
		return;
	}
	
	if(v_certNum == '' || v_certNum == null || v_certNum.length < 6){
		alert("인증번호를 정확히 입력해 주세요");
		return;
	}
	
	/** 비밀번호 변경 전 체크로직 */
	$.ajax({
		  type: "POST"
		, url: "<%=request.getContextPath()%>/checkOtpAndUpdatePwd.do"
		, data: {mgrId : v_userId, mgrPwd : v_newMgrPwd, mgrPwd2 : v_newMgrPwd2, certNum : v_certNum}
		, async : false
		, success: function(result) {
			var v_chkResultCode = result.chkResultCode; // 결과값
			var v_errorMsg      = result.errorMsg;      // 오류내용
			
			if (v_chkResultCode == "OK") {
				alert("비밀번호가 정상적으로 변경되었습니다.\n로그인 창으로 이동합니다.");
				window.location = "<%=request.getContextPath()%>/index.do";
			} else {
				// 오류 메시지 출력
				alert(v_errorMsg);
				return;
			}
		},error : function(e) {
            alert("비밀번호 체크 중 오류가 발생했습니다.");
        }
	});
}
</script>

<body onload="" style="background-color:#eaeaea;">
	<div class="container" >
		<div class="row" style="padding-top:300px;">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-info">
					<div class="panel-heading text-center" >
						<h3 class="panel-title" style="font-weight:bold;">
							<span>비밀번호 재설정</span>
						</h3>
					</div>
					<div class="panel-body">
						<div class="signup-or-separator">
							<h5 class="text shift text-special">비밀번호 변경</h5>
							<hr></hr>
						</div>
						<form id="pwdInfo" name="pwdInfo">
							<fieldset>
								<div style="margin-bottom:10px;">
									<input class="decorative-input" type="password" id="newMgrPwd" name="newMgrPwd" placeholder="신규 비밀번호">
								</div>
								<div style="margin-bottom:10px;">
									<input class="decorative-input" type="password" id="newMgrPwd2" name="newMgrPwd2" placeholder="신규 비밀번호 확인">
								</div>
								<div class="form-group" id="certiNumBox">
									인증번호<input class="form-control" id="certiNum" name="userPwd" type="password">
								</div>
								<div id="div_inputCertNum" style="margin-bottom:10px;">
									<input class="decorative-input" type="certNum" id="certNum" name="certNum" placeholder="인증번호 6자리" maxlength="6">
								</div>
								<div id="div_labelTime" class="text-right" style="margin-bottom:10px;">
									<span id="span_time" style="color:red;"></span>
								</div>
								<div id="div_btnCertNum" class="b_block">
									<a class="btn b_login b_block" id="btn_getCertNum" onclick="fn_getCertNum()"><span>인증번호 받기</span></a>
								</div>
								<div id="div_btnUpdate" class="b_none">
									<a class="btn b_login b_none" id="btn_getCertNum" onclick="fn_confirmCertNum()"><span>비밀번호 변경</span></a>
								</div>
								<hr></hr>
								<div class="text-left">
									<b>※ 비밀번호 변경 규칙</b><br>
									- 8자리 이상의 3가지 문자조합(특수문자/영문자/숫자)<br>
									- 연속된 숫자, 문자 사용 불가<br>
								</div>
								<input type="hidden" id="userId" name="userId" />
								<input type="hidden" id="userName" name="userName" />
							</fieldset>
						</form>
						<div class="pull-right">
							<br>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
