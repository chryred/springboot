<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<%-- <script src="<%=request.getContextPath() %>/js/jquery-ui.js" type="text/javascript"></script> --%>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/jquery-ui.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/theme.css" /> --%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/css/app/wiki/listWiki.css" rel="stylesheet">
<style type="text/css">
label {
	height: 34px !important;
	line-height: 34px !important;
}
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>온라인 업무가이드</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading btn-box">
                	<div>
                    	<i class="fa fa-bullhorn fa-fw"></i>&nbsp;업무가이드 작성/관리/공유를 제공합니다.
                    </div>
                    <div class="text-right">
	                    <a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_search">&nbsp;조회&nbsp;</button></a>
	                	<a href="<%=request.getContextPath() %>/wiki/edit.do"><button class="btn btn-primary btn-xs" type="button" id="btn_create">&nbsp;등록&nbsp;</button></a>
                	</div>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body text-right">
                	<div class="col-md-12 search-condition-box">
						<!-- 검색조건 line 1 -->
						<div>
							<div class="col-md-12">
								
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-3 control-label" for="workName">업무명</label>  
							  		<div class="col-md-6">
										<select id="workName" class="form-control">								
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-3 control-label" for="keywordTitle">제목</label>  
							  		<div class="col-md-6">
										<input class="form-control" type="text" id="keywordTitle" placeholder="제목 검색">
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-3 control-label" for="keywordContents">내용</label>  
							  		<div class="col-md-6">
										<input class="form-control" type="text" id="keywordContents" placeholder="내용 검색">
									</div>
								</div>
							</div>
						</div>
					</div>
                </div>
                <div class="panel-body table-wrapper">
                   	<table id="jqGridTable" class="table table-bordered table-hover table-striped">
					</table>
                    <div id="pager"></div>
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

<script src="<%=request.getContextPath() %>/js/app/wiki/listWiki.js"></script>    
