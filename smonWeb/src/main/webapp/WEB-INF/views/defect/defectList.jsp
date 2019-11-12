<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<script type="text/javascript"> 
	var contextPath = '<%=request.getContextPath()%>';
</script>
 
  <script>
  $( function() {
    $( "#period" ).datepicker();
  } );
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
<link href="<%=request.getContextPath() %>/resources/css/app/defect/defectList.css" rel="stylesheet">
<style type="text/css">
label {
	height: 34px !important;
	line-height: 34px !important;
}
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>장애 리스트</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading btn-box">
                	<div>
                    	<i class="fa fa-bullhorn fa-fw"></i>&nbsp;발생한 장애 결과를 관리합니다.
                    </div>
                    <div class="text-right">
	                    <a href="javascript:void(0)"><button class="btn btn-success btn-xs btn_search" type="button" id="btn_search">&nbsp;조회&nbsp;</button></a>
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
							  		<label class="col-md-3 control-label" for="effect">업무영향</label>  
							  		<div class="col-md-6">
										<select id="effect" class="form-control">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-3 control-label" for="defectGrade">장애등급</label>  
							  		<div class="col-md-6">
										<select id="defectGrade" class="form-control">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-5 control-label" for="effectRange">장애시스템범위</label>  
							  		<div class="col-md-6">
										<select id="effectRange" class="form-control">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-5 control-label" for="targetSystem">대상시스템</label>  
							  		<div class="col-md-6">
										<select id="targetSystem" class="form-control">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
							  		<label class="col-md-3 control-label" for="status">현재상태</label>  
							  		<div class="col-md-6">
										<select id="status" class="form-control">
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group search-condition-item-end">
									<label class="col-md-3 control-label" for="period">기간 설정</label>
						　　			<div class="col-md-6">
										<input type="text" id="period" name="period" align="center" style="font-size:20px; vertical-align:middle" placeholder="   기간을 설정해주세요.">										
									</div>
									<label class="col-md-8 control-label" for="period" style="color: blue">　　　　(장애발생시각 기준)</label>
								</div>
							</div>
						</div>
					</div>
                </div>
                <div id="1" class="panel-body table-wrapper">
                   	<table id="jqGridTable" class="table table-bordered table-hover table-striped"></table>
                    <div id="pager"></div>
                </div>
                <!-- /.panel-body -->
                <form id="defectForm" action="/defect/updateStatus.do" method="post">
	                <input type="hidden" id="rowId" name="rowId">
	                <div class="modal fade" id="myModal" name="myModal" role="dialog" >
						<div class="modal-dialog modal-sm">   
							    	<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
							     	<button type="button" class="close" data-dismiss="modal">&times;</button>
							        <h4 class="modal-title">현재 상태</h4>
								    <div>					 
								    	<div class="modal-body"> 
											처리완료		<input type="radio" id="status" name="status" value="처리완료"		style="width:17px;height:17px; vertical-align:middle;" checked="checked">　　
							  				미처리종료		<input type="radio" id="status" name="status" value="미처리종료"	style="width:17px;height:17px; vertical-align:middle;">　　
										</div>
								    	<div class="modal-footer">
								    		<button type="submit" class="btn btn-default">확인</button>	
									    	<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
									    </div>
									</div>  
								</div>
							</div>	
	            		</div>
	            <!-- /.panel -->
	        		</div>
	        	</form>
        <!-- /.col-lg-12 -->
    		</div>
    <!-- /.row -->
		</div>  
	</div> 
</div> 
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/resources/js/app/defect/defectList.js"></script>    
