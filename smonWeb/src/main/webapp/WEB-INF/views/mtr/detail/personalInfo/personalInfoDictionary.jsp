<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
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
<link href="<%=request.getContextPath() %>/resources/css/app/personalInfo/personalInfoDictionary.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>개인정보 Object 관리</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                    	<div class="col-lg-6">
	                    	<div class="panel panel-default">
				                <div class="panel-heading">
									<p>테이블 목록</p>
				                </div>
					            <div class="panel-body">
					            	<div class="search-condition-table col-lg-12">
										<div class="form-group">
											<div class="col-lg-2">
												<label>DB 명</label>
					                            <select id="dbName" class="form-control">
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>소유자</label>
					                            <select id="owner" class="form-control">
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>타입</label>
					                            <select id="objectType" class="form-control">
					                            	<option value="" selected>전체</option>
					                            	<option value="TABLE">테이블</option>
					                            	<option value="VIEW">뷰</option>
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>개인정보</label>
					                            <select id="personalInfoFlag" class="form-control">
					                            	<option value="" selected>전체</option>
					                            	<option value="Y">포함</option>
					                            	<option value="N">미포함</option>
					                            	<option value="NN">미확정</option>
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>VALID</label>
					                            <select id="status" class="form-control">
					                            	<option value="" selected>전체</option>
					                            	<option value="VALID">VALID</option>
					                            	<option value="INVALID">INVALID</option>
												</select>
											</div>
											<div class="col-lg-2">
												<label>&nbsp;</label>
												<p>
													<button type="button" class="btn btn-primary btn-circle search-btn" title="조회">
														<i class="fa fa-search" aria-hidden="true"></i>
													</button>
												</p>
					                        </div>
											<div class="col-lg-2">
					                        	<label>오브젝트명</label>
					                            <input id="object_name" type="text" class="form-control" />
											</div>
										</div>
			                        </div>
			                        <div class="col-lg-12">
			                            <div class="table-wrapper">
			                                <table id="dataTable1" class="table table-bordered table-hover table-striped">
			                                </table>
			                                <div id="pager"></div>
			                            </div>
			                            <!-- /.table-responsive -->
			                        </div>
					            </div>
				            </div>
						</div>
			            <div class=" col-lg-6">
				            <div class="panel panel-default">
				                <div class="panel-heading">
									<p>컬럼 목록</p>
				                </div>
					            <div class="panel-body">
					            	<div class="col-lg-12 search-condition-column">
										<div class="form-group">
											<div class="col-lg-offset-10 col-lg-2">
												<label>&nbsp;</label>
												<p>
													<button type="button" class="btn btn-custom-save btn-circle btn-xl save-btn" title="저장">
														<i class="fa fa-floppy-o fa-2x" aria-hidden="true"></i>
													</button>
												</p>
					                        </div>
										</div>
			                        </div>
			                        <div class="col-lg-12">
			                            <div class="table-wrapper">
			                                <table id="dataTable2" class="table table-bordered table-hover table-striped dataTables">
			                                </table>
			                            </div>
			                            <!-- /.table-responsive -->
			                        </div>
					            </div>
				            </div>
			            </div>
					</div>
				</div>
                <!-- /.panel-heading -->
            </div>
            <!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/resources/js/app/personalInfo/personalInfoDictionary.js"></script>    
</body>
</html>