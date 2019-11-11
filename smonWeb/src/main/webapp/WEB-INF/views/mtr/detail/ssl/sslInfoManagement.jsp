<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../common/navigation.jsp" flush="false"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/resources/css/ui.jqgrid.css" />
<link href="<%=request.getContextPath() %>/resources/css/app/personalInfoCheck/personalInfoCheck.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-lg-12">
        	<!-- /.panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
					<p>SSL 인증서 확인</p>
                </div>
               
                <!-- /.panel-heading -->
                <div class="panel-body">
                            <div class="table-wrapper">
                                <table id="sslInfoDataTables" width="100%" class="table table-striped table-bordered">
                                <thead>
									<tr>
									<tr>
										<th rowspan="2">시스템명</th>
										<th rowspan="2">IP/PORT</th>
										<th rowspan="2">도메인</th>
										<th colspan="3">SSL 인증서</th>
										
									</tr>
									<tr>
									    <th>잔여일</th>
										<th>만료일시</th>
										<th>SSL 발급 정보</th>
									</tr>
								</thead>
								<tbody id="grid">

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
</div>
<!-- /#page-wrapper -->

<script src="<%=request.getContextPath() %>/resources/js/app/ssl/sslInfoCheck.js"></script>    



</body>
</html>