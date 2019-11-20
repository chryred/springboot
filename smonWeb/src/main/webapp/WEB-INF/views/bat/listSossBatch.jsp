<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <jsp:include page="../../common/navigation.jsp" flush="false"/> --%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/ztree/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/batch.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.qtip.min.css" />

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
            <h1 class="page-header"><small>운영정보 배치확인(DataStage)</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;운영정보 배치를 조회할 수 있습니다.
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body text-right" style="margin-bottom:0px;padding-bottom:0px;">
                	<button class="btn btn-default btn-xs" type="button" id="btn_search">&nbsp;조회&nbsp;</button>
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
                				<col style="width:10%">
                				<col style="width:6%">
                				<col style="width:10%">
                				<col style="width:6%">
                				<col style="width:10%">
                				<col style="width:6%">
                				<col style="*">
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
								    		<option value="SHUB_BAT">스마트허브/EDI</option>
								    	</select>
						    		</td>
						    		<th>
						        		배치잡명
						        	</th>
						        	<td>
						        		<input id="jobName" class="inputbox" style="width:100%">
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
						    		<th>
						        		하위포함
						        	</th>
						        	<td>
						        		<select id="subYn" style="">
								    		<option value="Y" selected>포함</option>
								    		<option value="N">미포함</option>
								    	</select>
						    		</td>
						    		<th>
						        		하위개수표시
						        	</th>
						        	<td>
						        		<select id="displaySub" style="">
								    		<option value="Y">표시</option>
								    		<option value="N" selected>미표시</option>
								    	</select>
						    		</td>
						    	</tr>
					    	</thead>
				    	</table>
                	</div>
                	<div class="col-lg-2" style="padding:0px;padding-right:5px;margin:0px;">
                		<div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree" ></ul>
						</div>
                	</div>
                	<!-- /.col-lg-2 -->
                	<div class="col-lg-10" style="padding:0px;margin:0px;border:0px;overflow:hidden;">
                		<div id="tableContents">
	                		<div id="jobDiv" class="jobList" style="float:left;padding:0px;margin:0px;width:300px;height:400px;z-index:2;">
	                			<table id="jobTable">
	                				<thead></thead>
	                				<tbody></tbody>
	                			</table>
	                		</div>
		                    <div id="chartDiv" class="chart" style="float:left;padding:0px;margin:0px;width:1222px;z-index:2;">
		                    	<table id="chartTable">
									<thead></thead>
									<tbody></tbody>
								</table>
		                    </div>
	                    </div>
                    </div>
                    <!-- /.col-lg-10 -->
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


<script src="<%=request.getContextPath() %>/resources/js/app/batch/listSossBatch.js"></script>    
