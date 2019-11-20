<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>

<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
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

<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/app/adm/logActionMap.css" />


<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>관리자 - URL 관리</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#setter">등록</a></li>
		<li><a data-toggle="tab" href="#getter">현황조회</a></li>
	</ul>
    <div class="tab-content">
    
	    <div class="row tab-pane fade in active" id="setter">
	        <div class="col-lg-12">
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    <i class="fa fa-bullhorn fa-fw"></i>&nbsp; URL 관리화면입니다.
	                </div>
	                <div class="panel-body">
		                <div class="col-sm-3" style="padding:0px;padding-right:5px;margin:0px;height:400px;">
	                		<div class="zTreeDemoBackground left">
								<ul id="treeDemo" class="ztree" ></ul>
							</div>
		               	</div>
		               	<div class="col-sm-9" style="padding:0px;margin:0px;border:0px;overflow:hidden;height:400px;">
	                		<div class="content-container">
	                			<div class="content-wrapper" id="title-url"></div>
	               			    <div class="content-wrapper text-right">
					            	<button class="btn btn-default btn-xs" type="button" id="save-detail">&nbsp;저장&nbsp;</button>
					            </div>
		                		<div id="content-wrapper detailDiv">
			                		<table class="table_list">
						        	<colgroup>
						            	<col width="15%" />
						                <col width="" />
						                <col width="15%" />
						                <col width="" /> 
						            </colgroup>
						    		<thead>
							    		<tr>
								    		<th class="control-label">동작명</th>
							    			<td>
							    				<span id="viewMenuNo"></span>
							    				<input type="text" class="form-control" id="action-name" name="menuId" alt="메뉴번호"> 
							    			</td>
								      		<th class="control-label">동작구분</th>
								      		<td>
								      			<select class="form-control selectpicker" id="action-gubun">
							             		<option value="R">조회</option>
												<option value="C">생성</option>
												<option value="U">수정</option>
												<option value="D">삭제</option>
							         		</select>
								      		</td>
								     	</tr>
							      		<th class="control-label">개인정보 포함 유무</th>
							      		<td colspan="3"> 
							      			<select class="form-control selectpicker" id="personal-yn">
													<option value="Y">포함</option>
													<option value="N">미포함</option>
												</select>
							      		</td> 
							      
							    	</thead>
								</table>	
	   	                		</div>
		                    </div>
	                    </div>
	               	</div>
	            </div>
	        </div>
	    </div>
	    <!-- /.row -->
	    
	    <div id="getter" class="tab-pane fade">
			<div class="btn-wrapper">
            	<button class="btn btn-default btn-xs" id="search" type="button">&nbsp;조회&nbsp;</button>
            </div>			
	   		<table width="100%" class="table table-striped table-bordered table-hover searchTable no-footer dtr-inline" id="searchTable">
            	<thead>
                	<tr role="row">
	                    <th class="text-center" colidx="0">URL명</th>
	                    <th class="text-center" colidx="1">
	                    	<input id="url-name" class="inputbox">
	                    </th>
	                </tr>
            	</thead>
            	<tbody id="table_tbody">
            	</tbody>
            </table>
	    	<table width="100%" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" id="dataTable">
            	<thead>
                	<tr role="row">
	                    <th class="text-center sorting" colidx="0">URL명</th>
	                    <th class="text-center sorting" colidx="1">개인정보포함 유무</th>
	                    <th class="text-center sorting" colidx="2">평균 수행 시간(ms)</th>
	                    <th class="text-center sorting" colidx="3">최근 수행 시각</th>
	                    <th class="text-center sorting" colidx="4">요청 횟수</th>
	                </tr>
            	</thead>
            	<tbody id="table_tbody">
            	</tbody>
        	</table>
        	<div class="panel-body">
        	<div class="pagination" id="div_count"></div>
        	<div class="pagination center" id="div_page"></div>
        	</div>
	    </div>
    </div>
</div>    
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/app/adm/logActionMap.js"></script>  
<script>
	$(document).ready(function() {
		init(${URLs});	
	});
</script>  
