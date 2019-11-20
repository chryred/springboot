<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
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
<!-- Flot Charts JavaScript -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/flot/excanvas.min.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/flot/jquery.flot.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/flot/jquery.flot.pie.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/flot/jquery.flot.resize.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/flot/jquery.flot.time.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>

<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script> -->
<script src="<%=request.getContextPath() %>/js/plugins/chartJs/chart.js"></script>
<script src="<%=request.getContextPath() %>/js/plugins/chartJs/util.js"></script>

<script src="<%=request.getContextPath() %>/js/app/jenkinsInfo/jenkinsFixedInfoSearch.js"></script>
<style type="text/css">
</style>

	<div id="page-wrapper">
        <div class="row">
            <!-- div class="row form-inline" -->
	            <div class="col-lg-12">
	                <div class="panel panel-default">
	                    <div class="panel-body">
			                <div style='display:inline-block; margin:5px 0px 5px 0px;' class="form-group" id="jenkinsRegisteredcomboSystem">
			                    <label style="display:inline">&nbsp;시스템:&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                </div>
		                </div>
	                </div>
	            </div>
            <!-- /div -->
        </div>

        <div class="row">
		    <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading" id="pmdPanelHeading">
                        <i class="fa fa-bar-chart-o fa-fw"></i>Jenkins [PMD]개선 현황
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="wrapper"><canvas id="lineChartPmd"></canvas></div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-bar-chart-o fa-fw"></i>Jenkins [FindBugs]개선 현황
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="wrapper"><canvas id="lineChartFindBugs"></canvas></div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
		</div>
    </div>
</html>
