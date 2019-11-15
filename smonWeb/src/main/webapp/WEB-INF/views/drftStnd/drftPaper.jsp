<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<script type="text/javascript"> 
	var contextPath = '<%=request.getContextPath()%>';
	var list = [];
	var checkIfNew;	// 1이면 new, 2이면 update
	// 화면 호출 시 시작하는 함수
	$(document).ready(function(){
		fn_print();
		listinit();
		$("#btn_save").attr("disabled",true);	// 저장키 비활성화
		$("#btn_cancel").attr("disabled",true);	// 취소키 비활성화
	});
	
	// 화면에 list[0]의 데이터 출력
	function listinit(){
		$("#settleName1").val(list[0].STLNM1);
		$("#settleName2").val(list[0].STLNM2);
		$("#settleName3").val(list[0].STLNM3);
		$("#settleName4").val(list[0].STLNM4);
		$("#settleEmpType1").val(list[0].STLEMPTYP1);
		$("#settleEmpType2").val(list[0].STLEMPTYP2);
		$("#settleEmpType3").val(list[0].STLEMPTYP3);
		$("#settleEmpType4").val(list[0].STLEMPTYP4);
		$("#settleOrder1").val(list[0].STLORDR1);
		$("#settleOrder2").val(list[0].STLORDR2);
		$("#settleOrder3").val(list[0].STLORDR3);
		$("#settleOrder4").val(list[0].STLORDR4);
		$("#consentName1").val(list[0].CNSTNM1);
		$("#consentName2").val(list[0].CNSTNM2);
		$("#consentName3").val(list[0].CNSTNM3);
		$("#consentName4").val(list[0].CNSTNM4);
		$("#consentName5").val(list[0].CNSTNM5);
		$("#consentOrder1").val(list[0].CNSTORDR1);
		$("#consentOrder2").val(list[0].CNSTORDR2);
		$("#consentOrder3").val(list[0].CNSTORDR3);
		$("#consentOrder4").val(list[0].CNSTORDR4);
		$("#consentOrder5").val(list[0].CNSTORDR5);
		$("#consentEmpType1").val(list[0].CNSTEMPTYP1);
		$("#consentEmpType2").val(list[0].CNSTEMPTYP2);
		$("#consentEmpType3").val(list[0].CNSTEMPTYP3);
		$("#consentEmpType4").val(list[0].CNSTEMPTYP4);
		$("#consentEmpType5").val(list[0].CNSTEMPTYP5);
		$("#subject").val(list[0].SBJ);
		$("#content").val(list[0].CONTENT);		
		$("#SEQ").val(list[0].SEQ);	
		$("#MODID").val(list[0].MODID);
		$("#MODDT").val(list[0].MODDT.substr(2,2) + "년 " + list[0].MODDT.substr(4,2) + "월 " + list[0].MODDT.substr(6,2) + "일");	
		
	}
	
	// 기안서 리스트 선택 시 화면 출력
	function clickSelect(){
		console.log( "in clickSelect");
		var aval = document.getElementById("drftList");
		var num	= Number(aval.selectedIndex);
		
		$("#settleName1").val(list[num].STLNM1);
		$("#settleName2").val(list[num].STLNM2);
		$("#settleName3").val(list[num].STLNM3);
		$("#settleName4").val(list[num].STLNM4);
		$("#settleEmpType1").val(list[num].STLEMPTYP1);
		$("#settleEmpType2").val(list[num].STLEMPTYP2);
		$("#settleEmpType3").val(list[num].STLEMPTYP3);
		$("#settleEmpType4").val(list[num].STLEMPTYP4);
		$("#settleOrder1").val(list[num].STLORDR1);
		$("#settleOrder2").val(list[num].STLORDR2);
		$("#settleOrder3").val(list[num].STLORDR3);
		$("#settleOrder4").val(list[num].STLORDR4);
		$("#consentName1").val(list[num].CNSTNM1);
		$("#consentName2").val(list[num].CNSTNM2);
		$("#consentName3").val(list[num].CNSTNM3);
		$("#consentName4").val(list[num].CNSTNM4);
		$("#consentName5").val(list[num].CNSTNM5);
		$("#consentEmpType1").val(list[num].CNSTEMPTYP1);
		$("#consentEmpType2").val(list[num].CNSTEMPTYP2);
		$("#consentEmpType3").val(list[num].CNSTEMPTYP3);
		$("#consentEmpType4").val(list[num].CNSTEMPTYP4);
		$("#consentEmpType5").val(list[num].CNSTEMPTYP5);
		$("#consentOrder1").val(list[num].CNSTORDR1);
		$("#consentOrder2").val(list[num].CNSTORDR2);
		$("#consentOrder3").val(list[num].CNSTORDR3);
		$("#consentOrder4").val(list[num].CNSTORDR4);
		$("#consentOrder5").val(list[num].CNSTORDR5);
		$("#subject").val(list[num].SBJ);
		$("#content").val(list[num].CONTENT);	
		$("#SEQ").val(list[num].SEQ);
		$("#MODID").val(list[num].MODID);
		$("#MODDT").val(list[num].MODDT.substr(2,2) + "년 " + list[num].MODDT.substr(4,2) + "월 " + list[num].MODDT.substr(6,2) + "일");	
	}
	
	// 화면 조회
	function fn_print(){
		console.log("fn_print");
		$.ajax({
			url 			: 	contextPath + "drftPaperSearch.do",
			type			:	"POST"	,
			dataType 		: 	"json"	,
			async			:	false	,
			success 		: 	function(result) {		
									console.log(result.data);
									for(var i=0; i<result.data.length; i ++){
										list[i] = result.data[i];

									/* 	$("#drftList").append("<option value = "+ list[i].SEQ + ">"
															+ "<c:out value=" + list[i].SBJ} + "/>
														    + "</option>"
														     ); */
									}
								},
			error			:	function(result) {
									console.log(result);
								}
		});
	}
	
	
	// 신규 버튼 클릭 시 read only 해제
	function btn_new(){
		checkIfNew = 1;
		alert("기안지의 내용을 작성해주세요(제목: 기안서 명칭)");
		$("table tr input").each(function() {
			$("#content").val('');
			$("#content").attr("readonly",false);
			$(this).val(''); //모든 텍스트 클리어
			$(this).attr("readonly",false); // 글씨 작성 가능하게
		});
		$("#btn_save").attr("disabled",false);		// 저장키 활성화
		$("#btn_update").attr("disabled",true);		// 수정키 비활성화
		$("#btn_new").attr("disabled",true);		// 신규키 비활성화
		$("#btn_delete").attr("disabled",true);		// 삭제키 비활성화
		$("#btn_cancel").attr("disabled",false);	// 취소키 활성화
	}
	
	// 삭제 버튼 클릭 시
	function btn_delete(){
		if(confirm("정말 삭제하시겠습니까?") == true){
			
		}else{
			return;
		}
		
		var postData = {
			"SEQ"	:	$("#SEQ").val()
		}
		
		$.ajax({
			url 			: 	contextPath + "drftDelete.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(data) {		
									location.href="drftPaper.do";
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		});
	}
	
	function btn_cancel(){
		if(confirm("정말 취소하시겠습니까?") == true){
			location.href="drftPaper.do";
		}else{
			return;
		}
	}
	function btn_update(){
		checkIfNew = 2;
		alert("기안지의 내용을 수정해주세요");
		$("#content").attr("readonly",false);
		$("table tr input").each(function() {
			$(this).attr("readonly",false); // 글씨 작성 가능하게
		});
		$("#btn_save").attr("disabled",false);		// 저장키 활성화
		$("#btn_update").attr("disabled",true);		// 수정키 비활성화
		$("#btn_new").attr("disabled",true);		// 신규키 비활성화	
		$("#btn_delete").attr("disabled",true);		// 삭제키 비활성화
		$("#btn_cancel").attr("disabled",false);	// 취소키 활성화
	}
	

	function btn_save(){	
		var postData = {
				"SBJ" 			:	$("#subject").val(),
				"CONTENT"		:	$("#content").val(),
				"STLNM1"		:	$("#settleName1").val(),
				"STLNM2"		:	$("#settleName2").val(),
				"STLNM3"		:	$("#settleName3").val(),
				"STLNM4"		:	$("#settleName4").val(),
				"STLEMPTYP1"	:	$("#settleEmpType1").val(),
				"STLEMPTYP2"	:	$("#settleEmpType2").val(),
				"STLEMPTYP3"	:	$("#settleEmpType3").val(),
				"STLEMPTYP4"	:	$("#settleEmpType4").val(),
				"STLORDR1"		:	$("#settleOrder1").val(),
				"STLORDR2"		:	$("#settleOrder2").val(),
				"STLORDR3"		:	$("#settleOrder3").val(),
				"STLORDR4"		:	$("#settleOrder4").val(),
				"CNSTNM1"		:	$("#consentName1").val(),
				"CNSTNM2"		:	$("#consentName2").val(),
				"CNSTNM3"		:	$("#consentName3").val(),
				"CNSTNM4"		:	$("#consentName4").val(),
				"CNSTNM5"		:	$("#consentName5").val(),
				"CNSTORDR1"		:	$("#consentOrder1").val(),
				"CNSTORDR2"		:	$("#consentOrder2").val(),
				"CNSTORDR3"		:	$("#consentOrder3").val(),
				"CNSTORDR4"		:	$("#consentOrder4").val(),
				"CNSTORDR5"		:	$("#consentOrder5").val(),
				"CNSTEMPTYP1"	:	$("#consentEmpType1").val(),
				"CNSTEMPTYP2"	:	$("#consentEmpType2").val(),
				"CNSTEMPTYP3"	:	$("#consentEmpType3").val(),
				"CNSTEMPTYP4"	:	$("#consentEmpType4").val(),
				"CNSTEMPTYP5"	:	$("#consentEmpType5").val(),
				"SEQ"			:	$("#SEQ").val()
		};
		
		if(checkIfNew == 1)	// 신규이면
		{
			$.ajax({
				url 			: 	contextPath + "drftSave.do",
				type			:	"POST"		,
				data			:	postData	,
				dataType 		: 	"json"		,
				success 		: 	function(data) {		
										location.href="drftPaper.do";
									},
				error			:	function(result) {
										console.log("2");
										console.log(result);
									}
			});
		}
		if(checkIfNew == 2)	// 수정이면
		{
			$.ajax({
				url 			: 	contextPath + "drftUpdate.do",
				type			:	"POST"		,
				data			:	postData	,
				dataType 		: 	"json"		,
				success 		: 	function(data) {		
										location.href="drftPaper.do";
									},
				error			:	function(result) {
										console.log("2");
										console.log(result);
									}
			});
		}
		
	}
