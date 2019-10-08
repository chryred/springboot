<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
	<title>운영관리시스템</title>
</head>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/app/common.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" type="text/css">


<script src="<%=request.getContextPath()%>/resources/bootstrap/bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-ui.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>

<%
	String frontUrl = "http://10.253.21.183:8080/sysvoc";
%>

<style>
	#certiNumBox {
		display: none;
	}
	
	#btnLogin {
		display: none;
	}
	
	#vocIcon {
		height: 50px !important;
	}
	
    .setDiv {
        text-align: center;
    }
    .mask {
        position:absolute;
        left:0;
        top:0;
        z-index:9999;
        background-color:#000;
        display:none;
    }
    .window {
        display: none;
        background-color: #ffffff;
        z-index:99999;
    }
    
    /* 비밀번호 찾기 css - 최병문 */
    .findPwdMask {
        position:absolute;
        left:0;
        top:0;
        z-index:9999;
        background-color:#000;
        display:none;
    }
    .findPwdWindow {
        display: none;
        background-color: #ffffff;
        z-index:99999;
    }
    /* 체크박스 추가 -최병문 */
    .check{
    	margin-top: 5px;
    	font-size: 13px;
    }
    .check input{
    	vertical-align: -7px;
    }
    .check label{
    	border-right:1px solid #DCDCDC;
    	padding-right: 5px;
    }   
</style>

<script>
var g_baseTime = new Date(1990, 01, 01, 00, 00, 00); 
var g_changeTime = new Date(1990, 01, 01, 00, 03, 00);
var g_interval;
var g_userId;    // 쿠키 사용자 변수 - 최병문



/* 문서 시작 */
$(document).ready(function() { 
	fn_start(); // 시작
	
    // showMask를 클릭시 작동하며 검은 마스크 배경과 레이어 팝업을 띄웁니다.
    $('.showMask').click(function(e){
        // preventDefault는 href의 링크 기본 행동을 막는 기능입니다.
        e.preventDefault();
        wrapWindowByMask();
    });

    // 닫기(close)를 눌렀을 때 작동합니다. -- 최병문 수정
    $('#btn_close').click(function (e) {
        e.preventDefault();
        
        if(confirm("창을 닫을 시 신청서가 초기화 됩니다.\n닫으시겠습니까?")) {
        	// 폼 초기화
            $("#mgrId").val("");
            $("#mgrPwd").val("");
            $("#mgrPwd2").val("");
            $("#mgrTask").val("");
            
            $('.mask, .window').hide();
        }
    });

    // 뒤 검은 마스크를 클릭시에도 모두 제거하도록 처리합니다.
    $('.mask').click(function () {
        $(this).hide();
        $('.window').hide();
    });
    
    
    //로그인 ID 쿠키체크 - 최병문
    g_userId = $.cookie('cookUserId');
    
    if(g_userId == undefined || g_userId == ""){ // 없는 경우
    	
    }else{
    	$('#saveMgrId').prop("checked", true);
    	$('#userId').val(g_userId);
    }
    
    // 쿠키변경, 미체크시 해제 - 최병문
    $("[name=saveMgrId]").each(function() {
    	$(this).change(function(){    		
    		if(!$('#saveMgrId').prop("checked")){
    			$.cookie('cookUserId', "");	
    		}
    	});
    });
    
    // 비밀번호 찾기 클릭 이벤트
    $('#findPassword').click(function(e){
    	 // preventDefault는 href의 링크 기본 행동을 막는 기능입니다.
        e.preventDefault();
        wrapWindowByPwdMask();
    });
    
    // 비밀번호 찾기 마스크 클릭 시 제거 - 최병문
    $('.findPwdMask').click(function () {
        $(this).hide();
        $('.findPwdWindow').hide();
    });
    
 	// 닫기(close)를 눌렀을 때 작동합니다.
    $('#btnFindPwdClose').click(function (e) {
        e.preventDefault();
        
        if(confirm("창을 닫을 시 임시비밀번호 발급이 되지 않습니다.\n닫으시겠습니까?")) {
        	// 폼 초기화
            $("#findPwdId").val("");
            $("#findUserName").val("");
            
            $('.findPwdMask, .findPwdWindow').hide();
        }
    });
 	

    
    $("#userPwd").keypress(function (e) {
		if (e.which == 13){		// enter key
			fn_getCertNum();
		}
	});
    
	$("#certNum").keypress(function (e) {
		if (e.which == 13){
			fn_confirmCertNum();
		}
	});
	
}); 

