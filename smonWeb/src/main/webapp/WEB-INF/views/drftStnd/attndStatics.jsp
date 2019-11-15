<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
 
	// 화면 호출 시 시작하는 함수
	$(document).ready(function(){
		var dt = new Date();
		
		setDateBox();
		makeChart();
	});
	
	function setDateBox(){
        var dt = new Date();
        var year = "";
        var com_year = dt.getFullYear();
        // 발행 뿌려주기
        $("#YEAR").append("<option value='"+ com_year +"'>"+com_year +" 년</option>");
        // 올해 기준으로 -1년부터 +5년을 보여준다.
        for(var y = (com_year-5); y <= (com_year+5); y++){
            $("#YEAR").append("<option value='"+ y +"'>"+ y + " 년" +"</option>");
        }
    }


	
	function makeChart() {
		var halfYear;
		if($("#SEMIANNUAL").val()=="상반기") {
			halfYear = "0";
		} else {
			halfYear = "1";
		}
		
		var postData = {
				YMD : $("#YEAR").val(),
				HALFYEAR : halfYear
			};
		$.ajax({
			url 			: 	contextPath + "searchAttndResult.do",
			type			:	"POST"		,
			data			:	postData	,
			dataType 		: 	"json"		,
			success 		: 	function(result) {	
				var listArr = result.data;
				var s_name = new Array();
				var s_cnt = new Array();
				var s_color = new Array();
				var s_color2 = new Array();
				
				for(var i=0; i<listArr.length; i++) {
					s_name[i] 	= listArr[i].NAME;
					s_cnt[i] 	= listArr[i].CNT;
					s_color[i] 	= "rgba(54, 162, 235, 0.2)";
					s_color2[i] = "rgba(54, 162, 235, 1)";
				}
								
				var ctx = document.getElementById("myChart").getContext('2d');
				var myChart = new Chart(ctx, {
				    type: 'bar',
				    data: {
				        labels: s_name,
				        datasets: [{
				            label: '# of Votes',
				            data: s_cnt,
				            backgroundColor: s_color,
				            borderColor: s_color2,
				            borderWidth: 1
				        }]
				    },
				    options: {
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero:true,
				                    stepSize:1
				                }
				            }]
				        }
				    }
				});
								},
			error			:	function(result) {
									console.log("2");
									console.log(result);
								}
		}); 		
	}
	
		
	

    


</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/ztree/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/plugins/morris/morris.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>

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

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>참석자 통계 관리</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	<div class="col-lg-12">
        	<div class="panel panel-default">
            	<div class="panel-heading">
                	<i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;공유회 참석자 통계관리 화면입니다.
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                 	<div class="col-lg-12 verticalCenter" style=";margin-top:10px;margin-bottom:20px;padding:0px 0px 0px 0px;">
                  		<table class="table_list">
                   			<thead>
                    			<tr> 
                    				<th style="width:2%">년도</th>
               						<td style="width:2%">
                						<select name="YEAR" id="YEAR" title="년도" class="select w80"></select> 
            						</td>
            						<th style="width:2%">반기</th>
               						<td style="width:10%">
                						<select name="SEMIANNUAL" id="SEMIANNUAL" style="height:35px;" onchange="makeChart()">
											<option value="상반기">상반기</option>
											<option value="하반기">하반기</option>	
										</select> 
            						</td>  						
            					</tr>
          					</thead>
         				</table>
                 	</div>
                    <!-- /.col-lg-10 -->
                </div>
               
                <!-- /.panel-body -->
              		<!-- <div> 
                		<button class="btn btn-default btn-xs" type="button" id="btn_search" style="float:right;">&nbsp;조회&nbsp;</button>
                	</div> -->
                <!-- /.panel-heading -->
   				<div class="container">
        			<canvas id="myChart" width="800" height="200"></canvas>
    			</div>
	
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
       
    </div>
    <!-- /.row -->