<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<!-- lib -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script>
<script src="<%=request.getContextPath() %>/js/select2/select2.min.js"></script>

<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/select2.min.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoProgress.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <p><b>개인정보 현황</b></p>
                </div>
                <div class="panel-body">
                   	<div class="col-lg-12 condition-wrapper">
                   		<div class="row form-inline">
	                   		<div class="col-lg-3">
							  <div class="form-group">
							    <label for="combo_division">사업부</label>
							    <select class="form-control" id="combo_division">
                                	<option>A사업부</option>
                                	<option>B사업부</option>
                                </select>
							  </div>
							</div>
							<div class="col-lg-3">
							  <div class="form-group">
							    <label for="combo_team">팀</label>
							    <select class="form-control" id="combo_team">
                                	<option>전체</option>
                                	<option>1</option>
                                	<option>2</option>
                                	<option>3</option>
                                	<option>4</option>
                                	<option>5</option>
                                	<option>6</option>
                                </select>
							  </div>
							</div>
							<div class="col-lg-6">
								<div class="form-group">
									<label class="checkbox-inline"><input type="checkbox" value="" checked="checked">이메일</label>
									<label class="checkbox-inline"><input type="checkbox" value="" checked="checked">전화번호</label>
									<label class="checkbox-inline"><input type="checkbox" value="" checked="checked">주민등록번호</label>
								</div>
							</div>
						</div>
						<div class="row form-inline" style="margin-top: 10px;">
							<div class="col-lg-12 form-group">
								<div class="col-lg-3">
									<label class="radio-inline"><input type="radio" class="radio_period" id="radio_period_year" name="period" value="year">년</label>
								</div>
								<div class="col-lg-3">
									<label class="radio-inline"><input type="radio" class="radio_period" id="radio_period_half" name="period" value="half">반기</label>
									<select class="form-control" id="combo_team">
	                                	<option>상반기</option>
	                                	<option>하반기</option>
	                                </select>
                                </div>
                                <div class="col-lg-3">
									<label class="radio-inline"><input type="radio" class="radio_period" id="radio_period_quater" name="period" value="quater" checked="checked">분기</label>
									<select class="form-control" id="combo_team">
	                                	<option selected="selected">1/4분기</option>
	                                	<option>2/4분기</option>
	                                	<option>3/4분기</option>
	                                	<option>4/4분기</option>
	                                </select>
                                </div>
                                <div class="col-lg-3">
									<label class="radio-inline"><input type="radio" class="radio_period" id="radio_period_recent" name="period" value="recent">최근3개월</label>
								</div>
							</div>
						</div>
                    </div>
                    <div class="col-lg-12">
                    	<div class="row">
                    		<div class="panel-group chart-wrapper">
	                    		<div class="panel panel-default">
					                <div class="panel-heading">
					                    <p><b>이메일</b></p>
					                </div>
					                <div class="panel-body pre-scrollable" style="height: 300px;">
					                	<div class="col-lg-4">
			                    			<canvas id="myChart1-1"></canvas>
			                    		</div>
			                    		<div class="col-lg-4">
			                    			<canvas id="myChart1-2"></canvas>
			                    		</div>
			                    		<div class="col-lg-4">
			                    			<canvas id="myChart1-3"></canvas>
			                    		</div>
					                </div>
								</div>
	                    		<div class="panel panel-default">
					                <div class="panel-heading">
					                    <p><b>전화번호</b></p>
					                </div>
					                <div class="panel-body pre-scrollable" style="height: 300px;">
					                	<div class="col-lg-4">
			                    			<canvas id="myChart2-1"></canvas>
			                    		</div>
			                    		<div class="col-lg-4">
			                    			<canvas id="myChart2-2"></canvas>
			                    		</div>
			                    		<div class="col-lg-4">
			                    			<canvas id="myChart2-3"></canvas>
			                    		</div>
					                </div>
								</div>
	                    		<div class="panel panel-default">
					                <div class="panel-heading">
					                    <p><b>주민등록번호</b></p>
					                </div>
					                <div class="panel-body pre-scrollable" style="height: 300px;">
		                    			<div class="col-lg-4"><canvas id="myChart3-1"></canvas></div>
		                    			<div class="col-lg-4"><canvas id="myChart3-2"></canvas></div>
		                    			<div class="col-lg-4"><canvas id="myChart3-3"></canvas></div>
		                    			<div class="col-lg-4"><canvas id="myChart3-4"></canvas></div>
		                    			<div class="col-lg-4"><canvas id="myChart3-5"></canvas></div>
		                    			<div class="col-lg-4"><canvas id="myChart3-6"></canvas></div>
					                </div>
								</div>
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

<script src="<%=request.getContextPath() %>/js/rgba-data.js"></script>
<script src="<%=request.getContextPath() %>/js/app/personalInfo/divisionPersonalInfoProgress.js"></script>    
</body>
</html>