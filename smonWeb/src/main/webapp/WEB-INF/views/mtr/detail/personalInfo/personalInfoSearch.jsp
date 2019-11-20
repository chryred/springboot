<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/clipboard.min.js"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/resources/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/resources/css/app/personalInfo/personalInfoSearch.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>개인정보 추출 관리</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
							<div class="form-group">
								<div class="col-lg-2">
									<label>DB 명</label>
		                            <select id="dbName" class="form-control" title="개인정보 추출 관리 대상 DB 목록">
									</select>
								</div>
								<div class="col-lg-2">
		                        	<label>소유자</label>
		                            <select id="owner" class="form-control" title="개인정보가 추출된 소유자 목록">
									</select>
								</div>
								<div class="col-lg-2">
		                        	<label>개인정보 구분</label>
		                        	<input type="text" id="infoType" class="form-control" title="개인정보 구분" />
								</div>
								<div class="col-lg-2">
		                        	<label>개인정보 대상 제외여부</label>
		                        	<select id="exceptFlag" class="form-control" title="개인정보 대상 제외여부">
		                        		<option value="">전체</option>
		                        		<option value="N" selected>N</option>
		                        		<option value="Y">Y</option>
									</select>
								</div>
								<div class="col-lg-2">
									<label>&nbsp;</label>
									<div class="input-group">
										<button type="button" class="btn btn-primary btn-circle search-btn" title="조회">
											<i class="fa fa-search" aria-hidden="true"></i>
										</button>&nbsp;
										<button type="button" class="btn btn-info btn-circle save-btn" title="저장">
											<i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i>
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
<textarea id="query" rows="0" cols="0" ></textarea>
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/resources/js/app/personalInfo/personalInfoSearch.js"></script>    
</body>
</html>