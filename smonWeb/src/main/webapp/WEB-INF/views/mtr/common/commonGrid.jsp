<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String pageName = request.getParameter("pageName");
%>
<head>

<jsp:include page="navigation.jsp" flush="false"/>

<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/ui.jqgrid.css" />

<script src="<%=request.getContextPath() %>/js/jqgrid/grid.locale-en.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jqgrid/jquery.jqGrid.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=pageName %></title>
</head>

<script type="text/javascript">
$(function(){
    $(window).resize(function(){
        $("#" + gridId).setGridWidth($('#gridDiv').width());
    }).resize();
});
</script>

<style type="text/css">
	.search-wrapper {
		border-bottom: 1px solid #ddd;
	}
</style>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  id= "mainBody">
    
    <div id ="pre-layer-bar" class="layer" >
        <div class="bg"></div>
        <div id="layer-bar" class="pop-layer"></div>
    </div>
  
    <div id="page-wrapper" class="gray-bg">
    	<div class="row">&nbsp;</div>
		<div class="row">
        	<div class="col-lg-12">
        		<!-- /.panel -->
            	<div class="panel panel-default">
                	<div class="panel-heading">
						<h4><%=pageName%>&nbsp;<i class="fa fa-question-circle" aria-hidden="true" onclick="popupOpen()" style="cursor: pointer;"></i></h4>
                	</div>
                	<div class="panel-body search-wrapper">
	                    <div class="row">
	                        <div class="col-lg-12">
		                        <div class="form-group">
									<div class="col-lg-2" id="comboSystem">
										<label>시스템</label>
									</div>
									<div class="col-lg-2">
										<label>&nbsp;</label>
										<div class="input-group date" data-provide="datepicker">
											<button type="button" class="btn btn-primary btn-circle search-btn" id="btn_view" title="조회">
												<i class="fa fa-search" aria-hidden="true"></i>
											</button>&nbsp;
										</div>
			                        </div>
								</div>
	                        </div>
						</div>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<div class="table-wrapper">
	                            	<!-- <div>&nbsp;</div> -->
								    <div id="gridDiv">
								    	<table width='99%'  border='0' cellspacing='0' cellpadding='0' id="myGrid"></table>
								        <div id="pager"></div>
								    </div>
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
	</div>
		
    
</div>
</body>
</html>