<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
 
	// 화면 호출 시 시작하는 함수
	$(document).ready(function(){
		fn_search();
		
		$(document).on("click","#empList td",function(e) {
			console.log($(this).remove());
		});
		
	});
	
 	$(function() {
		$( "#datepicker" ).datepicker(
	 		{
	    		dateFormat	:	'yymmdd'
	 		}
	   	);
	});
 	
 	function fn_search() {
 		$.ajax({
			url 			: 	contextPath + "confSearch.do",
			type			:	"POST"		,
			//data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(data) {	
									var v_str = "";
									var v_url = "/confAdm/attndList.do";
									$.each(data, function(idx,d) { 
										for(var i = 0; i< d.length; i++) {
											
											v_str += '<tr>';
											v_str += '<td align="center"><input type="checkbox" name="chk_user" id="chk_user" onclick="" value="' + d[i].SEQ + '"></td>';
											v_str += '<td class="text-center">' + d[i].SEQ + '</td>';
											v_str += '<td class="text-center">' + d[i].YMD + '</td>';
										    v_str += '<td class="text-center"><a href="<c:url value='/confAdm/attndList.do?SEQ='/>'+d[i].SEQ+'">'+d[i].SBJ+'</a></td>';
										    v_str += '<td class="text-center">' + d[i].REGNAME + '</td>';
										    v_str += '<td class="text-center"><input type="button" name="'+d[i].SEQ+'" id="excelConverBtn" value="엑셀다운" style="cursor:hand;" onclick ="fn_excelDown(this.name)"></td>';
										    v_str += '</tr>';
										}
										
										$("#table_tbody").html(v_str);
									})

								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 	
 	}
 	
 	function fn_excelDown(seq) {
 		location.href="/confAdm/excelDown.do?SEQ="+ seq;
 	}
 	
 	function fn_attndList(seq) {
 		console.log(seq);
 		
 		var postData = {
 				SEQ : seq
 		};
 		
 		$.ajax({
			url 			: 	contextPath + "attndList.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(data) {	
									
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 	
 		
 	}
 	
 	function btn_save(){
 		var postData = {
				"YMD" 			:	$("#datepicker").val(),
				"SBJ"			:	$("#confSubject").val(),
				"REGID"			:	$("#confHost").val()
 		}
 		
 		$.ajax({
			url 			: 	contextPath + "confSave.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(data) {		
									fn_search();
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 		
 	}
 	
 	function btn_emp_save() {
 		var v_str = "";
 		for(var i = 0; i<$("#empList td").length; i++) {
 			if(i == $("#empList td").length -1) {
 				v_str += $("#empList td")[i].id;
 			} else {
 				v_str += $("#empList td")[i].id + ",";
 			}	
 		}
 		
 		$("#confHost").val(v_str);
 		
 	}
 	
 	function btn_search() {
 		console.log($("#findEmp").val());
 		
 		var postData = {
				"STDID"	:	$("#findEmp").val()
 		}
 		
 		$.ajax({
			url 			: 	contextPath + "searchHost.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(data) {
									console.log(data);
									console.log(data.data);
									var d = data.data;
									var v_str = "";
									for(var i = 0; i< d.length; i++) {
										console.log(d[i]);
										
										v_str += '<tr>';
									    v_str += '<td class="text-center" id="'+d[i].STDID+","+d[i].NAME+'" name="'+d[i].STDID+'" onclick ="fn_selEmp(this.id)">' + d[i].STDID + '</td>';
									    v_str += '<td class="text-center" id="'+d[i].STDID+","+d[i].NAME+'" name="'+d[i].STDID+'" onclick ="fn_selEmp(this.id)">' + d[i].NAME + '</td>';
									    v_str += '</tr>';
									}
										
									$("#emp_tbody").html(v_str);
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 	
 	}
 	
 	function fn_selEmp(data) {
 		console.log(data);
 		var dataSplt = data.split(',');
 		var alrdyYN = "0";
 		var v_str = "";
 		v_str = '<td class="text-center" style="font-size:20px; padding:10px; background-color:#FAED7D" id="'+dataSplt[0]+'" name="'+dataSplt[1]+'">' + dataSplt[1] + "&nbsp;x" + '</td>';
 		
 		if($("#empList td").length == 5) {
 			return;
 		}
 		
 		if($("#empList td").length == 0) {
 			$("#empList").append(v_str);
 		} else {
	 		for(var i = 0; i<$("#empList td").length; i++) {
	 			if($("#empList td")[i].id.toString() == dataSplt[0].toString()) {
	 				alrdyYN = "1";
	 			}
	 		}
	 		
	 		if(alrdyYN != "1") {
		 		$("#empList").append(v_str);
		 	}
 		}
 	}
 	
 	function btn_del() {
 		
 		var checkbox =$("input[name=chk_user]:checked");
 		
 		if (checkbox.size() == 0) {
 			alert("삭제할 공유회를 선택해주세요.");
 			return;
 		}
 		
 		if (checkbox.size() > 1) {
 			alert("다중 삭제는 불가합니다.");
 			return;
 		}
 		if(confirm("정말 삭제하시겠습니까?") == true) {
			var tr = checkbox.parent().parent().eq(0);
			var td = tr.children();
			
			var postData = {
					SEQ : td.eq(1).text()	
			};
			
			$.ajax({
				url 			: 	contextPath + "deleteConf.do",
				type			:	"POST"		,
				data			:	postData	,
				dataType 		: 	"json"		,
				success 		: 	function(data) {
										alert("삭제 되었습니다.");
										fn_search();
									},
				error			:	function(result) {
										console.log("2");
										console.log(result);
									}
			}); 	
 		} else {
 			return;
 		}
 	}
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/ztree/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/batch.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.qtip.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.css" />


<style type="text/css">
.form-control {
 height: 30px;
 font-size: 12px;
}

select {
 border: 1px solid #dcdcdc;
 font-size: 1em;
 height: 25px;
 padding: 2px;
 margin: 1px 0;
}

input.calendar {
 /* border-color: #ACACAC #D9D9D9 #D9D9D9 #ACACAC;*/
 border: 1px solid #dcdcdc;
 padding: 2px 0 0 5px;
 font-size: 1em;
 height: 21px;
 margin: 1px 0;
 width: 75px;
}

input.inputbox {
 /* border-color: #ACACAC #D9D9D9 #D9D9D9 #ACACAC;*/
 border: 1px solid #dcdcdc;
 height: 25px; 
}

.table_list, .table_view{table-layout:fixed; border-collapse:collapse; border-spacing:0; width:100%;border:1px solid #dcdcdc;}
.table_list thead tr th{background:#F5F5F5; color:darkblue;font-weight:bold;padding:5px;text-align:center;vertical-align:middle}
.table_list thead tr td{color:darkblue;font-weight:normal;padding:5px;text-align:left;vertical-align:middle}
.table_list tbody tr td{border-bottom:1px solid #dcdcdc; border-left:1px solid #dcdcdc; padding:5px 5px; text-align:center;}

.ui-datepicker{z-index: 99 !important};

</style>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>공유회 리스트 관리</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	<div class="col-lg-12">
        	<div class="panel panel-default">
            	<div class="panel-heading">
                	<i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;공유회 리스트 관리 화면입니다.
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body text-right" style="margin-bottom:0px;padding-bottom:0px;">
                	<div id="toggle" style="position:absolute;">
                  		<select id="pageScale" style="">
             				<option value="10" selected>10</option>
				          	<option value="20">20</option>
				          	<option value="50">50</option>
         				</select> 
                 	</div>
              	</div>
                <div class="panel-body">
                 	<div class="col-lg-12 verticalCenter" style=";margin-top:10px;margin-bottom:20px;padding:0px 0px 0px 0px;">
                  		<table class="table_list">
                   			<thead>
                    			<tr> 
                    				<th style="width:5%">일자</th>
               						<td style="width:10%">
                						<input type="text" id="datepicker" style="width:100%">
            						</td>
            						<th style="width:10%">공유회 제목</th>
               						<td style="width:30%">
                						<input id="confSubject" class="inputbox" style="width:100%">
           	 						</td>
           	 						<th style="width:10%">공유회 진행자</th>
               						<td style="width:30%">
                						<input id="confHost" class="inputbox" data-toggle="modal" data-target="#myModal" readonly="readonly">
           	 						</td>
               						<td>
                						<button class="btn btn-default btn-xs" style="float:right" type="button" id="btn_save" onclick="btn_save()">&nbsp;저장&nbsp;</button>
           	 						</td>
            					</tr>
          					</thead>
         				</table>
                 	</div>
                    <!-- /.col-lg-10 -->
                </div>
                <div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog">	    
		    			<!-- Modal content-->
		      			<div class="modal-content">
		        			<div class="modal-header">
		          				<button type="button" class="close" data-dismiss="modal">&times;</button>
		          				<h4 class="modal-title">공유회 진행자 선정</h4>
		        			</div>					 
							<div>
								<table>
									<tbody id="empList">
										<tr style="font-size:10px" id="empList_tr">
										</tr>
		                        	</tbody>
		                        </table>
							</div>
		        			<table>
		        				<tr>
			        				<th>사번/이름</th>
	               					<td>
	                					<input id="findEmp" class="inputbox" style="width:100%">
	           	 					</td>
	           	 					<td>
	                					<button class="btn btn-default btn-xs" type="button" id="btn_search" onclick="btn_search()">&nbsp;검색&nbsp;</button>
	           	 					</td>
	           	 				</tr>
           	 				</table>
           	 				<table class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" id="EmpTable" style="padding-top:0px; overflow:auto; height:100px">
		                        <thead>
		                            <tr role="row">
		                            	<th class="text-center" style="width:20%;font-weight:bold;" colidx="0">사원아이디</th>
		                                <th class="text-center" style="width:70%;font-weight:bold;" colidx="1">사원명</th>                             
		                            </tr>
		                        </thead>
		                        <tbody id="emp_tbody" style="height:100px">
		                        </tbody>
                    		</table>
		        			<div class="modal-footer">
		        				<button type="button" class="btn btn-default" data-dismiss="modal" onclick="btn_emp_save()">저장</button>
		          				<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
		        			</div>
		      			</div>  
	    			</div>
				</div>
                <!-- /.panel-body -->
              		<!-- <div> 
                		<button class="btn btn-default btn-xs" type="button" id="btn_search" style="float:right;">&nbsp;조회&nbsp;</button>
                	</div> -->
                <!-- /.panel-heading -->
                <div class="col-lg-12">
	            	<a style="float:right; color:red;">※ 참석리스트는 공유회 진행자 및 품질담당자만 열람 가능합니다.</a>
	            </div>
	            <br>
	            <div class="col-lg-12">
	            	<button style="float:right;" onclick="btn_del()">삭제</button>
	            </div>
	           	<br>
	           	<br>
                <div class="panel-body" style="padding-top:0px; overflow:auto">
                    <table style="width:100%;" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" id="dataTable">
                        <thead>
                            <tr role="row">
                             <th align="center" style="width:4%;font-weight:bold;">
                              <input type="checkbox" id="chk_no" class="cb">
                             </th>
                             	<th class="text-center" style="width:5%;font-weight:bold;" colidx="0">SEQ</th>
                                <th class="text-center" style="width:20%;font-weight:bold;" colidx="1">일자</th>
                                <th class="text-center" style="width:60%;font-weight:bold;" colidx="2">공유회 제목</th>
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="3">공유회 진행자</th>                                
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="4">참석자 명단</th>                                
                            </tr>
                        </thead>
                        <tbody id="table_tbody">
                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                    <div class="" id="div_count" style="margin-top:0px;text-align:left;padding:0px;"></div>
                    <div class="" id="div_page" style="margin-top:0px;text-align:center;padding:0px;"></div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
       
    </div>
    <!-- /.row -->