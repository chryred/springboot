<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';

	function clickSelect() {
		var selVal = document.getElementById("selBox");
		console.log("in val");
		console.log(selVal.options[selVal.selectedIndex].value);
		
		if(selVal.options[selVal.selectedIndex].value == "그룹공통시스템" )
		{
			$("#One").css("display","");
			$("#Two").css("display","none");
			console.log("1");
		}
		else if(selVal.options[selVal.selectedIndex].value == "관계사시스템" )
		{
			$("#One").css("display","none");
			$("#Two").css("display","");
			console.log("2");
		} 	
		
	}
	
	function clickRadio(val) {
		console.log(val);
		if( val=="0")
		{
			$("#inputH").attr("disabled",false);
			$("#inputM").attr("disabled",false);
		}
		else
		{
			$("#inputH").attr("disabled",true);
			$("#inputM").attr("disabled",true);
		}
	}
	
	function checkForm() {
		console.log($("#myLocalDate1").val());
		console.log($("#myLocalDate2").val());
		
		if($("#myLocalDate1").val() > $("#myLocalDate2").val())
		{
			alert("현재시간보다 발생시간이 앞설 수 없습니다.");
			return false;
		}
		else
		{
			return true;
		}
	}
</script>

<!-- lib -->
<!-- css -->

