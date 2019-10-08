<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.ko.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/clipboard.min.js"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/css/app/personalInfo/personalInfoCurrentState.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>개인정보 암호화 현황 관리</p>
                </div>
                <div class="panel-body search-wrapper">
					<form class="form-horizontal">
						<fieldset>
							<div class="row">
								<div class="col-md-12 text-right btn-box">
									<a href="javascript:void(0)" id="init-btn" class="btn btn-default ">검색조건초기화</a>
									<a href="javascript:void(0)" id="new-btn" class="btn btn-primary">신규</a>
									<a href="javascript:void(0)" id="save-btn" class="btn btn-success">저장</a>
									<a href="javascript:void(0)" id="search-btn" class="btn btn-info">조회</a>
								</div>
								<div class="col-md-12 search-condition-box">
									<!-- 검색조건 line 1 -->
									<div class="col-md-12 search-condition-line">
								  		<div class="col-md-2">
											<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label" for="team">운영팀</label>  
										  		<div class="col-md-8">
													<select id="team" class="form-control">
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
											<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label two-line-label" for="system">업무<br>시스템</label>  
										  		<div class="col-md-8">
												    <select id="system" class="form-control">
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label" for="operationFlag">운영구분</label>  
										  		<div class="col-md-8">
												    <select id="operationFlag" class="form-control">
												    	<option value="">전체</option>
												    	<option value="1">운영</option>
												      	<option value="2">개발</option>
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label" for="saveType">보관유형</label>  
										  		<div class="col-md-8">
												    <select id="saveType" class="form-control">
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item-end">
										  		<label class="col-md-4 control-label two-line-label" for=""objectType"">OBJECT<br>구분</label>  
										  		<div class="col-md-8">
												    <select id="objectType" class="form-control">
												    </select>
												</div>
											</div>
										</div>
									</div><!-- 검색조건 line 1 -->
									<!-- 검색조건 line 2 -->
									<div class="col-md-12 search-condition-line">
								  		<div class="col-md-2">
											<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label two-line-label" for="personalInfoType">개인정보<br>구분</label>  
										  		<div class="col-md-8">
												    <select id="personalInfoType" class="form-control">
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
											<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label" for="dataType">DATATYPE</label>  
										  		<div class="col-md-8">
												    <select id="dataType" class="form-control">
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label" for="ifType">I/F구분</label>  
										  		<div class="col-md-8">
												    <select id="ifType" class="form-control">
												    	<option value="">전체</option>
												    	<option value="1">내부</option>
												    	<option value="2">외부</option>
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item">
										  		<label class="col-md-4 control-label" for="actionType">조치방향</label>  
										  		<div class="col-md-8">
												    <select id="actionType" class="form-control">
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item-end">
										  		<label class="col-md-4 control-label" for="infoType">정보종류</label>  
										  		<div class="col-md-8">
												    <select id="infoType" class="form-control">
												    	<option value="">전체</option>
												    	<option value="1">고객</option>
												    	<option value="2">협력사</option>
												    	<option value="3">임직원</option>
												    </select>
												</div>
											</div>
										</div>
									</div><!-- 검색조건 line 2 -->
									<!-- 검색조건 line 3 -->
									<div class="col-md-12 search-condition-line">
										<div class="col-md-2">
									  		<div class="form-group search-condition-item-end">
										  		<label class="col-md-4 control-label" for="actionFlag">조치여부</label>  
										  		<div class="col-md-8">
												    <select id="actionFlag" class="form-control">
												    	<option value="">전체</option>
												    	<option value="1">Y</option>
												      	<option value="0">N</option>
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-4">
											<div class="form-group search-condition-item">
										  		<label class="col-md-2 control-label two-line-label" for="">조치<br>예정일</label>  
										  		<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="actionStartDate" class="form-control form_datetime" placeholder="시작일" readonly>
												  	</div>
												</div>
												<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="actionEndDate" class="form-control form_datetime" placeholder="종료일" readonly>
												  	</div>
												</div>
											</div>
										</div>
										<div class="col-md-2">
									  		<div class="form-group search-condition-item-end">
										  		<label class="col-md-4 control-label two-line-label" for="leaderConfirmFlag">리더확인<br>여부</label>  
										  		<div class="col-md-8">
												    <select id="" class="form-control">
												    	<option value="leaderConfirmFlag">전체</option>
												    	<option value="1">Y</option>
												      	<option value="0">N</option>
												    </select>
												</div>
											</div>
										</div>
										<div class="col-md-4">
											<div class="form-group search-condition-item-end">
										  		<label class="col-md-2 control-label two-line-label" for="">리더<br>확인일</label>  
										  		<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="leaderStartDate" class="form-control form_datetime" placeholder="시작일" readonly>
												  	</div>
												</div>
												<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="leaderEndDate" class="form-control form_datetime" placeholder="종료일" readonly>
												  	</div>
												</div>
											</div>
										</div>
									</div><!-- 검색조건 line 3 -->
									<!-- 검색조건 line 4 -->
									<div class="col-md-12 search-condition-line">
										<div class="col-md-4">
											<div class="form-group search-condition-item">
										  		<label class="col-md-2 control-label" for="">등록일</label>  
										  		<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="entStartDate" class="form-control form_datetime" placeholder="시작일" readonly>
												  	</div>
												</div>
												<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="entEndDate" class="form-control form_datetime" placeholder="종료일" readonly>
												  	</div>
												</div>
											</div>
										</div>
										<div class="col-md-4">
											<div class="form-group search-condition-item">
										  		<label class="col-md-2 control-label" for="">변경일</label>  
										  		<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="modStartDate" class="form-control form_datetime" placeholder="시작일" readonly>
												  	</div>
												</div>
												<div class="col-md-5">
												  	<div class="input-group">
												    	<input type="text" id="modEndDate" class="form-control form_datetime" placeholder="종료일" readonly>
												  	</div>
												</div>
											</div>
										</div>
										<div class="col-md-4">
									  		<div class="form-group search-condition-item-end">
										  		<div class="col-md-4">
												    <select id="searchType" class="form-control">
												    	<option value="">검색분류선택</option>
												      	<option value="1">서버IP</option>
												      	<option value="2">시스템ID</option>
												      	<option value="3">USER</option>
												      	<option value="4">OBJECT명</option>
												      	<option value="5">OBJECT설명</option>
												      	<option value="6">비고</option>
												    </select>
												</div>  
										  		<div class="col-md-8">
												    <div class="input-group">
												    	<input type="text" id="searchText" class="form-control" placeholder="검색키워드입력">
												  	</div>
												</div>
											</div>
										</div>
									</div><!-- 검색조건 line 4 -->
								</div>
							</div>
						</fieldset>
					</form>
				</div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="table-wrapper">
                                <table id="dataTables" class="table table-bordered table-hover table-striped">
                                </table>
                                <div id="pager"></div>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
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

<script src="<%=request.getContextPath() %>/js/app/personalInfo/personalInfoCurrentState.js"></script>    
</body>
</html>