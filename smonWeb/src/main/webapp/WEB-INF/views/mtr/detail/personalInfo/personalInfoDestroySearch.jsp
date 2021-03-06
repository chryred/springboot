<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<head>
<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>
</head>

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
					<p>개인정보 파기 현황 조회</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                    	<div class="col-lg-12">
	                    	<div class="panel panel-default">
				                
					            <div class="panel-body">
					            	<div class="search-condition-table col-lg-12">
										<div class="form-group">
											<!--<div class="col-lg-2">
					                        	<label>검출일자</label>
					                            <select id="ifYmd" class="form-control">
					                           		<option value="" selected>전체</option>
												</select>
											</div>-->
											<div class="col-lg-2">
												<label>DB명</label>
					                            <select id="dbName" class="form-control">
					                            	<option value="" selected>전체</option>
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>소유자</label>
					                            <select id="owner" class="form-control">
					                            	<option value="" selected>전체</option>
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>테이블이름</label>
					                            <select id="tableName" class="form-control">
					                            	<option value="" selected>전체</option>
												</select>
											</div>
											<!--<div class="col-lg-2">
					                        	<label>SQL문</label>
					                            <input id="sql" type="text" class="form-control" />				                            	
											</div>
											<div class="col-lg-2">
					                        	<label>설명</label>
					                            <input id="info" type="text" class="form-control" />						                            	
											</div>
											<div class="col-lg-1">
					                        	<label>COUNT</label>
					                            <input id="cnt" type="number" class="form-control" />	
											</div>-->
											<div class="col-lg-1">
												<label>&nbsp;</label>
												<p>
													<button type="button" class="btn btn-primary btn-circle search-btn" title="조회">
														<i class="fa fa-search" aria-hidden="true"></i>
													</button>
												</p>
					                        </div>										
										</div>
			                        </div>
			                        <div class="col-lg-12">
			                            <div class="table-wrapper">
			                                <table id="dataTable3" class="table table-bordered table-hover table-striped">
			                                </table>
			                                <div id="pager"></div>
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

<script src="<%=request.getContextPath() %>/resources/js/app/personalInfo/personalInfoDestroySearch.js"></script>    
</body>
</html>