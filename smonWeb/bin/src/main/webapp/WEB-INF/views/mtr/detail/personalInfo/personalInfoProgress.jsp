<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<!-- lib -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datatables-plugins/api/fnFakeRowspan.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script>
<script src="<%=request.getContextPath() %>/js/select2/select2.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/select2.min.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoProgress.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <p>개인정보 추출 현황</p>
                </div>
                <div class="panel-body">
                	<div class="row">
	                   	<div class="col-lg-12 filter-wrapper">
							<label for="dbName">
								<p style="display: inline; font-size: 16px">DB 명</p>&nbsp;
								<p style="display: inline;">
		                    		<button type="button" id="all" class="btn btn-success btn-xs">ALL</button>
		                    		<button type="button" id="clear" class="btn btn-info btn-xs">CLEAR</button>
		                    	</p>
							</label>
							<div class="col-lg-11">
	                        	<select id="dbName" multiple="multiple" style="width: 100%;"></select>
	                        </div>
	                        <div class="col-lg-1">
								<div class="radio">
									<label>
										<input type="radio" name="periodType" class="periodType" value="month">월
									</label>
									<label>
										<input type="radio" name="periodType" class="periodType" value="day" checked>일
									</label>
								</div>
	                        </div>
	                    </div>
                    </div>
                    <div class="panel-group">
	                    <div class="panel panel-default">
			                <div class="panel-heading">
			                    <p>이메일</p>
			                </div>
			                <div class="panel-body">
			                	<canvas id="emailChart" height="50"></canvas>
			                </div>
						</div>
	                    <div class="panel panel-default">
			                <div class="panel-heading">
			                    <p>전화번호</p>
			                </div>
			                <div class="panel-body">
			                	<canvas id="telnoChart" height="50"></canvas>
			                </div>
						</div>
	                    <div class="panel panel-default">
			                <div class="panel-heading">
			                    <p>주민등록번호</p>
			                </div>
			                <div class="panel-body">
			                	<canvas id="juminChart" height="50"></canvas>
			                </div>
						</div>
					</div>
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

<script src="<%=request.getContextPath() %>/js/app/personalInfo/personalInfoProgress.js"></script>    
</body>
</html>