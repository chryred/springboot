<%@ page import="com.shinsegae.smon.model.DefectVO" %>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<script src='https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/tripledes.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js'></script>
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<script type="text/javascript"  language=”javascript”>
	var contextPath = '<%=request.getContextPath()%>';
	
	function func() {
		
		document.all.holdtext2.value = $("#holdtext").val();
	}
	
	function base64url(source) {
		encodedSource = CryptoJS.enc.Base64.stringify(source);
		encodedSource = encodedSource.replace(/=+$/,'');
		encodedSource = encodedSource.replace(/\+/g,'-');
		encodedSource = encodedSource.replace(/\//g,'_');
		
		return encodedSource;
	}
	
	function SendGroupTalk() {
		
		console.log("--in testSend-- ")
		
		// 토큰키 헤더 작성
		var header = {
				"alg" : "HS256",
				"typ" : "JWT"
		};
		
		var stringifiedHeader	= CryptoJS.enc.Utf8.parse(JSON.stringify(header));
		var encodedHeader 		= base64url(stringifiedHeader);
		
		// 토큰키 데이터 작성
		var data = {
				"sender"	: 	"${uId}"	,		// 보내는 사원번호.
				"iat"		:	1523497723	,              
				"exp" 		:	1530424752
		};
		
		var stringifiedData 	= CryptoJS.enc.Utf8.parse(JSON.stringify(data));
		var encodedData 		= base64url(stringifiedData);
		
		// 토큰키 헤더, 데이터 로그
		console.log("Header--  " 	+ encodedHeader);
		console.log("Data-- " 		+ encodedData);
		
		// 토큰키 헤더.데이터 결합
		var token = encodedHeader + "." + encodedData;
		
		// 시크릿 키
		var secret		= "S-dV7@1SS#AGd#%^";
		
		//HmacSHA256 암호화
		var signature 	= CryptoJS.HmacSHA256(token, secret);		
		var signature 	= base64url(signature);
		console.log("signature-- " + signature);
		
		
		
		// HEADER.PAYLOAD 생성 및 SIGNATURE 생성 완료.
		// SIGNATURE를 secret base64 encoded 하면 끝. !!
		//----------------------------------------------------//
		

		// 최종 토큰 생성
		var signedToken = token + "." + signature;
		console.log("signedToken-- " + signedToken);
	
		// 채팅방 제목 
		var chatname 	= "${uName}"+ "("+"${uId}"+")" +" - " + "${result}"+"급 장애 발생 알림";
		var textString 	= $("#holdtext2").val();
		var receivers 	= "${uId}" + "," + "${receivers}"; 
		console.log(chatname);
		console.log(textString);
		console.log(receivers);
		var postData = {
					"x-access-token" 	: 	signedToken			,
					"req"				: 	"deptSmon"			,
					"receivers"			:	receivers		 	,
					"chatname"			:	chatname			,
					"text"				:	textString		// String 선 선언 후 작성.
		};
	
		$.ajax({
			url 			: 	"https://mblossom.shinsegae.com:9001/v1/chat/inbound/chat",
			type 			: 	"POST"		,
			data 			: 	postData	,
			dataType 		: 	"json"		, 
			
			success : function(result) {		
			
				console.log("1");
				console.log(result);
				
			},
			error 	: function(result) {
				
				console.log("2");	
				console.log(result);
			}
		});
	}
</script>

<!-- lib -->
<!-- css -->

<div id="page-wrapper">
	<!-- 1행  -->
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header"><small>장애 등급 확인 리스트</small></h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	
	<!-- 2행 -->
	<div class="row">
		<div class="col-lg-12">
		    <div class="panel panel-default">
		        <div class="panel-heading">
					<i class="fa fa-bullhorn fa-fw"></i>
					<span class="fa arrow"></span>&nbsp; 등급에 해당하는 내용을 확인한 뒤 신속하게 내용을 공유해 주세요.
		        </div>
		        
		        <!-- /.panel-heading -->
				<div>
					<form id="defectForm" action="/defect/sendDefect.do" method="post" onsubmit="SendGroupTalk()">
						<h3 class="text-center">장애 등급 측정 결과 </h3><br>
					
						<input type="hidden" name="defectGrade" 	value="${result}">
						<input type="hidden" name="defectMessage" 	value="${resultStr}">
						<input type="hidden" name="effect" 			value="${defect.effect}">
						<input type="hidden" name="effectRange" 	value="${defect.effectRange}">
						<input type="hidden" name="targetSystem" 	value="${defect.targetSystem}">
						<input type="hidden" name="effectTime" 		value="${defect.effectTime}">
						<input type="hidden" name="myLocalDate1" 	value="${defect.myLocalDate1}">
						<input type="hidden" name="myLocalDate2" 	value="${defect.myLocalDate2}">
						<input type="hidden" name="selBox" 			value="${defect.selBox}">
					
						<c:if test="${result eq 'A'}">
							<table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline">
								<tr>
									<td class="text-center" style="font-size:15px;">장애등급</td>
									<td class="text-center" style="font-size:15px;">등급별 정의</td>
									<td class="text-center" style="font-size:15px;">등급별 상황전파대상</td>
									<td class="text-center" style="font-size:15px;">상황전파수단</td>
								</tr>
								<tr>
									<td width="10%" align="center" style="font-size:60px; color:white; vertical-align:middle" bgcolor="red">A</td>
									<td width="50%" style="font-size:20px; vertical-align:middle"><dir>○ 장애로 인하여 고객 또는 비즈니스에 치명적인 영향/손실을 초래한 경우<br>○ 현업/점포 업무중단, 사용불가<br>○ 즉각적인 해결이 필요<br>○ 재무/인명/데이터 손실, 언론보도, 제재(制裁), 보안이슈사항 등 회사 이미지/신뢰도 손상 발생 시</dir></td>
									<td width="15%" align="center" style="font-size:20px;">팀장<br>품질관리팀 전원<br>ITO 1,2 담당<br>IT사업부장<br>CSR팀장<br>인사팀장<br>지원담당<br>대표이사</td>
									<td width="30%" style="font-size:20px; vertical-align:middle"><dir>[1차] 블라섬톡<br>[2차] SRMS 등록 (24H이내)<br>　　   ITSM 등록 (3DAY이내)<br><br>※ 1차 : 장애발생인지 후<br>　2차 : 장애조치완료 후</dir></td>						
								</tr>
							</table>
						</c:if>
						
						<c:if test="${result eq 'B'}">
							<table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline">
								<tr>
									<td class="text-center" style="font-size:15px;">장애등급</td>
									<td class="text-center" style="font-size:15px;">등급별 정의</td>
									<td class="text-center" style="font-size:15px;">등급별 상황전파대상</td>
									<td class="text-center" style="font-size:15px;">상황전파수단</td>
								</tr>
								<tr>
									<td width="10%" align="center" style="font-size:60px; color:white; vertical-align:middle" bgcolor="black">B</td>
									<td width="50%" style="font-size:20px; vertical-align:middle"><dir>○ 장애로 인하여 고객 또는 비즈니스에 영향/손실을 초래한 경우<br>○ 현업/점포 지연, 처리오류, 제한적사용<br>○ 가능한 신속한 해결이 필요</dir></td>
									<td width="15%" align="center" style="font-size:20px;">팀장<br>품질관리팀 전원<br>ITO 1,2 담당<br>IT사업부장<br>CSR팀장<br>인사팀장<br>지원담당<br>대표이사</td>					
									<td width="30%" style="font-size:20px; vertical-align:middle"><dir>[1차] 블라섬톡<br>[2차] SRMS 등록 (24H이내)<br>　　   ITSM 등록 (3DAY이내)<br><br>※ 1차 : 장애발생인지 후<br>　2차 : 장애조치완료 후</dir></td>
									
								</tr>
							</table> 
						</c:if>
						
						<c:if test="${result eq 'C'}">
							<table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline">
								<tr>
									<td class="text-center" style="font-size:15px;">장애등급</td>
									<td class="text-center" style="font-size:15px;">등급별 정의</td>
									<td class="text-center" style="font-size:15px;">등급별 상황전파대상</td>
									<td class="text-center" style="font-size:15px;">상황전파수단</td>
								</tr>
								<tr>
									<td width="10%" align="center" style="font-size:60px; vertical-align:middle" bgcolor="gray">C</td>
									<td width="50%" style="font-size:20px; vertical-align:middle"><dir>○ 장애로 인하여 고객 또는 비즈니스에 경미한 영향/손실을 초래한 경우<br>○ 현업/점포 경미한영향, 불편<br>○ 어느정도 대응시간을 가지고 해결</dir></td>
									<td width="15%" align="center" style="font-size:20px; vertical-align:middle">팀장<br>품질관리팀 전원<br>ITO 1,2 담당<br>IT사업부장<br>CSR팀장</td>		
									<td width="30%" style="font-size:20px; vertical-align:middle"><dir>[1차] 블라섬톡<br>[2차] SRMS 등록 (24H이내)<br>　　  ITSM 등록 (3DAY이내)<br><br>※ 1차 : 장애발생인지 후<br>　2차 : 장애조치완료 후</dir></td>
									
								</tr>
							</table>
						</c:if>
						
						<c:if test="${result eq 'D'}">
							<table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline">
								<tr>
									<td class="text-center" style="font-size:15px;">장애등급</td>
									<td class="text-center" style="font-size:15px;">등급별 정의</td>
									<td class="text-center" style="font-size:15px;">등급별 상황전파대상</td>
									<td class="text-center" style="font-size:15px;">상황전파수단</td>
								</tr>
								<tr>
									<td width="10%" align="center" style="font-size:60px; vertical-align:middle">D</td>
									<td width="50%" style="font-size:20px; vertical-align:middle"><dir>○ 고객 또는 당사의 비즈니스에 영향은 발생하지 않지만 장애가 발생할 뻔했거나<br>　이어질 가능성이 높은 인시던트<br>○ 현업/점포 영향없음<br>○ 관리적 해결이 거의 필요없음</dir></td>
									<td width="15%" align="center" style="font-size:20px; vertical-align:middle">팀장<br>품질관리팀 전원<br>ITO 1,2 담당<br>IT사업부장<br>CSR팀장</td>			
									<td width="30%" style="font-size:20px; vertical-align:middle"><dir>[1차] 블라섬톡<br>[2차] ITSM 등록<br><br>※ 1차 : 장애발생인지 후<br>　2차 : 장애조치완료 후</dir></td>
									
								</tr>
							</table>
						</c:if>
					    
						<br><br>
						<div align="center">
					   		<textarea  rows="10" cols="25" name="holdtext" id="holdtext" align="center" style="font-size:25px; vertical-align:middle">${resultStr}</textarea><br><br>
							
						</div>
						<div align="center" style="font-size:17px; color: blue;">※ 위의 내용을 수정하여 상세한 장애 내용을 전송하세요. </div>
					
		
						<!-- Modal -->
						<br><br><br>
							<button type="button" class="btn btn-info btn-lg btn-block" data-toggle="modal" data-target="#myModal" onclick="func()">블톡보내기</button>
							
						<div class="modal fade" id="myModal" role="dialog">
							<div class="modal-dialog">
						    
						    	<!-- Modal content-->
						      	<div class="modal-content">
						        	<div class="modal-header">
						          		<button type="button" class="close" data-dismiss="modal">&times;</button>
						          		<h4 class="modal-title">블톡 전송 확인</h4>
						        	</div>					 
						        	<div class="modal-body">
						          		<p style="font-size: 18px; color: #4641D9" align="center" >아래와 같은 장애 발생 메시지를 <br> 상황전파대상에게 보내시겠습니까?</p>
						          		<div align="center">
						          			<textarea  rows="10" cols="25" name="holdtext2" id="holdtext2" align="center" style="font-size:25px; vertical-align:middle"></textarea><br><br>
						        		</div>
						        	</div>
						        	<div class="modal-footer">
						        		<button type="submit" class="btn btn-default">확인</button>	
						          		<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						        	</div>
						      	</div>  
						    </div>
						</div>	
						<button type="button" class="btn btn-info btn-lg btn-block" onclick="location.href='findDefect.do'"><b>돌아가기</b></button>	
					</form>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>  
</div>    

<!-- /#page-wrapper -->

</body>
</html>