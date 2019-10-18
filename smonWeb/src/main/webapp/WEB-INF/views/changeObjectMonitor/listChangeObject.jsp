<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

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
<script src="<%=request.getContextPath() %>/resources/js/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.ko.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/resources/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/jquery-ui.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/theme.css" /> --%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/resources/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/resources/css/app/changeObjectMonitor/listChangeObject.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
    <div class="col-lg-12">
	    <div class="panel-body search-wrapper">
			<form class="form-horizontal">
				<fieldset>
					<div class="row">
						<div class="col-md-12 text-right btn-box">
							<a href="javascript:void(0)" id="search-btn" class="btn btn-info search-btn">조회</a>
							<a href="javascript:void(0)" id="save-btn" class="btn btn-success save-btn">승인</a>
						</div>
						<div class="col-md-12 search-condition-box">
							<!-- 검색조건 line 1 -->
							<div>
							<!-- <div class="col-md-12 search-condition-line"> -->
								<!-- 
						  		<div class="col-md-4">
									<div class="form-group search-condition-item">
								  		<label class="col-md-4 control-label" for="eventDate">발생일자</label>  
								  		<div class="col-md-8">
								  			<input type="text" id="eventDate" class="form-control form_datetime" placeholder="발생일자" readonly>
										</div>
									</div>
								</div>
								 -->
								<div class="col-md-3">
									<div class="form-group search-condition-item-end">
								  		<label class="col-md-3 control-label" for="dbName">DB 명</label>  
								  		<div class="col-md-6">
											<select id="dbName" class="form-control">
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group search-condition-item-end">
								  		<label class="col-md-3 control-label" for="owner">소유자</label>  
								  		<div class="col-md-6">
											<select id="owner" class="form-control">
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group search-condition-item-end">
								  		<label class="col-md-3 control-label" for="objectType">타입</label>  
								  		<div class="col-md-6">
											<select id="objectType" class="form-control">
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group search-condition-item-end">
								  		<label class="col-md-3 control-label" for="objectName">Object 명</label>  
								  		<div class="col-md-6">
											<input type="text" id="objectName" class="form-control" placeholder="Object 명">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
    </div>
    <div class="col-md-12 text-right btn-box">
    </div>
        <div class="col-lg-12">
        	<!-- /.panel -->
			<div class="col-lg-8">
		    	<div class="panel panel-default">
		            <div class="panel-heading">
						<p>테이블 목록</p>
		            </div>
		            <table id="dataTable1" class="table table-bordered table-hover table-striped">
					</table>
					<!-- <div class="panel-body">
					    
						<div class="col-lg-12">
							<div class="table-wrapper-first">
								
							</div>
						</div>
					</div> -->
				</div>
			</div>
			<div class=" col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<p>컬럼 목록</p>
					</div>
					<table id="dataTable2" class="table table-bordered table-hover table-striped dataTables">
			        </table>
					<!-- <div class="panel-body">
					    
						<div class="col-lg-12">
						    
							<div class="table-wrapper_second">
			                	
							</div>
						</div>
					</div> -->
				</div>
			</div>
		<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/resources/js/app/changeObjectMonitor/listChangeObject.js"></script>    
</body>
</html>