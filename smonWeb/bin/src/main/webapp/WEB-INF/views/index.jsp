<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="mtr/common/navigation.jsp" flush="false"/>

<script type="text/javascript">
$(function(){

    $(document).ready(function(){

       fn_loadSystemCombo('SYSTEM');

       fn_loadingUndoTablespace(false);
       fn_loadingTablespace('1');
       //fn_loadingCpuUsage(false);
       fn_loadingAlertLog(false);
       fn_loadingHitRatio();
       fn_loadingInvalidObjects();
       fn_loadingLongQuery();
    });

});



</script>

    <div id="page-wrapper">

        <div class="row">
            <div class="row form-inline">
	            <div class="col-lg-12">
	                <div class="panel panel-default">

	                    <div class="panel-body">
			                <div style='float: left;' class="form-group" id="comboSystem">
			                    <label>&nbsp;시스템&nbsp;&nbsp;&nbsp;&nbsp;</label>
			                </div>
		                </div>
	                </div>
	            </div>
            </div>
        </div>

        <div class="row">

		    <!-- <div class="col-lg-4">
		        <div class="panel panel-default">
		            <div class="panel-heading">
		                <i class="fa fa-bar-chart-o fa-fw"></i> CPU 사용률 체크&nbsp;(11초 후 갱신)
		            </div>
		            /.panel-heading
		            <div class="panel-body">
		                <div class="flot-chart">
		                    <div class="flot-chart-content" id="flot-line-chart-moving"></div>
		                </div>
		            </div>
		            /.panel-body
		        </div>
		        /.panel
		    </div> -->
		    <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-bar-chart-o fa-fw"></i>Long Query 현황(수행시간 10초 이상)
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- <div class="flot-chart">
                            <div class="flot-chart-content" id="flot-line-chart-multi2"></div>
                        </div> -->
                        <div class="wrapper"><canvas id="chart-0"></canvas></div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#longQueryDetailModal" id="longOpModal" href="">Open Modal</button -->
            <div class="modal fade" id="longQueryDetailModal" role="dialog">
            	<div class="modal-dialog modal-lg">
            		<!-- Modal content -->
            		<div class="modal-content" id="longQueryModalContent"></div>
            	</div>
            </div>
		    <div class="col-lg-3">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-bar-chart-o fa-fw"></i>Undo Tablespace 사용현황&nbsp;(4분 후 갱신)
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="flot-chart">
                            <div class="flot-chart-content" id="flot-line-chart-multi"></div>
                        </div>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <div class="col-lg-3">
              <div class="panel panel-default">
                 <div class="panel-heading">
                     <i class="fa fa-bar-chart-o fa-fw"></i> Invalid&nbsp;Objects&nbsp;현황
                 </div>
                 <!-- /.panel-heading -->
                 <div class="panel-body">
					 <canvas id="invalidObjectsChart" height="250"></canvas>
                 </div>
                 <!-- /.panel-body -->
             </div>
            </div>
            <div class="col-lg-12">
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    <i class="fa fa-bar-chart-o fa-fw"></i> <a href='tableSpace.do'>Tablespace 증가현황(단위:MB)</a>
	                    <div class="pull-right">
	                        <div class="btn-group">
	                            <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
	                                기간
	                                <span class="caret"></span>
	                            </button>
	                            <ul class="dropdown-menu pull-right" role="menu">
	                                <li><a href="#" onclick="fn_loadingTablespace('0')">1일</a>
	                                </li>
	                                <li><a href="#" onclick="fn_loadingTablespace('1')">2일</a>
	                                </li>
	                                <li><a href="#" onclick="fn_loadingTablespace('2')">3일</a>
	                                </li>
	                                <!-- <li class="divider"></li>
	                                <li><a href="#">Separated link</a>
	                                </li>-->
	                            </ul>
	                        </div>
	                    </div>
	                </div>
	                <!-- /.panel-heading -->
	                <div class="panel-body">
	                    <div class="row">
	                        <div class="col-lg-4">
	                            <div class="table-responsive">
	                                <table id="tsGrid" class="table table-bordered table-hover table-striped">

	                                </table>
	                            </div>
	                            <!-- /.table-responsive -->
	                        </div>
	                        <!-- /.col-lg-4 (nested) -->
	                        <div class="col-lg-8">
	                            <div id="morris-bar-chart"></div>
	                        </div>
	                        <!-- /.col-lg-8 (nested) -->
	                    </div>
	                    <!-- /.row -->
	                </div>
	                <!-- /.panel-body -->
	            </div>
	         </div>

		</div>
		<div id="hitRatioDiv" class="row">
        </div>


    </div>



    <!-- Bootstrap core JavaScript
    ================================================== -->

    <!-- Morris -->
    <script src="resources/bootstrap/bower_components/raphael/raphael-min.js"></script>
    <script src="resources/bootstrap/bower_components/morrisjs/morris.min.js"></script>

    <!-- Flot Charts JavaScript -->
    <script src="resources/bootstrap/bower_components/flot/excanvas.min.js"></script>
    <script src="resources/bootstrap/bower_components/flot/jquery.flot.js"></script>
    <script src="resources/bootstrap/bower_components/flot/jquery.flot.pie.js"></script>
    <script src="resources/bootstrap/bower_components/flot/jquery.flot.resize.js"></script>
    <script src="resources/bootstrap/bower_components/flot/jquery.flot.time.js"></script>
    <script src="resources/bootstrap/bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>

    <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script> -->
    <script src="resources/js/plugins/chartJs/chart.js"></script>
    <script src="resources/js/plugins/chartJs/util.js"></script>

    <!-- ChartJS-->
    <script src="resources/js/charts.js"></script>
</body>
</html>