</script>



<!-- lib -->
<%-- <script src="<%=request.getContextPath() %>/js/jquery-ui.js" type="text/javascript"></script> --%>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/jquery-ui.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/theme.css" /> --%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/resources/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/resources/css/app/defect/defectList.css" rel="stylesheet">
<style type="text/css">
label {
	height: 34px !important;
	line-height: 34px !important;
}
</style>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>기안서 결재 기준 리스트</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading btn-box">
                	<div>
                    	<i class="fa fa-bullhorn fa-fw"></i>&nbsp;기안서 결제 기준 및 내용을 관리합니다.
                    </div>
                    <div class="text-right">
                   		<input type="hidden" id="SEQ" name="SEQ">
	                    <a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_new" onclick="btn_new()">&nbsp;신규&nbsp;</button></a>
	                    <a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_update" onclick="btn_update()">&nbsp;수정&nbsp;</button></a>
	                    <a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_delete" onclick="btn_delete()">&nbsp;삭제&nbsp;</button></a>
	                    <a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_save" onclick="btn_save()">&nbsp;저장&nbsp;</button></a>
                		<a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_cancel" onclick="btn_cancel()">&nbsp;취소&nbsp;</button></a>
                	</div>
                </div>
                <!-- /.panel-heading -->
                <div style="padding:20px">
	                <div align="right">		  		
				  		<div style="font-size: 20px">
							기안서 리스트　:　<select name="drftList" id="drftList" size="1" style="width:500px; height:30px;" onchange="clickSelect()">
												<c:forEach var="list" items="${list}" varStatus="status">
													<option value = "${status.current}">
														<c:out value="${list.SBJ}"/>
													</option>
												</c:forEach>
											  </select>
						</div>
	                </div>
	                <div align= "right">
	                    <!-- 결재 테이블 -->
		            	<table border="2" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" style="width: 500px;" > 			 
							<tbody>
						    	<tr>
						    		<td rowspan="3" align="center" width="10%" style="vertical-align:middle;font-size:20px;">결재</td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;">기안</td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleEmpType1" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleEmpType2" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleEmpType3" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleEmpType4" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						   		</tr>
						    	
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">홍길동</td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleName1" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleName2" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleName3" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="settleName4" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    	</tr>					  
						     	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;">-</td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="settleOrder1" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="settleOrder2" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="settleOrder3" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="settleOrder4" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 						    		
						    	</tr>	
						    </tbody>
						</table>
						<!-- 결재 테이블 -->
		            	<table border="2" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" style="width: 500px;" > 			 
							<tbody>
						    	<tr>
						    		<td rowspan="3" align="center" width="10%" style="vertical-align:middle;font-size:20px;">합의</td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentEmpType1" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentEmpType2" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentEmpType3" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentEmpType4" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>	
						    		<td width="18%" class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentEmpType5" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>	
						    	</tr>
						    	
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentName1" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentName2" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentName3" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentName4" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;"><input type="text" id="consentName5" placeholder="/" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td>
						    	</tr>		  
						     	<tr>
						   			<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="consentOrder1" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="consentOrder2" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="consentOrder3" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="consentOrder4" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 
						    		<td class="text-center" style="vertical-align:middle;font-size:13px;"><input type="text" id="consentOrder5" placeholder="-" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly"></td> 		
						    	</tr>	
						    </tbody>
						</table>
						<table class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" style="width: 200px;">
							<tbody>
								<tr> 
									<td class="text-center" style="vertical-align:middle;font-size:12px;">최종 작성자
									</td>
									<td class="text-center" style="vertical-align:middle;font-size:12px;"><input type="text" id="MODID" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td class="text-center" style="vertical-align:middle;font-size:12px;">최종 작성일
									</td>
									<td class="text-center" style="vertical-align:middle;font-size:12px;"><input type="text" id="MODDT" style="width: 80px; text-align:center; background-color:transparent;border:0 solid black;" readonly="readonly">
									</td>
								</tr>		
							</tbody>
						</table>
						<div style="color: RED; ">※ 최종 작성일을 참고해주세요.</div>
					</div>
	                <div>	
					    <table class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline">
					    	<tbody>
					    		<tr>
					    			<td width="40px" style="font-size: 25px; text-align: center;">제목</td>
					    			<td align="center"><input type="text" id="subject" style="height:40px; width:1350px; font-size: 25px; text-align: center;" readonly="readonly"></td>
					    		</tr>
					    		<tr>
					    			<td style="font-size: 25px; height: 300px; text-align: center; vertical-align: middle;">내용</td>
					    			<td align="center"><textarea id="content" style="height:300px; font-size: 25px; width:1350px;" readonly="readonly"></textarea></td>
					    		</tr>
					    	</tbody>
					    </table>		
	                </div>
                </div>
        <!-- /.col-lg-12 -->
    		</div>
    <!-- /.row -->
		</div>  
	</div> 
</div> 
<!-- /#page-wrapper -->

 