<div id="page-wrapper">
	<!-- 1행  -->

	<!-- 2행 -->
	<div class="row">
		<div class="col-lg-12">
		    <div class="panel panel-default">
		        <div class="panel-heading">
					<i class="fa fa-bullhorn fa-fw"></i>
					<span class="fa arrow"></span>&nbsp; 신속한 장애 대응을 위해 리스트를 작성해주세요.
		        </div>
		        <form id="defectForm" action="/defect/defectResult.do" method="post" onsubmit="return checkForm();">
		        <!-- /.panel-heading -->
					<div>
				  	<br>
						<div class="text-center" style="font-size:40px;color: gray;">장애등급 계산/등록</div>
						<br><br><br> 
						
						<div style="vertical-align:middle;font-size:21px;">　①　장애가 발생한 대상 시스템은 무엇인가요?　</div>
					    <br><br>
					    
					    <span style="vertical-align:middle;font-size:20px;"> 	
							　　  　<input type="radio" id="targetSystem" name="targetSystem" value="대고객시스템"		style="width:17px;height:17px;vertical-align:middle;" checked="checked">　대고객시스템<br>　
							　　<input type="radio" id="targetSystem" name="targetSystem" value="POS시스템" 		style="width:17px;height:17px;vertical-align:middle;">　POS시스템<br>　
							　　<input type="radio" id="targetSystem" name="targetSystem" value="영업시스템" 		style="width:17px;height:17px;vertical-align:middle;">　영업시스템<br>　
							　　<input type="radio" id="targetSystem" name="targetSystem" value="영업지원시스템" 	style="width:17px;height:17px;vertical-align:middle;">　영업지원시스템<br>　
							　　<input type="radio" id="targetSystem" name="targetSystem" value="비영업시스템" 		style="width:17px;height:17px;vertical-align:middle;">　비영업시스템
					    </span>
					    <br><br><br>
					    
					    <div style="vertical-align:middle;font-size:21px;">　②　장애가 끼치는 업무영향은 어느정도 인가요?　</div>
					    <br><br>
					    
					    <span style="vertical-align:middle;font-size:20px;"> 	
						　　  　<input type="radio" id="effect" name="effect" value="중단, 사용불가"					style="width:17px;height:17px; vertical-align:middle;" checked="checked">　중단, 사용불가<br>　　
							　<input type="radio" id="effect" name="effect" value="지연, 처리오류, 제한적사용"		style="width:17px;height:17px; vertical-align:middle;">　지연, 처리오류, 제한적사용<br>　　
							　<input type="radio" id="effect" name="effect" value="경미한영향, 불편"				style="width:17px;height:17px; vertical-align:middle;">　경미한영향, 불편<br>　　
							　<input type="radio" id="effect" name="effect" value="영향없음"						style="width:17px;height:17px; vertical-align:middle;">　영향없음
					    </span>				    
					    <br><br><br>
						
						<span style="vertical-align:middle; font-size:21px;">　③　장애범위를 선택해주세요.　　　</span>
						<br><br><br>
						
				　　　　　	<select name="selBox" id="selBox" style="font-size:20px; height:35px;" onchange="clickSelect()">
							<option value="selNone">시스템 선택</option>
							<option value="그룹공통시스템">그룹공통시스템</option>
							<option value="관계사시스템">관계사시스템</option>	
						</select>
						<br><br>
						
						<span id="One" style="vertical-align:middle; font-size:20px; display:none;">
						　　　전관계사		<input type="radio" id="effectRange" name="effectRange" value="전관계사"	style="width:17px;height:17px; vertical-align:middle;" checked="checked">　　
					    	일부관계사		<input type="radio" id="effectRange" name="effectRange" value="일부관계사"	style="width:17px;height:17px; vertical-align:middle;">　　
					    	단일관계사 		<input type="radio" id="effectRange" name="effectRange" value="단일관계사"	style="width:17px;height:17px; vertical-align:middle;">
					    </span>
					    <br>
					
					    <span id="Two" style="vertical-align:middle; font-size:20px; display:none;">
				    	　　　전사업장		<input type="radio" id="effectRange" name="effectRange" value="전사업장"	style="width:17px;height:17px; vertical-align:middle;">　　
					    	일부사업장		<input type="radio" id="effectRange" name="effectRange" value="일부사업장" 	style="width:17px;height:17px; vertical-align:middle;">　　
					    	단일사업장 		<input type="radio" id="effectRange" name="effectRange" value="단일사업장" 	style="width:17px;height:17px; vertical-align:middle;">
					    </span>
						<br><br>
						
						<div style="vertical-align:middle; font-size:21px;">　④　장애가 발생한 시간이 언제인가요?　 　　</div>
						<br><br>
						
						<span style="vertical-align:middle;font-size:20px;"> 	
						　　　발생시간　　　　　　　　　　　　현재시간<br>
						　　　<input type="datetime-local" id="myLocalDate1" name="myLocalDate1" value="${cal}"> - <input type="datetime-local" id="myLocalDate2" name="myLocalDate2" value="${cal}">
						</span>
					    <br><br><br><br>
					    
					    <div class="col-lg-12"><button type="submit" class="btn btn-info btn-lg btn-block" id="btn_save" ><b>결과 확인</b></button></div>	
					    <br><br><br><br><br>
					    
					    <div class="text-center" style="font-size:40px;color: gray;">기준표</div>
						<br><br>
						
						<table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" > 			 
							<tbody>
						    	<tr>
						    		<td rowspan="5" align="center" width="4%" style="vertical-align:middle;font-size:20px;">① 대상 시스템 </td>
						    		<td width="6%" class="text-center" style="vertical-align:middle;font-size:17px;">대고객시스템</td>
						    		<td width="50%" style="vertical-align:middle; font-size:17px;">고객이 직접 사용하는 시스템. 장애발생 시 고객에게 피해를 줄 수 있음.</td>
						   		</tr>
						    	
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">POS시스템</td>
						    		<td style="vertical-align:middle;font-size:17px;">점포/현장에서 사용하는 시스템. 장애발생 시 고객에게 피해를 줄 수 있음.</td>
						    	</tr>
						   
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">영업시스템</td>
						    		<td rowSpan="2" style="vertical-align:middle; font-size:17px;">영업/매출에 영향을 주고, 장애발생 시 고객에게 피해를 줄 수 있음.<br>영업/매출에 영향을 주지 않으나, 장애 발생 시 고객/현업에게 피해를 줄 수 있음.</td>
						    	</tr>
						  
						     	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">영업지원시스템</td> 
						    	</tr>
						   
						     	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">비영업시스템</td>
						    		<td style="vertical-align:middle;font-size:17px;">영업/매출과 무관하고, 장애발생 시 현업에게 피해를 줄 수 있음.</td>
						    	</tr>    
						    </tbody>
						</table>
						<br>
				
						<table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" >
							<tbody>
						   		<tr>
						    		<td rowspan="6" width="4%" class="text-center" style="vertical-align:middle;font-size:20px;"><div>② 업무영향</div></td>
						    		<td width="6%" class="text-center" style="vertical-align:middle;font-size:17px;">중단</td>
						    		<td width="50%" style="vertical-align:middle;font-size:17px;">장애로 인해 IT를 통한 업무처리가 중단되었거나 또는 사용자가 해당업무를 더 이상 수행할 수 없는 상태</td>
						   		</tr>
						  
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">사용불가</td>
						    		<td style="vertical-align:middle;font-size:17px;">장애로 인해 업무를 처리하는 IT를 사용할 수 없는 상태</td>
						    	</tr>
						   
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">지연</td>
						    		<td style="vertical-align:middle;font-size:17px;">IT를 통한 업무 처리가 이루어지거나 처리속도가 상당히 저하된 상태로 업무 또는 사용자가 상당한 영향/불편을 느끼는 상태</td>
						    	</tr>
						  
						     	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">처리오류</td>
						    		<td style="vertical-align:middle;font-size:17px;">IT를 통한 처리 결과가 예상과 다르게 산출되어 해당 업무 또는 사용자에게 상당한 영향/불편을 미치는 상태</td>
						    	</tr>
						   
						     	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">제한적 사용</td> 
						    		<td style="vertical-align:middle;font-size:17px;">IT를 통한 업무가 제한적으로 처리됨으로 인하여 해당 업무 또는 사용자가 상당한 영햔/불편을 느끼는 상태</td>
						    	</tr>
						    	
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle;font-size:17px;">경미한영향/불편</td>
						    		<td style="vertical-align:middle;font-size:17px;">IT와 관련된 경미한 문제로 인해 해당 업무 또는 사용자가 작은 불편을 느끼는 상태</td>
						    	</tr>
						    </tbody>
						</table>			
					    <br>
					    
					    <table border="2" align="center" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" >		 
							<tbody>
						   		<tr>	
						    		<td rowspan="2" align="center" width="4%" style="vertical-align:middle; font-size: 20px">③ 장애범위</td>
						    		<td width="6%" class="text-center" style="vertical-align:middle; font-size:17px;" >그룹공통 시스템</td>
						    		<td width="50%" style="vertical-align:middle; font-size:17px;">전 관계사를 대상으로 운영하는 시스템<br>예) IDC, 블라섬, 인사/재무 시스템 등</td>
						   		</tr>
						  
						    	<tr>
						    		<td class="text-center" style="vertical-align:middle; font-size:17px;">관계사 시스템</td>
						    		<td style="vertical-align:middle; font-size:17px;">특정 관계사를 대상으로 운영하는 시스템 (여러점포/영업장이 있는 경우)<br>예) 각 관계사 영업시스템 등</td>
						    	</tr>
						    </tbody>
						</table>
						
						<div style="vertical-align:middle; font-size:15px;">※ 장애시간은 장애시작시간부터 서비스재개까지의 시간을 의미한다.</div>
					    <br><br><br><br><br><br><br><br><br><br>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>
</div>    
<!-- /#page-wrapper -->
<script type="text/javascript">


</script>
   
</body>
</html>