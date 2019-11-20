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
            <h1 class="page-header"><small>관리자 - 권한그룹 관리</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	 
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;권한그룹관리 화면 입니다.
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body text-right" style="margin-bottom:0px;padding-bottom:0px;">
                	<div id="toggle" style="position:absolute;disabled:false;">
                		<select id="pageScale" style="">
		        			<option value="10" selected>10</option>
				    		<option value="20">20</option>
				    		<option value="50">50</option>
				    	</select>
                	 
                	</div>
                	<div>     
                		<button class="btn btn-default btn-xs" type="button" id="btn_search">&nbsp;조회&nbsp;</button>            	   
                	    <button class="btn btn-default btn-xs" type="button" id="btn_NEW">&nbsp;추가&nbsp;</button>
                	     <!--  button class="btn btn-default btn-xs" type="button" id="btn_del">&nbsp;삭제&nbsp;</button-->
                	</div>
                </div>
          		 <div class="panel-body">
                	<div class="col-lg-12 verticalCenter" style=";margin-top:10px;margin-bottom:20px;padding:0px 0px 0px 0px;">
                		<table class="table_list">
                			<colgroup> 
                				<col style="width:10%">
                				<col style="width:10%">
                				<col style="width:10%">
                				<col style="width:10%">
                				<col style="width:10%">      
								<col style="width:10%">         				
                				 
                			</colgroup>
                			<thead>
	                			<tr> 	
						    		<th>
						        		권한명
						        	</th>
						        	<td>
						        		<input id="auth_Group_nm" class="inputbox" style="width:100%">
						    		</td>
						    		<th>
						        		사용여부
						        	</th>
						        	<td>
						        		<select id="use_Yn" name="use_Yn" style="width:100%">
						        			<option value="" selected>전체</option>
								    		<option value="Y">사용</option>
								    		<option value="N">미사용</option>
								    	</select>
						    		</td>
						    		 <th>
						        		Role_Group
						        	</th>
						        	<td>
						        		<select id="role_grouop" name="role_grouop" style="width:100%">
						        			<option value="" selected>전체</option>
								    		<option value="ROLE_ADMIN">ROLE_ADMIN</option>
								    		<option value="ROLE_USER">ROLE_USER</option>
								    	</select>								    	 
						    		</td>
						    	</tr>
						    	</tr>
					    	</thead>
				    	</table>
                	</div>
                    <!-- /.col-lg-10 -->
                </div>
           

                <!-- /.panel-heading -->
                <div class="panel-body" style="padding-top:0px;">
                    <table width="100%" class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline" id="dataTable">
                        <thead>
                            <tr role="row">
                            	<!--  th align="center" style="width:4%;font-weight:bold;">
                            		<input type="checkbox" id="chk_no" class="cb">
                            	</th>-->
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="0">권한코드</th>
                                <th class="text-center" style="width:25%;font-weight:bold;" colidx="1">권한명</th>
                                <th class="text-center" style="width:40%;font-weight:bold;" colidx="2">권한설명</th>
                                <th class="text-center" style="width:10%;font-weight:bold;" colidx="4">사용여부</th>  
                                <th class="text-center" style="width:15%;font-weight:bold;" colidx="5">Role_Group</th>                                
                            </tr>
                        </thead>
                        <tbody id="table_tbody">
                        </tbody>
                    </table>                  
                    <!-- /.table-responsive --> 
                     <div class="" id="div_count" style="margin-top:0px;text-align:left;padding:0px;"></div>
                    <div class="" id="div_page" style="margin-top:0px;text-align:center;padding:0px;"></div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
       
    </div>
    <!-- /.row -->
    
</div>    
<!-- /#page-wrapper
v_str += '<td class="text-center">' + data.authGroupid + '</td>';
						v_str += '<td class="text-center">' + data.authGroupnm + '</td>';
						v_str += '<td class="text-center">' + data.authGroupremark + '</td>';
						v_str += '<td class="text-center">' + data.useyn + '</td>'; -->

<div class="modal fade" id="groupStatus" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title text-center" id="exampleModalLongTitle"><b>권한그룹</b></h5>	        
	      </div>
	      <div class="modal-body">
	        <form id="form_authGroup" name="form_authGroup"> <!-- form_userIp -->
				<fieldset>
					<div class="form-group">
			            <label for="authGroupid" class="control-label">권한코드</label>
			            <div id="userIpForm">
                            <input id="authGroupid" type="text" class="form-control" name="authGroupid" placeholder="3자리 숫자로 입력" maxlength="3">
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="authGroupnm" class="control-label">권한명</label>
			            <div>
                            <input id="authGroupnm" type="text" class="form-control" name="authGroupnm" placeholder="AUTH_GRUOP_NM" maxlength="20">
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="authGroupremark" class="control-label">권한설명</label>
			            <div>
                            <input id="authGroupremark" type="text" class="form-control" name="authGroupremark"  placeholder="AUTH_GRUOP_REMARK" maxlength="50">
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="useyn" class="control-label">사용여부</label>
			            <div>                          
                            <select id="useyn" name="useyn" class="form-control">
						        			<option value="" selected>선택</option>
								    		<option value="Y">사용</option>
								    		<option value="N">미사용</option>
						    </select>
                        </div>
			        </div>
			        <div class="form-group">
			            <label for="roleGroup" class="control-label">role_group</label>
			            <div>
                            <select id="roleGroup" name="roleGroup" class="form-control">
						        			<option value="" selected>선택</option>
						        			<option value="ROLE_ADMIN">ROLE_ADMIN</option>
								    		<option value="ROLE_USER">ROLE_USER</option>								    		
						    </select>
                        </div>
			        </div>
				</fieldset>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-block submitBtn" id="reqNewGroup">권한생성</button>
	        <button type="button" class="btn btn-info btn-block submitBtn" id="updateGroup">변경</button>
	        <button type="button" class="btn btn-danger btn-block submitBtn" id="deleteGroup">삭제</button>
	        <button type="button" class="btn btn-secondary btn-block submitBtn" data-dismiss="modal">닫기</button>		    
	      </div>
	    </div>
	  </div>
	</div>
</div>    
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/app/adm/listAuthGroup.js"></script>