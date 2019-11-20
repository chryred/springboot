<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
	var g_contextWebRoot = "${pageContext.request.contextPath}/";
	
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/app/batch/listUserIp.js"></script>   
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/dataTables.buttons.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/buttons.html5.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jszip.min.js"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
	.ipCon td{
		height:70px;
	}
	.ipCon>tbody>tr>td:hover, .ipCon>tbody>tr>td:hover{
        background-color: #FAF4C0 !important; // Or any colour you like
    }
    .ipCon>tbody>tr>td, .ipCon>tbody>tr>td{
        padding: 8px;
        line-height: 1.42857143;
        vertical-align: middle;
    }
    .ipCell {
    	text-align: center;
    	vertical-align: middle;
    }
    .explain {
    	margin-top : 5px;
    }
    .modal {
        text-align: center;
	}
	@media screen and (min-width: 768px) { 
	        .modal:before {
	                display: inline-block;
	                vertical-align: middle;
	                content: " ";
	                height: 100%;
	        }
	}
	.modal-dialog {
	        display: inline-block;
	        text-align: left;
	        vertical-align: middle;
	}
	.submitBtn {
	    font-size: 12px;
	}
	.modal-header {
	    color:#fff;
	    padding:9px 15px;
	    border-bottom:1px solid #eee;
	    background-color: #428bca;
	    -webkit-border-top-left-radius: 5px;
	    -webkit-border-top-right-radius: 5px;
	    -moz-border-radius-topleft: 5px;
	    -moz-border-radius-topright: 5px;
	     border-top-left-radius: 5px;
	     border-top-right-radius: 5px;
	}
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>백화점팀 IP 현황</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bullhorn fa-fw"></i>&nbsp;백화점팀 IP를 관리합니다.
                </div>
                <ul class="nav nav-tabs" id="myTab" role="tablist">
				  <li class="active">
				    <a id="ip-status-tab" data-toggle="tab" href="#ip-status">IP 사용현황</a>
				  </li>
				  <li>
				    <a id="ip-list-tab" data-toggle="tab" href="#ip-list">IP 사용 리스트</a>
				  </li>
				</ul>
                <!-- /.panel-heading -->
               
                <div class="panel-body tab-content" id="myTabContent">
                	<div class="tab-pane fade in active" id="ip-status">
                		<div class="col-lg-8">
	                		<!-- 최병문 - 임시 - IP 발급 설명 -->
	                		<ol id="slist">
	                			<li>
	                				<a href="#"><b>■ NETWORK 설정</b></a>
	                				<p class="explain">
	                					- 서브넷 마스크 : 255.255.255.0<br>
								        - 기본 게이트웨이 : 10.174.179.1<br>
								        - 기본 DNS 서버 : 174.100.20.84<br>
								        - 보조 DNS 서버 : 10.253.19.18<br>
	                				</p>
	                			</li>
	                			<li>
	                				<a href="#"><b>■ IP 발급방법</b></a>
	                				<p class="explain">
	                					1) 회색으로 표시된 IP 더블클릭<br>
	                					2) 팝업에 상세내용 작성<br>
	                					3) 발급<br>
	                					※ 신청 전 ping 확인 필수<br>
	                					- 윈도우 + R -> Command 창 -> ping -t ip(사용할 아이피)를 통해 체크<br>
	                					- 연결할 수 없는 경우 신청가능                					
	                				</p>
	                			</li>
	                			<li>
	                				<a href="#"><b>■ IP 삭제방법</b></a>
	                				<p class="explain">
	                					1) 본인 IP 더블클릭<br>
	                					2) 상세정보 內 삭제버튼 클릭<br>
	                					3) 비밀번호 입력 - 삭제완료<br>
	                				</p>
	                			</li>
	                		</ol>   
                		</div>
                		<div class="col-lg-4 text-right">
                			<a href="#"><button class="btn btn-primary btn-xs btn_newIp" type="button" id="btnNewIp">&nbsp;신규발급&nbsp;</button></a>
                			<a href="#"><button class="btn btn-default btn-xs" type="button" id="btn_search">&nbsp;조회&nbsp;</button></a>
                		</div>
                		<table width="100%" class="table table-bordered ipCon">
	                		<colgroup>
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
								<col class="col-md-1">
							</colgroup>
	                		<tbody id="ipList">
	                		</tbody>
	                	</table>
	                </div>
	                <div class="tab-pane fade" id="ip-list">
	                	<div class="text-right" style="margin-bottom:5px;">
	                		<a href="#"><button class="btn btn-primary btn-xs btn_newIp" type="button" id="btnTableNewIp">&nbsp;신규발급&nbsp;</button></a>
	                		<a href="#"><button class="btn btn-default btn-xs" type="button" id="btnTableSrch">&nbsp;조회&nbsp;</button></a>	                		
	                	</div>
	                	<table width="100%" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" id="dataTable">
	                        <thead>
	                            <tr role="row">
	                                <th class="text-center" style="width:5%; font-weight:bold;" colidx="0">IP</th>
	                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="1">사용자</th>
	                                <th class="text-center" style="width:50%;font-weight:bold;" colidx="2">사용목적</th>
	                                <th class="text-center" style="width:5%; font-weight:bold;" colidx="3">상태</th>
	                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="4">등록일</th>
	                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="5">수정일</th>
	                            </tr>
	                        </thead>
	                        <tbody id="table_tbody">
	                        </tbody>
	                    </table>
	                </div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <!-- Modal -->
	<div class="modal fade" id="userIpStatus" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title text-center" id="exampleModalLongTitle"><b>IP 발급/수정</b></h5>	        
	      </div>
	      <div class="modal-body">
	        <form id="form_userIp" name="form_userIp">
				<fieldset>
					<div class="form-group">
			            <label for="userIp" class="control-label">IP</label>
			            <div id="userIpForm">
                            <input id="userIp" type="text" class="form-control" name="userIp" autofocus readonly>
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="userName" class="control-label">사용자</label>
			            <div>
                            <input id="userName" type="text" class="form-control" name="userName" placeholder="USER_NAME" maxlength="20">
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="reason" class="control-label">사용목적</label>
			            <div>
                            <input id="reason" type="text" class="form-control" name="reason"  placeholder="PURPOSE" maxlength="50">
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="passwd" class="control-label">비밀번호</label>
			            <div>
                            <input id="passwd" type="password" class="form-control" name="passwd"  placeholder="PASSWORD" maxlength="20">
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="entDate" class="control-label">등록일</label>
			            <div>
                            <input id="entDate" type="text" class="form-control" name="entDate" readonly>
                        </div>
			        </div>
				</fieldset>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-block submitBtn" id="reqNewIp">발급요청</button>
	        <button type="button" class="btn btn-info btn-block submitBtn" id="updateIp">변경</button>
	        <button type="button" class="btn btn-danger btn-block submitBtn" id="deleteIp">삭제</button>
	        <button type="button" class="btn btn-secondary btn-block submitBtn" data-dismiss="modal">닫기</button>		    
	      </div>
	    </div>
	  </div>
	</div>
</div>    
<!-- /#page-wrapper -->
