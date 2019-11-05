<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	
	var global = {
		isNew: null,
		seq: null
	};
	global.isNew = '<%=request.getParameter("isNew")%>';
	global.seq = '<%=request.getParameter("seq")%>';
</script>

<!-- lib -->
<%-- <script src="<%=request.getContextPath() %>/js/jquery-ui.js" type="text/javascript"></script> --%>
<script src="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.form.min.js"></script>
<script src="<%=request.getContextPath() %>/js/ckeditor/ckeditor.js"></script>
<script src="<%=request.getContextPath() %>/js/ckeditor/plugins/codesnippet/lib/highlight/highlight.pack.js"></script>
<!-- css -->
<link href="<%=request.getContextPath() %>/bootstrap/bower_components/datepicker/datepicker.css" rel="stylesheet">
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/jquery-ui.css" /> --%>
<%-- <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/css/theme.css" /> --%>
<link href="<%=request.getContextPath() %>/css/app/wiki/detailWiki.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/js/ckeditor/plugins/codesnippet/lib/highlight/styles/default.css" rel="stylesheet">

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>온라인 업무가이드</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;업무가이드 조회
                </div>
                <div class="panel-body">
                	<div class="row form-group">
			        	<div class="title-bar">
			        		<h3><span id="work"></span>&nbsp;<span id="title"></span></h3>
			        		<span id="fileList" ></span>
			        	</div>
				    </div>
					<div class="row form-group">
			            <div id="contents" ></div>
                        <textarea class="form-control" rows="3" name="editor" id="editor"></textarea>
				    </div>
				    <div class="row btn-wrap">
				        <div class="text-right col-lg-3 col-lg-offset-9 col-xs-7 col-xs-offset-5">
				            <button id="share" class="btn btn-default"><i class="fa fa-share-alt fa-2x" aria-hidden="true"></i></button>
				            <button id="cancel" class="btn btn-default" onclick="javascript: location.href = '<%=request.getContextPath() %>/wiki/list.do';">목록</button>
				            <button id="edit" class="btn btn-primary">편집</button>
				        </div>
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

<div class="modal fade" id="share_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">블라썸으로 공유</h4>
            </div>
            <div class="modal-body">
                <div class="tab-pane fade active in" id="uploadWithUrl">
					<div class="form-group">
                    	<label class="control-label" for="receivers">수신자</label>
                        <input class="form-control" id="receivers" type="text" title="수신자" placeholder="사번">
                    </div>
                    <div class="form-group">
                    	<label class="control-label" for="share_title">공유제목</label>
                        <input class="form-control" id="share_title" type="text" title="공유제목" placeholder="미지정 시, 해당 업무제목으로 설정">
                    </div>
                </div>
                <br>
                <div class="col-lg-4 col-lg-offset-8">
                    <button type="button" class="btn btn-primary" id="send_blossom">보내기</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath() %>/js/app/wiki/detailWiki.js"></script>    
