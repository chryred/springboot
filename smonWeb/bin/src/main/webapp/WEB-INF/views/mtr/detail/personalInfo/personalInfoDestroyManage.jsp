<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<%-- <script src="<%=request.getContextPath() %>/js/jquery-ui.js" type="text/javascript"></script> --%>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jquery-ui.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/jquery-ui.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/theme.css" /> --%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoDictionary.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/jquery-ui-1.7.1.custom.css" rel="stylesheet">

<style>
  .custom-combobox {
    position: relative;
    display: inline-block;
  }
  .custom-combobox-toggle {
    position: absolute;
    top: 0;
    bottom: 0;
    margin-left: -1px;
    padding: 0;
  }
  .custom-combobox-input {
    margin: 0;
    padding: 5px 10px;
  }
  </style>

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
                <div class="panel-body search-wrapper">
                    <div class="row">
                    	<div class="col-lg-6">
	                    	<div class="panel panel-default">
				                <div class="panel-heading">
									<p>테이블 목록</p>
				                </div>
					            <div class="panel-body">
					            	<div class="search-condition-table col-lg-12">
										<div class="form-group">
											<div class="col-lg-2">
												<label>DB 명</label>
					                            <select id="dbName" class="form-control">
												</select>
											</div>
											<div class="col-lg-2">
					                        	<label>소유자</label>
					                            <select id="owner" class="form-control">
												</select>
											</div>
											<div class="col-lg-4">
					                        	<label>테이블명</label>
					                            	<input type="text" id="tableName" class="form-control"/>
											</div>
											<div class="col-lg-offset-3 col-lg-1">
												<label>&nbsp;</label>
												<p>
													<button class="btn btn-default btn-xs search-btn" type="button" >&nbsp;조회&nbsp;</button>
												</p>
					                        </div>
										</div>
			                        </div>
			                        <div class="col-lg-12">
			                            <div class="table-wrapper">
			                                <table id="dataTable1" class="table table-bordered table-hover table-striped">
			                                </table>
			                                <div id="pager"></div>
			                            </div>
			                            <!-- /.table-responsive -->
			                        </div>
					            </div>
				            </div>
						</div>
			            <div class=" col-lg-6">
				            <div class="panel panel-default">
				                <div class="panel-heading">
									<p>개인정보 파기 SQL</p>
				                </div>
					            <div class="panel-body">
					            	<div class="col-lg-12 search-condition-column">
										<div class="form-group">
											<div class="col-lg-offset-10 col-lg-3">
												<label>&nbsp;</label>
												<p>
													<button class="btn btn-default btn-xs new-btn" type="button" >&nbsp;신규&nbsp;</button>
													<button class="btn btn-default btn-xs save-btn" type="button" >&nbsp;저장&nbsp;</button>
													<button class="btn btn-default btn-xs delete-btn" type="button" >&nbsp;삭제&nbsp;</button>
													<!-- <button class="btn btn-default btn-xs cancel-btn" type="button" >&nbsp;취소&nbsp;</button> -->
													<!-- <button type="button" class="btn btn-custom-save btn-circle btn-xl save-btn" title="저장">
														<i class="fa fa-floppy-o fa-2x" aria-hidden="true"></i>
													</button> -->
												</p>
					                        </div>
										</div>
			                        </div>
			                        <div>
			                        	<label>*DB명</label>
			                        	<!-- <input type="text" id="inputDBName" class="form-control" title="DB명 입력" /> -->
			                        	<div class="ui-widget">
										  <select id="customDbNameCombo">
										  </select>
										</div>
			                        </div>
			                        <div>
			                        	<label>*소유자</label>
			                        	<input type="text" id="inputOwner" class="form-control" title="OWNER 입력" />
			                        	<!-- <div class="ui-widget">
										  <select id="customOwnerCombo">
										  </select>
										</div> -->
			                        </div>
			                        <div>
			                        	<label>*테이블명</label>
			                        	<input type="text" id="inputTableName" class="form-control" title="TABLE명 입력" />
			                        </div>
			                        <div>
			                        	<label>*SQL</label>
			                        	<textarea id="inputSQL" class="form-control" style="height:300px;"></textarea>
			                        	<!-- <input type="text" id="inputSQL" class="form-control" title="SQL 입력" style="height:300px;" /> -->
			                        </div>
			                        <div>
			                        	<label>파기기준(2개월 파기 기준이라면)</label>
			                        	    <font>예)2</font><input type="text" id="inputStndrdNmbr"class="form-control" />
			                        		<font>예)개월</font><select id="inputStndrdCycle" class="form-control" >
				                        	    <option value="n/a">해당없음</option>
				                        	    <option value="day">일</option>
				                        	    <option value="month">개월</option>
				                        	</select>
			                        	<!-- <input type="text" id="inputINFO" class="form-control" title="INFO 입력" /> -->
			                        </div>
			                        <div>
			                            <label>비고</label>
			                        	<textarea id="inputINFO" class="form-control" style="height:80px;"></textarea>
			                        	<!-- <input type="text" id="inputINFO" class="form-control" title="INFO 입력" /> -->
			                        </div>
			                        <!-- <div class="col-lg-12">
			                            <div class="table-wrapper">
			                                <table id="dataTable2" class="table table-bordered table-hover table-striped dataTables">
			                                </table>
			                            </div>
			                            /.table-responsive
			                        </div> -->
					            </div>
				            </div>
			            </div>
					</div>
				</div>
                <!-- /.panel-heading -->
		</div>
		<!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/js/app/personalInfo/personalInfoDestroyManage.js"></script>    
</body>
</html>