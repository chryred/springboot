<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<script type="text/javascript"> 
	var contextPath = '<%=request.getContextPath()%>';
	var checkIfNew;	// 1이면 new, 2이면 update
	// 화면 호출 시 시작하는 함수
	$(document).ready(function(){
		var seq = '${SEQ}';
		fn_search(seq);

		$(document).on("click", "#dataTable td", function(e) {
			var data = $(this).attr('id');
			var spltData = data.split(',');
			
			if(spltData[3] == "0") {
				spltData[3] = "1";
			} else {
				spltData[3] = "0";	
			}
			
			var postData = {
	 				STDID 	: 	spltData[0] ,
	 				YMD   	: 	spltData[1] ,
	 				SEQ		:	spltData[2]	,
	 				ATTNDYN : 	spltData[3]
	 		};
			
			$.ajax({
				url 			: 	contextPath + "attndChng.do",
				type			:	"POST"		,
				data			:	postData	,
				dataType 		: 	"json"		,
				success 		: 	function(result) {	
										fn_search(result.confVO.seq);
									},
				error			:	function(result) {
										console.log("2");
										console.log(result);
									}
			}); 	
		});
		
	});
	
	
	
	function fn_search(seq) {
		fn_selConfName(seq);
		fn_selAttnd(seq);
	}
	
	function fn_selConfName(seq) {
		var postData = {
 				SEQ : seq
 		};
 		
 		$.ajax({
			url 			: 	contextPath + "selConfName.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(result) {	
									console.log(result);
									var d = result.data;
									document.getElementById('YMD').innerHTML = result.data[0].YMD; 
									document.getElementById('SBJ').innerHTML = result.data[0].SBJ; 
									document.getElementById('REGNAME').innerHTML = result.data[0].REGNAME; 
									
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 	
	}
	
	function fn_selAttnd(seq) {
		
		var postData = {
 				SEQ : seq
 		};
 		
 		$.ajax({
			url 			: 	contextPath + "searchAttndList.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(result) {	
									var list = result.data;
									var v_str = "";
									var cnt = 0;

									for(var i = 0; i< list.length; i++) {
										
										if(cnt == 0) {
											v_str += '<tr>';
										}
										
										if(list[i].ATTNDYN == "0") {
										    v_str += '<td id='+list[i].STDID+ ","+list[i].YMD + "," + list[i].SEQ + ","  + list[i].ATTNDYN +' style="text-align:center; vertical-align: middle; border-collapse:collapse; border:1px gray solid; font-size:30px; height:150px;" readonly="readonly" value=' + list[i].NAME + '>'+list[i].NAME+'</td>';
										} else {
										    v_str += '<td id='+list[i].STDID+ ","+list[i].YMD + "," + list[i].SEQ + ","  + list[i].ATTNDYN +' style="background-color:#DAD9FF; vertical-align: middle; border-collapse:collapse; border:1px gray solid; text-align:center; font-size:30px; height:150px;" readonly="readonly" value=' + list[i].NAME + '>'+list[i].NAME+'</td>';
										}
									    if(cnt == 5) {
									    	v_str += '</tr>';
									    	cnt = 0;
									    } else {
									    	cnt ++;
									    }
									    
									}
									
									if(cnt != 0) {
										v_str += '</tr>';
									}
									
									$("#table_tbody").html(v_str);
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 	
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
<link rel=stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
<style type="text/css">
label {
	height: 34px !important;
	line-height: 34px !important;
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

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>참석자 명단 리스트</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading btn-box">
                	<i class="fa fa-bullhorn fa-fw"></i>&nbsp;참석유무를 관리하는 화면입니다.
                </div>
                <div class="panel-body">
              	  	<div style=";margin-top:10px;margin-bottom:20px;padding:0px 0px 0px 0px;">
		                <table class="table_list">
		                	<tr> 
		             			<th style="width:7%; font-size:17px; text-align:center">일자</th>
		       					<td style="width:10%; text-align:center; font-size:17px; text-align:center" id="YMD">
		    					</td>
		    					<th style="width:8%; font-size:17px; text-align:center">공유회 제목</th>
		       					<td style="width:20%; text-align:center; font-size:17px" id="SBJ"></td>
		     				</tr>
		     				<tr>
		   	 					<th style=" font-size:17px; text-align:center">공유회 진행자</th>
		       					<td style=" text-align:center;font-size:17px; text-align:center" id="REGNAME"></td>
							</tr>
		                </table>
		        	</div>
	            </div>
	            <div class="col-lg-12">
	            	<h3>&nbsp;참석자 명단</h3>
	            </div>
                <!-- /.panel-heading -->
                <div class="panel-body" style="padding-top:0px;">
                    <table width="100%" class="table_list" id="dataTable">
                        <thead>
                            <tr role="row">
                                <th class="text-center" style="font-weight:bold; border-collapse:collapse; border:1px gray solid;" colidx="0"></th>
                                <th class="text-center" style="font-weight:bold; border-collapse:collapse; border:1px gray solid;" colidx="1"></th>                                
                                <th class="text-center" style="font-weight:bold; border-collapse:collapse; border:1px gray solid;" colidx="2"></th>
                                <th class="text-center" style="font-weight:bold; border-collapse:collapse; border:1px gray solid;" colidx="3"></th>
                                <th class="text-center" style="font-weight:bold; border-collapse:collapse; border:1px gray solid;" colidx="4"></th>                                
                                <th class="text-center" style="font-weight:bold; border-collapse:collapse; border:1px gray solid;" colidx="5"></th>                            
                            </tr>
                        </thead>
                        <tbody id="table_tbody">
                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                    <div class="" id="div_count" style="margin-top:0px;text-align:left;padding:0px;"></div>
                    <div class="" id="div_page" style="margin-top:0px;text-align:center;padding:0px;"></div>
                </div>
        <!-- /.col-lg-12 -->
    		</div>
    <!-- /.row -->
		</div>  
	</div> 
</div> 
<!-- /#page-wrapper -->

 
