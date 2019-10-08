<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>

<jsp:include page="../../common/navigation.jsp" flush="false"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/toggle/bootstrap-toggle.min.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>

<!-- css -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ztree/batch.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.qtip.min.css" />
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap/bower_components/toggle/bootstrap-toggle.min.css" />


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
	    display:inline-block;
	}
	div#select_box label {
	    position: absolute;
	    font-size: 14px;
	    color: #a97228;
	    top: 13px;
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

	a:link    { text-decoration:underline; color:#8600E4 }
	a:visited { text-decoration:underline; color:#800080 }
	a:hover   { text-decoration:underline; color:#FF0000 }
	a:active  { text-decoration:underline; color:#FF0000 }
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
            	<fmt:formatDate pattern="YYYY" var="thisYear" value="<%=new java.util.Date()%>" />
            	<small>Jenkins ${thisYear}년 룰셋 검출 현황
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
    <!--
    	 <div class="col-lg-3 col-md-6">
           <div class="panel panel-primary">
               <div class="panel-heading">
                   <div class="row">
                       <div class="col-xs-3">
                           <i class="fa fa-tasks fa-5x"></i>
                       </div>
                       <div class="col-xs-9 text-right">
                           <div class="huge"><span id="span_totCnt" style="font-style: italic">0</span></div>
                           <div>총 잔여건수</div>
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
                           <div>개선 누적 건수</div>
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
                           <div>신규 누적 건수</div>
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
                           <div>처리현황</div>
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
       -->
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bullhorn fa-fw"></i>&nbsp;* 검출 등급 : High(Red), Normal(Blue), Low(Black)
                </div>
                <!-- /.panel-heading -->

                <div class="panel-body">
                	<!-- div class="col-lg-12 verticalCenter" style=";margin:5px 0px 10px 0px;;padding:0px 0px 0px 0px;">
                		<table class="table_list">
                			<colgroup>
                				<col style="width:5%">
                				<col style="width:10%">
                			</colgroup>
                			<thead>
	                			<tr>
						    		<th>
						        		검출유형
						        	</th>
						        	<td>
										<input type="radio" name="scrtyTypeCd" id="scrtyTypeCd" value="P" checked>&nbsp;PMD
										<input type="radio" name="scrtyTypeCd" id="scrtyTypeCd" value="F" >&nbsp;FindBugs
						    		</td>
						    	</tr>
					    	</thead>
				    	</table>
                	</div-->
                    <div class="row">
	                        <div class="col-lg-12">
	                            <div class="table-wrapper">
	                                <table id="dataTables" class="table table-bordered table-hover table-striped">
	                                </table>
	                            </div>
	                            <!-- /.table-responsive -->
	                        </div>
	                        <!-- /.col-lg-4 (nested) -->
                       </div>
                   	<!-- /.row -->
               	 	<!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->

</div>
<!-- /#page-wrapper -->


<script src="<%=request.getContextPath() %>/js/app/jenkinsInfo/jenkinsRuleSetInfoSearch.js"></script>
