<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- Script for editable table-->
<link rel="stylesheet" type="text/css" href="http://www.prepbootstrap.com/Content/shieldui-lite/dist/css/light/all.min.css" />
<script type="text/javascript" src="http://www.prepbootstrap.com/Content/shieldui-lite/dist/js/shieldui-lite-all.min.js"></script>
<script type="text/javascript" src="http://www.prepbootstrap.com/Content/data/shortGridData.js"></script>

<!-- Script for editable table -->
<!-- lib -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/css/app/personalInfoCheck/personalInfoCheck.css" rel="stylesheet">

<div id="page-wrapper">


    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>야간작업 점검 시스템</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
						</div>
				</div>
		    
                <!-- /.panel-heading -->
                <div class="panel-body">
                
	   		<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="text-center">Bootstrap Editable jQuery Grid <span class="fa fa-edit pull-right bigicon"></span></h4>
        </div>
        <div class="panel-body text-center">
            <div id="grid"></div>
        </div>
    </div>
</div>

                    <!-- <div class="row">
                    	<div class="col-lg-9">
                    	</div>
                    	<div class="col-lg-3">
                    		<button id='btn-add-row'>행 추가하기</button>
							<button id='btn-delete-row'>행 삭제하기</button>
							<button id='btn-save-table'>저장</button>
                    	</div>
                    	<div class="col-lg-12">
                    		<br>
                    	</div>
	                    <div class="col-lg-1">
							<div class="list-group">
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('운영정보')">운영정보</a></strong> 
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('CRM')">CRM</a>       </strong>
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('통합고객')">통합고객</a>  </strong>
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('그룹정보')">그룹정보</a>  </strong>
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('워크스마트')">워크스마트</a></strong>
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('직 매입')">직 매입</a>   </strong>
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('배송')">배송</a>      </strong>
							  <strong><a href="#" class="list-group-item" onclick="checkTodoList('EDI')" >협력회사EDI</a></strong>
							</div>
	                    </div>
                        <div class="col-lg-11">
                        <table id="mytable" class="table table-striped table-bordered table-hover" style="text-align: center">
                        <thead>
                        	<tr>
								<td rowspan="3"><br>호스트명</td>
								<td rowspan="3"><br>IP</td>
								<td rowspan="3"><br>시스템명</td>
								<td rowspan="3"><br>포함 메시지 내용</td>
								<td rowspan="3"><br>프로그램명</td>
								<td rowspan="3"><br>설명</td>
								<td rowspan="3"><br>담당자</td>
								<td colspan="4" >상황실 처리 방법</td>
								<td rowspan="3"><br>비고</td>
								<td rowspan="3"><br>발생</td>
								<td rowspan="3"><br>위험도</td>
							</tr>
							<tr>
								<td colspan="2">영업시간(09:00 ~ 21:00)</td>
								<td colspan="2">영업시간 外</td>
							</tr>
							<tr>
								<td>블톡 or PUSH</td>
								<td>전화</td>
								<td>블톡 or PUSH</td>
								<td>전화</td>
							</tr>
                        </thead>
							<tbody>
							
							</tbody>
							</table>
                        </div>
                    </div> -->
                    <!-- /.row -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->
	<script src="<%=request.getContextPath() %>/js/app/polestarErrorMessageGuide/polestarErrorMessageGuide.js"></script>  
    <!-- Morris -->
    <script src="bootstrap/bower_components/raphael/raphael-min.js"></script>
    <script src="bootstrap/bower_components/morrisjs/morris.min.js"></script>
    
    <!-- Flot Charts JavaScript -->
    <script src="bootstrap/bower_components/flot/excanvas.min.js"></script>
    <script src="bootstrap/bower_components/flot/jquery.flot.js"></script>
    <script src="bootstrap/bower_components/flot/jquery.flot.pie.js"></script>
    <script src="bootstrap/bower_components/flot/jquery.flot.resize.js"></script>
    <script src="bootstrap/bower_components/flot/jquery.flot.time.js"></script>
    <script src="bootstrap/bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script>

    <!-- ChartJS-->
    <script src="js/charts.js"></script>
</body>
</html>