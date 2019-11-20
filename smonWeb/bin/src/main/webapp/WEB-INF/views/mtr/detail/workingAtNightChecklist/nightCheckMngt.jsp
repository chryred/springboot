<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/clipboard.min.js"></script>
<script src="bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoSearch.css" rel="stylesheet">


<script type="text/javascript">
        function initDatePicker() {

            var dp1 = $('#dp1').datepicker({
                format : 'yyyy-mm-dd',
                todayBtn : 'linked'
            });

          var dp2 = $('#dp2').datepicker({
                format : 'yyyy-mm-dd',
                todayBtn : 'linked'
            });
       //    <!-- var dp2 = $('#dp2').datepicker({
       //*         format : 'yyyy-mm-dd',
      //          todayBtn : 'linked'
      //      });

   //      dp1.on('changeDate', function(ev) {
   //             if (ev.date.valueOf() > planEndDate.valueOf()) {
   //                 $('#alert1').show().find('strong').text('계획 : 시작일이 종료일보다 클 수 없습니다');
   //             } else {
   //                 $('#alert1').hide().find('strong').text("");
   //                 planStartDate = new Date(ev.date);
   //             }
   //             $('#dp1').datepicker('hide');
   //         });

   //         dp2.on('changeDate', function(ev) {
   //             if (ev.date.valueOf() < planStartDate.valueOf()) {
  //                  $('#alert1').show().find('strong').text('계획 : 종료일이 시작일보다 작을 수 없습니다');
   //             } else {
   //                 $('#alert1').hide().find('strong').text("");
   //                 planEndDate = new Date(ev.date);
   //             }
   //             $('#dp2').datepicker('hide');
   //         });

        }
        
        //화면 처음 로드 시 불러오는 함수 (onLoad)
        $(document).ready(function() {
            initDatePicker();

        });     
</script>
        
<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>야간 작업 내용 관리</p>
                </div>
                <div class="panel-body search-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
							<div class="form-group">
								<div class="col-lg-2">
									<label>시스템</label>
		                            <select id="partNm" class="form-control" title="파트명">
									</select>
								</div>
					 			<div class="col-lg-2">
		                        	<label>업무단위</label>
		                            <select id="sysNm" class="form-control" title="업무시스템">
									</select>
								</div>
							 	<!-- <div class="col-lg-2">
		                        	<label>담당자 여부</label>
		                        	<input type="text" id="infoType" class="form-control" title="개인정보 구분" />
								</div> -->
								<!-- <div class="col-lg-2">
		                        	<label>점검대상 여부</label>
		                        	<select id="exceptFlag" class="form-control" title="개인정보 대상 제외여부">
		                        		<option value="">전체</option>
		                        		<option value="N" selected>N</option>
		                        		<option value="Y">Y</option>
									</select>
								</div> -->
								<div class="col-lg-2">
									<label>&nbsp;</label>
									<div class="input-group">
										<button type="button" class="btn btn-primary btn-circle search-btn" title="조회">
											<i class="fa fa-search" aria-hidden="true"></i>
										</button>&nbsp;
								<!-- 		<button type="button" class="btn btn-info btn-circle save-btn" title="저장">
											<i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i>
										</button> -->

									</div>
		                        </div>
							</div>
                        </div>
					</div>							
				</div>
				
         <!--     <div class="panel-body search-wrapper">
                    <div class="row">
                        <div class="col-lg-12">
							<div class="form-group">
								
							 	<div class="col-lg-2">
		                        	<label>작업 날자<label>
		                        	<input type="text" id="dp1" class="form-control" title="개인정보 구분" />
								</div>		
 							    <div class="col-lg-2">
		                        	<label>작업시간   ([EX]  22:30~23:30  )</label>
		                        	<input type="text" id="infoType" class="form-control" title="개인정보 구분" />
								</div> 
								<div class="col-lg-2">
									<label>&nbsp;</label>
									<div class="input-group">
										<button type="button" class="btn btn-info btn-circle save-btn" title="저장">
											<i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i>
										</button>								
									</div>
		                        </div>																					
							</div>
                        </div>
					</div>							
				</div>				
				  --> 
			
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="table-wrapper">
                                <table id="dataTables" class="table table-bordered table-hover table-striped">
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
            <!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog modal-lg" style="z-index: 1100;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title1">
					<b></b>
				</h3>
				<h4 class="modal-title3"></h4>
			</div>
			<div class="modal-body" id="updateDiv">
				<div class="well">
					<label><b>작업일</b></label>
					<div class="input-group input-daterange">
						<input type="text" class="form-control" id="dp1"> <span
							class="input-group-addon">to</span> <input type="text"
							class="form-control" id="dp2">
					</div>
				</div>
		<!-- 	<div class="well">
					<label><b>실적</b></label>
					<div class="input-group input-daterange">
						<input type="text" class="form-control" id="dp3"> <span
							class="input-group-addon">to</span> <input type="text"
							class="form-control" id="dp4">
					</div>
				</div> -->
				<div class="well">
					<label><b>확인방법</b></label>
					<textarea class="form-control" rows="5" id="memo"></textarea>
				</div>
				<div class="well">
					<div class="checkbox">
						<label> <input type="checkbox" id="checkbox"
							value="T">점검대상여부
						</label>
					</div>
				</div>
			</div>
			<input type="hidden" class="form-control" id="COMN_GRP_CD" >
			<input type="hidden" class="form-control" id="COMN_CD" >
			<input type="hidden" class="form-control" id="Q_COMN_CD" >
			<input type="hidden" class="form-control" id="QUESTION_CD">
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" id="btn_save" class="btn btn-primary save-btn">저장</button>
			</div>
		</div>
	</div>
</div>
<textarea id="query" rows="0" cols="0" ></textarea>
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/js/app/workingAtNightCheckList/nightCheckMngt.js"></script>    

</body>
</html>