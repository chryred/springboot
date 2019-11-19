<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <jsp:include page="../../common/navigation.jsp" flush="false"/> --%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	var date = new Date();
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/ztree/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/batch.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.qtip.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.css" />


<style type="text/css">
.form-control {
	height: 30px;
	font-size: 12px;
}

select {
	border: 1px solid #dcdcdc;
	font-size: 1em;
	height: 25px;
	padding: 2px;
	margin: 1px 0;
}

input.calendar {
	/* border-color: #ACACAC #D9D9D9 #D9D9D9 #ACACAC;*/
	border: 1px solid #dcdcdc;
	padding: 2px 0 0 5px;
	font-size: 1em;
	height: 21px;
	margin: 1px 0;
	width: 75px;
}

input.inputbox {
	/* border-color: #ACACAC #D9D9D9 #D9D9D9 #ACACAC;*/
	border: 1px solid #dcdcdc;
	height: 25px;	
}

.table_list, .table_view{table-layout:fixed; border-collapse:collapse; border-spacing:0; width:100%;border:1px solid #dcdcdc;}
.table_list thead tr th{background:#F5F5F5; color:darkblue;font-weight:bold;padding:5px;text-align:center;vertical-align:middle}
.table_list thead tr td{color:darkblue;font-weight:normal;padding:5px;text-align:left;vertical-align:middle}
.table_list tbody tr td{border-bottom:1px solid #dcdcdc; border-left:1px solid #dcdcdc; padding:5px 5px; text-align:center;}

.ui-datepicker{z-index: 99 !important};

</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>운영정보 배치확인(표)</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	 <div class="col-lg-3 col-md-6">
           <div class="panel panel-primary">
               <div class="panel-heading">
                   <div class="row">
                       <div class="col-xs-3">
                           <i class="fa fa-tasks fa-5x"></i>
                       </div>
                       <div class="col-xs-9 text-right">
                           <div class="huge"><span id="span_totCnt" style="font-style: italic">0</span></div>
                           <div>총 배치잡 개수</div>
                       </div>
                   </div>
               </div>
               <a href="#">
                   <div class="panel-footer">
                       <span class="pull-left"></span>
                       <span class="pull-right"></span>
                       <div class="clearfix"></div>
                   </div>
               </a>
           </div>
       	</div>
       	<div class="col-lg-3 col-md-6">
           <div class="panel panel-green">
               <div class="panel-heading">
                   <div class="row">
                       <div class="col-xs-3">
                           <i class="fa fa-child fa-5x"></i>
                       </div>
                       <div class="col-xs-9 text-right">
                           <div class="huge"><span id="span_okCnt" style="font-style: italic">0</span></div>
                           <div>정상 수행 개수</div>
                       </div>
                   </div>
               </div>
               <a href="#">
                   <div class="panel-footer">
                       <span class="pull-left"></span>
                       <span class="pull-right"></span>
                       <div class="clearfix"></div>
                   </div>
               </a>
           </div>
       </div>
       <div class="col-lg-3 col-md-6">
           <div class="panel panel-red">
               <div class="panel-heading">
                   <div class="row">
                       <div class="col-xs-3">
                           <i class="fa fa-wrench fa-5x"></i>  
                       </div>
                       <div class="col-xs-9 text-right">
                           <div class="huge"><span id="span_failCnt" style="font-style: italic">0</span></div>
                           <div>실패 개수</div>
                       </div>
                   </div>
               </div>
               <a href="#">
                   <div class="panel-footer">
                       <span class="pull-left"></span>
                       <span class="pull-right"></span>
                       <div class="clearfix"></div>
                   </div>
               </a>
           </div>
       </div>
       <div class="col-lg-3 col-md-6">
           <div class="panel panel-yellow">
               <div class="panel-heading">
                   <div class="row">
                       <div class="col-xs-3">
                           <i class="fa fa-bar-chart-o fa-5x"></i>
                       </div>
                       <div class="col-xs-9 text-right">
                           <div class="huge"><span id="span_rate" style="font-style: italic">0</span></div>
                           <div>성공률</div>
                       </div>
                   </div>
               </div>
               <a href="#">
                   <div class="panel-footer">
                       <span class="pull-left"></span>
                       <span class="pull-right"></span>
                       <div class="clearfix"></div>
                   </div>
               </a>
           </div>
       </div>
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;운영정보 배치를 조회할 수 있습니다.
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body text-right" style="margin-bottom:0px;padding-bottom:0px;">
                	<div id="toggle" style="position:absolute;">
                		<select id="pageScale" style="">
		        			<option value="10" selected>10</option>
				    		<option value="20">20</option>
				    		<option value="50">50</option>
				    		<option value="100">100</option>
				    		<option value="1000">1000</option>
				    	</select>
                		<input type="checkbox" checked data-toggle="toggle" data-size="mini" id="chk_toggle">
                	</div>
                	<div><button class="btn btn-default btn-xs" type="button" id="btn_search">&nbsp;조회&nbsp;</button></div>
                </div>
                <div class="panel-body">
                	<div class="col-lg-12 verticalCenter" style=";margin-top:10px;margin-bottom:20px;padding:0px 0px 0px 0px;">
                		<table class="table_list">
                			<colgroup>
                				<col style="width:6%">
                				<col style="width:10%">
                				<col style="width:6%">
                				<col style="width:10%">
                				<col style="width:6%">
                				<col style="*">
                				<col style="width:6%">
                				<col style="*">
                				<col style="width:6%">
                				<col style="width:10%">
                			</colgroup>
                			<thead>
	                			<tr>
	                				<th>
	                					기준일자
						        	</th>
						        	<td>
						        		<span id="span_prev" style="text-decoration:underline;cursor:pointer;" title="이전날짜">◀</span>&nbsp;&nbsp;<input class="calendar" placeholder="" type="text" name="baseDay" id="baseDay" style="" />&nbsp;&nbsp;<span id="span_next" style="text-decoration:underline;cursor:pointer" title="다음날짜">▶</span>
									</td>
									<th>
						        		배치구분
						        	</th>
						        	<td>
						        		<select id="projectName" style="">
								    		<option value="SOSS_BAT">운영정보</option>
								    		<option value="WORK_BAT">워크스마트</option>
								    	</select>
						    		</td>
						    		<th>
						        		배치잡명
						        	</th>
						        	<td>
						        		<input id="jobName" class="inputbox" style="width:100%">
						    		</td>
						    		<th>
						        		경로
						        	</th>
						        	<td>
						        		<input id="folderPath" class="inputbox" style="width:100%">
						    		</td>
						    		<th>
						        		성공여부
						        	</th>
						        	<td>
						        		<select id="successYn" style="">
						        			<option value="" selected>전체</option>
								    		<option value="Y">성공</option>
								    		<option value="N">실패</option>
								    	</select>
						    		</td>
						    	</tr>
					    	</thead>
				    	</table>
                	</div>
                    <!-- /.col-lg-10 -->
                </div>
                <!-- /.panel-body -->

                <!-- /.panel-heading -->
                <div class="panel-body" style="padding-top:0px;">
                    <table width="100%" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" id="dataTable">
                        <thead>
                            <tr role="row">
                                <th class="text-center" style="width:6%;font-weight:bold;" colidx="0">프로젝트명</th>
                                <th class="text-center" style="width:18%;font-weight:bold;" colidx="1">배치잡명</th>
                                <th class="text-center" style="width:18%;font-weight:bold;" colidx="2">경로</th>
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="3">시작일시</th>
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="4">종료일시</th>
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="5">경과시간</th>
                                <th class="text-center" style="width:7%;font-weight:bold;" colidx="6">주요상태</th>
                                <th class="text-center" style="width:7%;font-weight:bold;" colidx="7">잡상태</th>
                                <th class="text-center" style="width:7%;font-weight:bold;" colidx="8">PID</th>
                                <th class="text-center" style="width:7%;font-weight:bold;" colidx="9">CPU사용량</th>
                            </tr>
                        </thead>
                        <tbody id="table_tbody">
                        </tbody>
                    </table>
                    <!-- /.table-responsive -->
                    <div class="" id="div_count" style="margin-top:0px;text-align:left;padding:0px;"></div>
                    <div class="" id="div_page" style="margin-top:0px;text-align:center;padding:0px;"></div>
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


<script src="<%=request.getContextPath() %>/resources/js/app/batch/listSimpleSossBatch.js"></script>    
