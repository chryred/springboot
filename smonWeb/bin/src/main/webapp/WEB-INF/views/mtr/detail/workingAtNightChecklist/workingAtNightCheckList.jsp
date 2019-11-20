<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/clipboard.min.js"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoSearch.css" rel="stylesheet">
<div>
</div>
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
							<div class="form-group">
								<div class="col-lg-2">
									<label>호스트명</label>
		                            <select id="dbName" class="form-control" title="">
									</select>
								</div>
								<div class="col-lg-2" >
		                        	<label>IP</label>
		                            <select id="owner" class="form-control" title="">
									</select>
								</div>
								<div class="col-lg-2" >
		                        	<label>시스템명</label>
		                        	<input type="text" id="infoType" class="form-control" title="" />
								</div>
							
																<div class="col-lg-2">
									<label>&nbsp;</label>
										
									<div class="input-group">
										<button type="button" class="btn btn-primary btn-circle search-btn" title="조회">
											<i class="fa fa-search" aria-hidden="true"></i>
										</button>
									</div>
		                        </div>
		                        
								<div class="col-lg-2" style="display:none">
		                        	<label>포함 메시지 내용</label>
		                        	<select id="exceptFlag" class="form-control" title="">

									</select>
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

<script src="<%=request.getContextPath() %>/js/app/workingAtNightCheckList/workingAtNightCheckList.js"></script>    
</body>
</html>