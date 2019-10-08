<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

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
<link href="<%=request.getContextPath() %>/css/app/personalInfoCheck/personalInfoCheck.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>개인정보 확인</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
							<div class="form-group">
								<div class="col-lg-2" hidden="true">
									<label>DB 명</label>
		                            <select id="dbName" class="form-control">
									</select>
								</div>
								<div class="col-lg-2" hidden="true">
		                        	<label>소유자</label>
		                            <select id="owner" class="form-control">
									</select>
								</div>
								<div class="col-lg-2" hidden="true">
		                        	<label>조회단위</label>
                                    <select id="group" class="form-control">
                                        <option value="D">DB</option>
                                        <option value="O">OWNER</option>
                                        <option value="T">TABLE</option>
                                    </select>
                                </div>
                                <div class="col-lg-1" hidden="true">
                                    <label>조회</label>
									<button type="button" class="btn btn-primary btn-circle search-btn" id="search"title="조회">
                                        <i class="fa fa-search" aria-hidden="true"></i>
                                    </button>
							    </div>
							    <div class="col-lg-1">
                                    <label>자동조회</label>
                                    <button type="button" class="btn btn-primary btn-circle search-btn" id="autoSearch" title="자동조회">
                                        <i class="fa fa-search" aria-hidden="true"></i>
                                    </button>
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

<script src="<%=request.getContextPath() %>/js/app/personalInfoCheck/personalInfoCheck.js"></script>    



</body>
</html>