function wrapWindowByMask(){
    // 화면의 높이와 너비를 변수로 만듭니다.
    var maskHeight = $(document).height();
    var maskWidth = $(window).width();

    // 마스크의 높이와 너비를 화면의 높이와 너비 변수로 설정합니다.
    $('.mask').css({'width':maskWidth,'height':maskHeight});

    // fade 애니메이션 : 1초 동안 검게 됐다가 80%의 불투명으로 변합니다.
    $('.mask').fadeIn(1000);
    $('.mask').fadeTo("slow",0.8);

    // 레이어 팝업을 가운데로 띄우기 위해 화면의 높이와 너비의 가운데 값과 스크롤 값을 더하여 변수로 만듭니다.
    var left = ( $(window).scrollLeft() + ( $(window).width() - $('.window').width()) / 2 );
    var top = ( $(window).scrollTop() + ( $(window).height() - $('.window').height()) / 2 );

    // css 스타일을 변경합니다.
    $('.window').css({'left':left,'top':top, 'position':'absolute'});

    // 레이어 팝업을 띄웁니다.
    $('.window').show();
}

/* 시작함수 */
function fn_start() {
	var v_type = "${type}"; // 유형
	
	if (v_type == "error") {
		alert("사용자 인증과정에서 오류가 발생하였습니다.\n다시 로그인 해주세요.");
	}
	
	fn_getVersionIe(); // IE 확인
	
	$("#div_btnCertNum").removeClass(); // 인증번호 받기 버튼 
	$("#div_btnLogin").removeClass(); // 로그인 버튼
	$("#div_inputCertNum").removeClass(); // 인증번호 입력
	$("#div_labelTime").removeClass(); // 시간 라벨
	$("#div_btnLogin").addClass("b_none"); // 로그인 버튼 감추기
	$("#div_inputCertNum").addClass("b_none"); // 인증번호 입력 감추기
	$("#div_labelTime").addClass("b_none"); // 시간 라벨 감추기
	$("#div_labelTime").addClass("text-right"); // 시간 라벨 감추기
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
* - 시큐리티 로그인 이후 인증번호 받기 가능
*************************/
function fn_getCertNum() {
	// 인증번호 받을 때 쿠키 저장 - 최병문
	if($('#saveMgrId').prop("checked")){
		$.cookie('cookUserId', $('#userId').val() == undefined?"":$("#userId").val());	
	}else{
		$.cookie('cookUserId', "");	
	}	
	
	var v_userId = $('#userId').val();
	var v_userPwd = $('#userPwd').val();
	
	if(v_userId == '' || v_userId == null){
		alert("사번을 입력해 주세요");
		return;
	}
	
	if(v_userPwd == '' || v_userPwd == null){
		alert("비밀번호를 입력해 주세요");
		return;
	}
	
	$.ajax({
		  type: "POST"
		, url: "<%=request.getContextPath()%>/securityCheck.do"
		, data: {userId: v_userId, userPwd : v_userPwd}
		, async : false
		, success: function(data) {
			if (data == "USER_LOGIN_OK") {
				g_changeTime = new Date(1990, 01, 01, 00, 03, 00); // 초기화
				g_interval = setInterval(function() {fn_countDown();}, 1000);
				
				$("#div_btnCertNum").removeClass(); // 인증번호 받기 버튼 
				$("#div_btnLogin").removeClass(); // 로그인 버튼
				$("#div_inputCertNum").removeClass("b_none"); // 인증번호 입력
				$("#div_labelTime").removeClass("b_none"); // 시간 라벨
				$("#div_btnCertNum").addClass("b_none"); // 인증번호 받기 버튼 감추기
				
				//var url = "<%=request.getContextPath()%>/login3.do?userId=admin&userName=admin";
				//window.location = url;
			} else {
				alert("로그인에 실패하였습니다.\n다시 로그인 해주세요.");	
				window.location = "<%=request.getContextPath()%>/index.do";
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
	var v_userId = $("#userId").val(); // 사번
	var v_userPwd = $("#userPwd").val(); // 비밀번호
	var v_certNum = $("#certNum").val(); // 인증번호
	
	if(v_userId == '' || v_userId == null){
		alert("사번을 입력해 주세요");
		return;
	}
	
	if(v_userPwd == '' || v_userPwd == null){
		alert("비밀번호를 입력해 주세요");
		return;
	}
	
	if(v_certNum == '' || v_certNum == null || v_certNum.length < 6){
		alert("인증번호를 정확히 입력해 주세요");
		return;
	}
	
	$.ajax({
		  type: "POST"
		, url: "<%=request.getContextPath()%>/confirmCertNum.do"
		, data: {mgrId : v_userId, mgrPwd : v_userPwd, certNum : v_certNum}
		, success: function(result) {
			var v_chkResult = result.chkResult; // 결과값
			var v_errorMsg = result.errorMsg; // 오류내용
			
			if (v_chkResult == "OK") {
				var url = "<%=request.getContextPath()%>/main.do";
				window.location = url;
			} else {
				alert("로그인에 실패하였습니다.\n다시 로그인 해주세요.");	
				window.location = "<%=request.getContextPath()%>/index.do";
			}
		}
	});	
}

/************************
* 회원가입
*************************/
function fn_confirmEmpNum() {	
	var v_result = false;
	
	var v_mgrId = $("#mgrId").val(); // 사번
	
	if (v_mgrId == '' || v_mgrId == null){
		alert("사번을 입력해 주세요");
		return;
	}
	
	$.ajax({
		  type: "POST"
		, url: "<%=request.getContextPath()%>/confirmEmpNum.do"
		, data: {mgrId : v_mgrId, mgrPwd : v_mgrId, certNum : v_mgrId}
		, async : false
		, success: function(result) {
			var v_chkResult = result.chkResult; // 결과값
			var v_errorMsg = result.errorMsg; // 오류내용
			
			if (v_chkResult == "OK") {
				fn_join();
			} else {
				alert("이미 가입한 회원이거나 관리자 승인 대기중입니다.");
				return;
			}
		}
	});	
	
	return v_result;
}

/************************
* 회원가입
*************************/
function fn_join(){
	var v_mgrId = $("#mgrId").val(); // 사번
	var v_mgrPwd = $("#mgrPwd").val(); // 비밀번호
	var v_mgrPwd2 = $("#mgrPwd2").val(); // 비밀번호확인
	var v_mgrTask = $("#mgrTask").val(); // 업무내용
	
	if (v_mgrPwd == '' || v_mgrPwd == null){
		alert("비밀번호를 입력해 주세요");
		return;
	}
	
	if (v_mgrPwd2 == '' || v_mgrPwd2 == null){
		alert("비밀번호 확인을 입력해 주세요");
		return;
	}
	
	if (v_mgrPwd != v_mgrPwd2){
		alert("비밀번호가 서로 일치하지 않습니다.");
		return;
	}
	
	if (v_mgrTask == '' || v_mgrTask == null){
		alert("업무내용을 입력해 주세요");
		return;
	}
	
	if (fn_strlen(v_mgrTask) > 50) {
		alert("업무내용을 50자 이내로 입력해 주세요");
		return;
	}
	
	$.ajax({
		  type: "POST"
		, url: "<%=request.getContextPath()%>/saveMember.do"
		, data: {mgrId : v_mgrId, mgrPwd : v_mgrPwd, mgrPwd2 : v_mgrPwd2, mgrTask : v_mgrTask}
		, success: function(result) {
			var v_chkResult = result.chkResult; // 결과값
			var v_errorMsg = result.errorMsg; // 오류내용
			
			if (v_chkResult == "OK") {
				alert("회원가입 신청이 완료되었습니다.\n관리자 승인 이후 사용가능합니다.");
				//var url = "<%=request.getContextPath()%>/main.do";
				//window.location = url;
			} else {
				alert(v_errorMsg);
				//alert("로그인에 실패하였습니다.\n다시 로그인 해주세요.");	
				//window.location = "<%=request.getContextPath()%>/index.do";
			}
		}
	});	
}

/*************************
* [유틸] 문자열 길이(한글포함) 
*************************/
function fn_strlen(str) { 
	  var len = 0;
	  for(var i=0;i<str.length;i++) {
		  len += (str.charCodeAt(i) > 128) ? 2 : 1;
	  }
	  return len;
}


/**======================================================
   wrapWindowByMask - 비밀번호 찾기 
 ======================================================**/
function wrapWindowByPwdMask(){
    // 화면의 높이와 너비를 변수로 만듭니다.
    var maskHeight = $(document).height();
    var maskWidth = $(window).width();

    // 마스크의 높이와 너비를 화면의 높이와 너비 변수로 설정합니다.
    $('.findPwdMask').css({'width':maskWidth,'height':maskHeight});

    // fade 애니메이션 : 1초 동안 검게 됐다가 80%의 불투명으로 변합니다.
    $('.findPwdMask').fadeIn(1000);
    $('.findPwdMask').fadeTo("slow",0.8);

    // 레이어 팝업을 가운데로 띄우기 위해 화면의 높이와 너비의 가운데 값과 스크롤 값을 더하여 변수로 만듭니다.
    var left = ( $(window).scrollLeft() + ( $(window).width() - $('.findPwdWindow').width()) / 2 );
    var top = ( $(window).scrollTop() + ( $(window).height() - $('.findPwdWindow').height()) / 2 );

    // css 스타일을 변경합니다.
    $('.findPwdWindow').css({'left':left,'top':top, 'position':'absolute'});

    // 레이어 팝업을 띄웁니다.
    $('.findPwdWindow').show();
}

/*************************
 * 비밀번호 찾기(최병문)
 ************************/
function fnFindPassword(){
	
	// 파라미터 값 체크
	var v_findPwdId    = $("#findPwdId").val();    // 기존비밀번호
	var v_findUserName = $("#findUserName").val(); // 신규비밀번호
	
	if(v_findPwdId == "" || v_findPwdId == null){
		alert("사번을 입력해주세요.");
		$('#findPwdId').focus();
		return;
	}else if(v_findUserName == "" || v_findUserName == null){
		alert("이름을 입력해주세요.");
		$('#findUserName').focus();
		return;
	}
	
	/* 사용자 확인 후 비밀번호 변경 창 이동 */
	$.ajax({
		type: "POST"
	   ,url: "<%=request.getContextPath()%>/checkValidUserInfo.do"
	   ,data: {mgrId : v_findPwdId, mgrName : v_findUserName}
	   ,success: function(result){
		   
		   var v_chkResult = result.chkResultCode; // 결과값
		   var v_errorMsg  = result.errorMsg; // 오류내용
		   
		   if (v_chkResult == "OK") {
				
				// 비밀번호 변경창으로 이동
				$('#form_findPwd').attr({
					action : "<%=request.getContextPath()%>/findPassword.do",
					method : 'post'
				}).submit();
	            
	            $('.findPwdMask, .findPwdWindow').hide();
			} else {
				alert(v_errorMsg);
			}
	   }
	   ,error : function(e) {
           alert("임시 비밀번호 발급 중 오류가 발생했습니다.");
       }
	});
	
} 


</script>

<body onload="" style="background-color:#eaeaea;">
	<div class="container" >
		<div class="row" style="padding-top:300px;">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading text-center" >
						<h3 class="panel-title" style="font-weight:bold;">
							<span>백화점팀 운영관리 시스템</span>
							<%-- <sec:authorize access="hasRole('ROLE_ADMIN')">
								ROLE_ADMIN
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_USER')">
								ROLE_USER
							</sec:authorize> --%>
						</h3>
					</div>
					<div class="panel-body">
						<div class="signup-or-separator">
							<h5 class="text shift text-special">로그인</h5>
							<hr></hr>
						</div>
						<form id="loginInfoSabun" name="loginInfo">
							<fieldset>
								<div style="margin-bottom:10px;">
									<input class="decorative-input" type="text" id="userId" name="userId" placeholder="사번">
								</div>
								<div style="margin-bottom:10px;">
									<input class="decorative-input" type="password" id="userPwd" name="userPwd" placeholder="비밀번호">
								</div>
								<div class="form-group" id="certiNumBox">
									인증번호<input class="form-control" id="certiNum" name="userPwd" type="password">
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>  
								<!-- Change this to a button or input when using this as a form -->
								<!-- 
                                <div>
                                	<a onclick="btnLoginType('idpwd')">계정으로 로그인</a>
                                </div>
                                 -->
								<!-- <div>
									<a id="btnGetCerti"
										class="btn btn-lg btn-warning btn-block disable"
										onclick="getCertNum()">인증번호 받기</a> <a id="btnLogin"
										class="btn btn-lg btn-info btn-block" onclick="login3_click()">로그인</a>
								</div> -->
								<div id="div_inputCertNum" style="margin-bottom:10px;">
									<input class="decorative-input" type="text" id="certNum" name="certNum" placeholder="인증번호 6자리" maxlength="6">
								</div>
								<div id="div_labelTime" class="text-right" style="margin-bottom:10px;">
									<span id="span_time" style="color:red;"></span>
								</div>
								<div id="div_btnCertNum" class="b_block">
									<a class="btn b_login b_block" id="btn_getCertNum" onclick="fn_getCertNum()"><span>인증번호 받기</span></a>
								</div>
								<div id="div_btnLogin" class="b_none">
									<a class="btn b_login b_none" id="btn_getCertNum" onclick="fn_confirmCertNum()"><span>로그인</span></a>
								</div>
								<!-- 아이디 저장 및 비밀번호 찾기 - 최병문 -->
								<div class="check">
									<input id="saveMgrId" name="saveMgrId" type="checkbox">
											<label for="saveMgrId">아이디 저장하기</label>
									<a href="#" id="findPassword" title="비밀번호 찾기">비밀번호 찾기</a>
								</div>
								<hr></hr>
								<div class="text-left">
									운영관리 시스템 계정이 없으세요?
									<a class="btn b_join showMask" id="btn_join">회원가입</a>
								</div>
							</fieldset>
						</form>
						<div class="pull-right">
							<br>
							<%-- <a href="<%=frontUrl%>/Login_exe3.do?system=214">
								<input id="vocIcon" class="text-right" type="image" src="<%=frontUrl%>/showImage.do?sys=214">
							</a> --%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="setDiv">
    <!-- <a href="#" class="showMask">검은 마스크와 레이어 팝업 띄우기</a> -->
 
    <div class="mask"></div>
    <div class="window">
		<div class="login-panel panel panel-default">
			<div class="panel-heading text-center" >
				<h3 class="panel-title" style="font-weight:bold;">
					<span>회원가입</span>
				</h3>
			</div>
			<div class="panel-body">
				<div class="signup-or-separator">
					<h5 class="text shift text-special">회원가입 신청서</h5>
					<hr></hr>
				</div>
				<form id="form_join" name="form_join">
					<fieldset>
						<div style="margin-bottom:10px;">
							<input class="decorative-input" type="text" id="mgrId" name="mgrId" placeholder="사번">
						</div>
						<div style="margin-bottom:10px;">
							<input class="decorative-input" type="password" id="mgrPwd" name="mgrPwd" placeholder="비밀번호">
						</div>
						<div style="margin-bottom:10px;">
							<input class="decorative-input" type="password" id="mgrPwd2" name="mgrPwd2" placeholder="비밀번호 확인">
						</div>
						<div id="div_inputCertNum" style="margin-bottom:10px;">
							<textarea id="mgrTask" name="mgrTask" class="decorative-input" placeholder="업무 내용(50자 이내)"></textarea>
						</div>
						<div id="" class="b_block" style="margin-bottom:10px;">
							<a class="btn b_login b_block" id="btn_join" onclick="fn_confirmEmpNum();"><span>회원가입</span></a>
						</div>
						<div id="" class="b_block" style="background-color:#EAEAEA;">
							<a class="btn b_close b_none" id="btn_close"><span>닫기</span></a>
						</div>
						<hr></hr>
						<div class="text-left">
							회원가입 이후 시스템 관리자의 승인 후 사용가능합니다.
						</div>
					</fieldset>
				</form>
				<div class="pull-right">
					<br>
					<%-- <a href="<%=frontUrl%>/Login_exe3.do?system=214">
						<input id="vocIcon" class="text-right" type="image" src="<%=frontUrl%>/showImage.do?sys=214">
					</a> --%>
				</div>
			</div>
		</div> 
    </div>
</div>
<!-- 최병문 -->
<div class="setFindPwdDiv">
	    <div class="findPwdMask"></div>
    <div class="findPwdWindow">
		<div class="panel-default">
			<div class="panel-heading text-center" >
				<h3 class="panel-title" style="font-weight:bold;">
					<span>비밀번호 찾기</span>
				</h3>
			</div>
			<div class="panel-body">
				<div class="signup-or-separator">
					<h5 class="text shift text-special">비밀번호 찾기</h5>
					<hr></hr>
				</div>
				<form id="form_findPwd" name="form_findPwd">
					<fieldset>
						<div style="margin-bottom:10px;">
							<input class="decorative-input" type="text" id="findPwdId" name="findPwdId" placeholder="사번">
						</div>
						<div style="margin-bottom:10px;">
							<input class="decorative-input" type="text" id="findUserName" name="findUserName" placeholder="이름">
						</div>
						<div id="" class="b_block" style="margin-bottom:10px;">
							<a class="btn b_login b_block" id="btnfindPwd" onclick="fnFindPassword();"><span>확인</span></a>
						</div>
						<div id="" class="b_block" style="background-color:#EAEAEA;">
							<a class="btn b_close b_none" id="btnFindPwdClose"><span>닫기</span></a>
						</div>
						<hr></hr>
						<div class="text-left">
						    <b>※  비밀번호 찾기</b><br>
							- 사용자의 비밀번호를 찾습니다.<br>
							- 사용자 정보가 없는 경우, 변경화면으로 이동되지 않습니다.
						</div>
					</fieldset>
				</form>
			</div>
		</div> 
    </div>
</div>
</body>
</html>
