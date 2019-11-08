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
<link href="<%=request.getContextPath() %>/css/app/wiki/editWiki.css" rel="stylesheet">
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
                    <i class="fa fa-bullhorn fa-fw"></i><!-- <span class="fa arrow"></span> -->&nbsp;업무가이드 등록/편집
                </div>
                <div class="panel-body">
                	<div class="row form-group">
				        <label for="title" class="col-lg-1 col-xs-2 control-label input-text-label">제목</label>
				        <div class="col-lg-11 col-xs-10">
				            <input type="text" class="form-control" id="title" title="제목" placeholder="제목">
				        </div>
				    </div>
				    <div class="row form-group">
				        <label for="title" class="col-lg-1 col-xs-2 control-label input-text-label">업무명</label>
				        <div class="col-lg-11 col-xs-10">
				            <select class="form-control" id="workCode" title="업무명"></select>
				        </div>
				    </div>
					<div class="row form-group">
				        <label for="textArea" class="col-lg-1 col-xs-2 control-label input-editor-label">내용</label>
				        <div class="col-lg-11 col-xs-10">
				            <textarea class="form-control" rows="3" name="editor" id="editor"></textarea>
				        </div>
				    </div>
				    <div class="row form-group">
				        <label for="textArea" class="col-lg-1 col-xs-2 control-label input-editor-label">파일첨부</label>
				        <div class="col-lg-11 col-xs-10">
				             <button type="button" class="btn btn-primary" id="uploader_select">파일 선택</button>
				        </div>
				        <span id="fileList" ></span>
				    </div>			
				    <div class="row btn-wrap">
				        <div class="text-right col-lg-3 col-lg-offset-9 col-xs-7 col-xs-offset-5">
				        	<button id="cancel" class="btn btn-default" onclick="javascript: location.href = '<%=request.getContextPath() %>/wiki/list.do';">목록</button>
				            <button id="cancel" class="btn btn-default" onclick="javascript: history.back();">취소</button>
				            <button id="save" class="btn btn-primary">저장</button>
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

<!-- 이미지첨부 Modal -->
<div class="modal fade" id="uploader_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog uploader-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">파일 첨부</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#uploadWithFile" data-toggle="tab">파일 선택</a></li>
                </ul>
                <div class="tab-content uploader_tab">
                    <div class="tab-pane fade active in" id="uploadWithFile">
                        <div class="form-group">
                            <form id="upload_form" enctype="multipart/form-data" method="post">
                                <input class="form-control" id="uploadfile" name="uploadfile" type="file">
                                <input class="form-control" id="seq" name="seq" type="hidden">
                                <input class="form-control" id="imageGubn" name="imageGubn" type="hidden">
                                <!-- <input class="form-control" id="uploadMultiplefile" name="uploadMultiplefile" type="file" multiple="multiple"/> -->
                            </form>
                        </div>
                    </div>
                </div>
                <br>
                <div class="col-lg-4 col-lg-offset-8">
                    <button type="button" class="btn btn-primary" id="uploader_confirm">확인</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath() %>/js/app/wiki/editWiki.js"></script>    






<%-- 
<div class="container">
    <div class="row form-group">
        <label for="title" class="col-lg-1 col-xs-2 control-label input-text-label">제목</label>
        <div class="col-lg-11 col-xs-10">
            <input type="text" class="form-control" id="title" title="제목" placeholder="제목">
        </div>
    </div>
    <div class="row form-group">
        <label for="title" class="col-lg-1 col-xs-2 control-label input-radio-label">분류</label>
        <div class="col-lg-11 col-xs-10">
            <div class="radio-box">
                <% if(params.isAdmin) {%>
                <label for="notice">공지</label>
                <input type="radio" name="qna_type" id="notice" class="qna_type" value="1">
                <% } %>
                <label for="susi">수시</label>
                <input type="radio" name="qna_type" id="susi" class="qna_type" value="2">
                <label for="jungsi">정시</label>
                <input type="radio" name="qna_type" id="jungsi" class="qna_type" value="3">
                <label for="etc">기타</label>
                <input type="radio" name="qna_type" id="etc" class="qna_type" value="4">
            </div>
        </div>
    </div>
    <div class="row form-group">
        <label for="textArea" class="col-lg-1 col-xs-2 control-label input-editor-label">내용</label>
        <div class="col-lg-11 col-xs-10">
            <textarea class="form-control" rows="3" name="editor" id="editor"></textarea>
        </div>
    </div>
    <div class="row form-group">
        <label for="title" class="col-lg-1 col-xs-2 control-label input-radio-label">비공개</label>
        <div class="col-lg-11 col-xs-10">
            <div class="radio-box">
                <input type="checkbox" id="secret_flag" class="secret_flag">
            </div>
        </div>
    </div>
    <div class="row btn-wrap">
        <div class="text-right col-lg-3 col-lg-offset-9 col-xs-7 col-xs-offset-5">
            <button id="cancel" class="btn btn-default">취소</button>
            <button id="save" class="btn btn-primary">저장</button>
        </div>
    </div>
    <br>
</div>

<!-- 이미지첨부 Modal -->
<div class="modal fade" id="uploader_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog uploader-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">이미지 첨부</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#uploadWithUrl" data-toggle="tab">이미지 주소</a></li>
                    <li><a href="#uploadWithFile" data-toggle="tab">파일 선택</a></li>
                </ul>
                <div class="tab-content uploader_tab">
                    <div class="tab-pane fade active in" id="uploadWithUrl">
                        <div class="form-group">
                            <label class="control-label" for="upload_url">이미지 URL</label>
                            <input class="form-control" id="upload_url" type="text" title="이미지 URL"
                                   placeholder="http://">
                        </div>
                    </div>
                    <div class="tab-pane fade" id="uploadWithFile">
                        <div class="form-group">
                            <label class="control-label" for="upload_file">이미지 파일</label>
                            <form id="upload_form" enctype="multipart/form-data">
                                <input class="form-control" id="upload_file" name="upload_file" type="file"
                                       title="이미지 파일" accept=".jpg, .jpeg, .png">
                            </form>
                        </div>
                    </div>
                </div>
                <br>
                <div class="col-lg-4 col-lg-offset-8">
                    <button type="button" class="btn btn-primary" id="uploader_confirm">확인</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="qna_seq" value="<%=params.qnaSeq%>" />
<input type="hidden" id="userName" value="<%=params.userName%>" />
<input type="hidden" id="userMobile" value="<%=params.userMobile%>" />
 --%>