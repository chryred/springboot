<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html >
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
	<c:set var="mgrId" value="${userVO.mgrId}" />
	<c:set var="mgrName"  value="${userVO.mgrName}" />	
	<c:set var="authGroupid"  value="${userVO.mgrGrade}" />
	<%

	String appPath = request.getContextPath();

	String userId = (String)pageContext.getAttribute("mgrId");
	String userName = (String)pageContext.getAttribute("mgrName");
	
	String frontUrl = "http://10.253.21.183:8080/sysvoc";
	
	String authGroupid = (String)pageContext.getAttribute("authGroupid");
	%>
	<title>백화점팀 운영관리 시스템</title>
	<style type="text/css">
	#vocIconLink{
		padding : 0 0 0 0;
	}
	#vocIcon{
		width: 35px;
		vertical-align: -webkit-baseline-middle;
	}
	<!-- 비밀번호 변경 팝업 css -->
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
	</style>
	
	<link href="<%=request.getContextPath() %>/resources/css/reset.css" rel="stylesheet" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/bootstrap/dist/css/bootstrap.css" rel="stylesheet" />
	<link href="<%=request.getContextPath() %>/resources/css/jquery-ui.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="<%=request.getContextPath() %>/resources/css/theme.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/dist/css/sb-admin-2.css" rel="stylesheet" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/resources/css/app/navigation.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/morrisjs/morris.css" rel="stylesheet" />
    <link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/resources/css/chosen.css" rel="stylesheet" />
    <link href="<%=request.getContextPath() %>/resources/css/app/common.css" rel="stylesheet" /><!-- 비밀번호 변경 common.css -->


	<script src="<%=request.getContextPath() %>/resources/js/underscore-min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/resources/js/jquery-ui.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.preapply.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/sb-admin-2.js"></script>
	<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/raphael/raphael-min.js"></script>
	<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/morrisjs/morris.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/chosen-jquery.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/rgba-data.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/window.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
	
	<script type="text/javascript">

		var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
		var g_contextWebRoot = "${pageContext.request.contextPath}/";
		window.contextPath = '<%=request.getContextPath()%>';
		var g_userPwdDiffDay = '';
		
		/**===============================================================================
		// 문서시작(DOM)
		//===============================================================================*/
		$(document).ready(function() {
			
		    // showMask를 클릭시 작동하며 검은 마스크 배경과 레이어 팝업을 띄웁니다.
		    $('.showMask').click(function(e){
		        // preventDefault는 href의 링크 기본 행동을 막는 기능입니다.
		        e.preventDefault();
		        fnWrapWindowByMask();
		    });

		    // 닫기(close)를 눌렀을 때 작동합니다.
		    /** 분기 비밀번호 변경 시에는 취소 시 로그인페이지로 이동 */
		    $('.b_close').click(function (e) {
		    	
		    	if(g_userPwdDiffDay > 90){
	            	alert('비밀번호 변경을 진행하지 않으면 로그아웃 됩니다.\n취소하시겠습니까?');
	            }
		    	
		        e.preventDefault();
		        
		     	// 폼 초기화
	        	$("#orgMgrPwd").val("");
	            $("#newMgrPwd").val("");
	            $("#newMgrPwd2").val("");
            
	            $('.mask, .window').hide();
	            
	            if(g_userPwdDiffDay > 90){
	            	//alert('로그아웃 페이지로 이동');
	            	location.href =  "<%=request.getContextPath() %>/logout.do";
	            }
		        
		    });
		    
		    // 비밀번호 변경주기 체크
		    var pwdChgDate = new Date("${userVO.mgrPwdChgDate}");
		    var pwdChgStdDate = new Date();

		    g_userPwdDiffDay = Math.floor(pwdChgStdDate.valueOf()/(24*60*60*1000)- pwdChgDate.valueOf()/(24*60*60*1000));
		    
		    if(g_userPwdDiffDay > 90){
		    	alert('비밀번호 변경일이 90일이 지났거나 임시 비밀번호로 로그인 되었습니다.\n비밀번호 변경을 진행해주세요.');
		    	$('#btn_pwd_close').show();
		    	$('#btn_close').hide();
		    	
		        fnWrapWindowByMask();
		    }else{
		    	$('#btn_pwd_close').hide();
		    	$('#btn_close').show();
		    }

		}); 
		
		/**===============================================================================
		// 화면 마스킹
		//===============================================================================*/
		function fnWrapWindowByMask(){
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
		
		/**===============================================================================
		// 비밀번호 변경 전 확인
		// 비밀번호 작성규칙 / 비밀번호 3회 이력 등 
		//===============================================================================*/
		function fnConfirmPwd(){
			var v_result = false;
			
			var v_mgrId      = "${userVO.mgrId}";      // 사용자 ID(추후변경-김성일 담당님 작업 이후)
			//var v_mgrId      = '<%=userId%>';          // 사용자 ID
			var v_orgMgrPwd  = $("#orgMgrPwd").val();  // 기존비밀번호
			var v_newMgrPwd  = $("#newMgrPwd").val();  // 신규비밀번호
			var v_newMgrPwd2 = $("#newMgrPwd2").val(); // 신규비밀번호확인
			
			/** 입력값 체크 */
			if (v_orgMgrPwd == '' || v_orgMgrPwd == null){
				alert("기존 비밀번호를 입력해주세요.");
				$("#orgMgrPwd").focus();
				return;
			}else if(v_newMgrPwd == '' || v_newMgrPwd == null){
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
			
			if(v_orgMgrPwd == v_newMgrPwd){
				alert("기존비밀번호와 신규비밀번호가 동일합니다.\n다른 비밀번호를 사용해주십시오.");
				return;
			}
			
			/** 비밀번호 변경 전 체크로직 */
			$.ajax({
				  type: "POST"
				, url: g_contextWebRoot+"confirmEmpPwd.do"
				, data: {mgrId : v_mgrId, mgrOldPwd : v_orgMgrPwd, mgrPwd : v_newMgrPwd, mgrPwd2 : v_newMgrPwd2}
				, async : false
				, success: function(result) {
					var v_chkResultCode = result.chkResultCode; // 결과값
					var v_errorMsg      = result.errorMsg;      // 오류내용
					
					if (v_chkResultCode == "OK") {
						fnChangePwd();						
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
		
		/**===============================================================================
		// 비밀번호 변경
		//===============================================================================*/
		function fnChangePwd() {	
			
			//var v_mgrId      = '<%=userId%>';          // 사용자 ID
			var v_mgrId      = "${userVO.mgrId}";      // 사용자 ID(추후변경-김성일 담당님 작업 이후)
			var v_orgMgrPwd  = $("#orgMgrPwd").val();  // 기존비밀번호
			var v_newMgrPwd  = $("#newMgrPwd").val();  // 신규비밀번호
			var v_newMgrPwd2 = $("#newMgrPwd2").val(); // 신규비밀번호확인
			
			/** 비밀번호 변경처리 */
			$.ajax({
				  type: "POST"
				, url: g_contextWebRoot+"updateEmpPwd.do"
				, data: {mgrId : v_mgrId, mgrOldPwd : v_orgMgrPwd, mgrPwd : v_newMgrPwd, mgrPwd2 : v_newMgrPwd2}
				, async : false
				, success: function(result) {
					var v_chkResultCode = result.chkResultCode; // 결과값
					var v_errorMsg      = result.errorMsg;  // 오류내용
					
					if (v_chkResultCode == "OK") {
						fnSuccessUpdatePwd(v_errorMsg);				
					} else {
						// 오류 메시지 출력
						alert(v_errorMsg);
						return;
					}
				},error : function(e) {
                    alert("비밀번호 변경 중 오류가 발생했습니다.");
                }
			});
		}
		
		/**===============================================================================
		// 비밀번호 변경 완료
		//===============================================================================*/
		function fnSuccessUpdatePwd(v_errorMsg){
			
			if(g_userPwdDiffDay > 90){
            	alert('비밀번호 변경이 완료되었습니다.\n로그인 페이지로 이동합니다.');
            	g_userPwdDiffDay = '';
            	// 로그아웃으로 이동
            	location.href = g_contextWebRoot;
            }else {
            	alert(v_errorMsg);
            }
		
			
			// 폼 초기화
        	$("#orgMgrPwd").val("");
            $("#newMgrPwd").val("");
            $("#newMgrPwd2").val("");
            
            $('.mask, .window').hide();
            
            
		}
		
	</script>
	
</head>

<body>
    <div id="wrapper" >
      <c:import url="/adm/navigation.do">
      	<c:param name="authGroupid" value="<%=authGroupid %>"></c:param>
      </c:import>
      <div class="overlay"><div id="loading"></div></div>

		<div class="modal fade" id="interlockingModal" role="dialog">
			<div class="modal-dialog modal-lg" style="z-index: 1100;">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h3 class="modal-title2">
							<b></b>
						</h3>
						<h4 class="modal-title4"></h4>
					</div>
					<div class="modal-body">
						<div class="panel-body">
							<div class="display compact">
								<table class="table table-striped table-bordered" cellspacing="0" width="100%" id="interlockingDataTables">
									<thead>
										<tr>
										<tr>
											<th>파트</th>
											<th>시스템명</th>
											<th>DB 서비스명</th>
											<th>연동현황</th>
										</tr>
									</thead>
									<tbody id="grid">

									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		
	<!-- 비밀번호 변경 팝업 -->	
	<div class="setDiv">
 
	    <div class="mask"></div>
	    <div class="window">
			<div class="panel-default">
				<div class="panel-heading text-center" >
					<h3 class="panel-title" style="font-weight:bold;">
						<span>사용자 비밀번호 변경</span>
					</h3>
				</div>
				<div class="panel-body">
					<div class="signup-or-separator">
						<h5 class="text shift text-special">비밀번호 변경</h5>
						<hr></hr>
					</div>
					<form id="form_chgPwd" name="form_chgPwd">
						<fieldset>
							<div style="margin-bottom:10px;">
								<input class="decorative-input" type="password" id="orgMgrPwd" name="orgMgrPwd" placeholder="기존 비밀번호">
							</div>
							<div style="margin-bottom:10px;">
								<input class="decorative-input" type="password" id="newMgrPwd" name="newMgrPwd" placeholder="신규 비밀번호">
							</div>
							<div style="margin-bottom:10px;">
								<input class="decorative-input" type="password" id="newMgrPwd2" name="newMgrPwd2" placeholder="신규 비밀번호 확인">
							</div>
							<div id="" class="b_block" style="margin-bottom:10px;">
								<a class="btn b_login b_block" id="btn_join" onclick="fnConfirmPwd();"><span>변경</span></a>
							</div>
							<div id="" class="b_block" style="background-color:#EAEAEA;">
								<a class="btn b_close b_none" id="btn_close"><span>취소</span></a>
							</div>
							<div id="" class="b_block" style="background-color:#EAEAEA;">
								<a class="btn b_close b_none" id="btn_pwd_close"><span>변경취소</span></a>
							</div>
							<hr></hr>
							<div class="text-left">
								<b>※ 비밀번호 사용규칙</b><br>
								- 8자리 이상의 3가지 문자조합(특수문자/영문자/숫자)<br>
								- 연속된 숫자, 문자 사용 불가<br>
								<b>※ 비밀번호 변경조건</b><br>
								- 변경주기(분기 1회)<br>
								- 임시 비밀번호 발급 시
							</div>
						</fieldset>
					</form>
				</div>
			</div> 
	    </div>
	</div>