<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%-- <jsp:include page="../../common/navigation.jsp" flush="false"/> --%>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/jquery-ui_v1.10.4.css" />
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoSearch.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>개인정보 관리</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
							<div class="form-group">
								<div class="col-lg-2">
									<label>DB 명</label>
		                            <select id="dbName" class="form-control">
									</select>
								</div>
								<div class="col-lg-2">
		                        	<label>확인일자</label>
		                        	<input type="text" id="checkDate" class="form-control" data-date-format="yyyy-mm-dd" readonly="readonly">
								</div>
								<div class="col-lg-2">
		                        	<label>소유자</label>
		                            <select id="owner" class="form-control">
									</select>
								</div>
								<div class="col-lg-2">
		                        	<label>구분</label>
		                        	<input type="text" id="infoType" class="form-control" />
								</div>
								<div class="col-lg-2">
									<label>&nbsp;</label>
									<div class="input-group date" data-provide="datepicker">
										<button type="button" class="btn btn-primary btn-circle search-btn" title="조회">
											<i class="fa fa-search" aria-hidden="true"></i>
										</button>&nbsp;
										<button type="button" class="btn btn-danger btn-circle except-btn" title="선택항목을 모니터링 대상에서 제외합니다.">
											<i class="fa fa-times" aria-hidden="true"></i>
										</button>
									</div>
		                        </div>
							</div>
                        </div>
					</div>
				</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="table-wrapper">
                                <table id="dataTables" class="table table-bordered table-hover table-striped">
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.col-lg-4 (nested) -->
                        <div class="col-lg-8">
                            <div id="morris-bar-chart"></div>
                        </div>
                        <!-- /.col-lg-8 (nested) -->
                    </div>
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

<script src="<%=request.getContextPath() %>/js/app/personalInfo/personalInfoSearch.js"></script>    
</body>
</html>