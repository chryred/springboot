<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../common/navigation.jsp" flush="false"/>


<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	var date = new Date();

</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>

<!-- css -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/batch.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.qtip.min.css" />
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/resources/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.css" />

<style type="text/css">
	.form-control {
		height: 30px;
		font-size: 12px;
	}

	select {
		border: 1px solid #dcdcdc;
		font-size: 0.6em;
		height: 25px;
		padding: 2px;
		margin: 1px 0;
	}

	div#select_box {
	    position: relative;
	    width: 100px;
	    height: 40px;
	    /*
	    background: url(select_arrow.png) 100px center no-repeat;
	    border: 1px solid #E9DDDD;
	    */
	    display:inline-block;
	}
	div#select_box label {
	    position: absolute;
	    font-size: 14px;
	    color: #a97228;
	    top: 10px;
	    left: 12px;
	    letter-spacing: 1px;
	    text-align: center;
	}
	div#select_box select#scrtyTypeCd {
	    width: 100%;
	    height: 40px;
	    min-height: 40px;
	    line-height: 40px;
	    padding: 0 10px;
	    opacity: 0;
	    filter: alpha(opacity=0); /* IE 8 */
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
	.ui-th-column-header{ text-align: center !important;}

/*
	a:link    { text-decoration:underline; color:#8600E4 }
	a:visited { text-decoration:underline; color:#800080 }
	a:hover   { text-decoration:underline; color:#FF0000 }
	a:active  { text-decoration:underline; color:#FF0000 }
	*/
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
            	
            	<small>Jenkins <fmt:formatDate value="<%=new java.util.Date()%>" pattern="YYYY"   />년 현황
            		<div id="select_box">
    					<label for="scrtyTypeCd">[PMD]</label>
            			<select id="scrtyTypeCd" class="select_box">
	        				<option value="P" selected>PMD</option>
			    			<option value="F">FindBugs</option>
			    		</select>
			    	</div>
            	</small>
			</h1>
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
                           <div>총 건수</div>
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
                           <div class="huge"><span id="span_fixedCnt" style="font-style: italic">0</span></div>
                           <div>개선 건수</div>
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
                           <div class="huge"><span id="span_newCnt" style="font-style: italic">0</span></div>
                           <div>미처리 건수</div>
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
                           <div class="huge"><span id="span_trtmRate" style="font-style: italic">0</span></div>
                           <div>처리율</div>
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
                    <i class="fa fa-bullhorn fa-fw"></i>&nbsp;* 표시된 컬럼은 최종빌드를 기준으로 보여지는 수치입니다.
                </div>
                <!-- /.panel-heading -->

                <div class="panel-body">
                	<div class="col-lg-12 verticalCenter" style=";margin:5px 0px 10px 0px;;padding:0px 0px 0px 0px;">
                		<table class="table_list">
                			<colgroup>
                				<col style="width:5%">
                				<col style="width:10%">
                				<col style="width:10%">
                			</colgroup>
                			<thead>
	                			<tr>
						    		<th>
						        		Jenkins 작업명
						        	</th>
						        	<td>
						        		<input id="jobName" class="inputbox" style="width:100%;border: 0px">
						    		</td>
						    		<td style="text-align:right">
						    			<button class="btn btn-primary btn-xs" type="button" id="btn_search">&nbsp;조회&nbsp;</button>
						    		</td>
						    	</tr>
					    	</thead>
				    	</table>
                	</div>
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


<script src="<%=request.getContextPath() %>/resources/js/app/jenkinsInfo/jenkinsInfoSearch.js"></script>
