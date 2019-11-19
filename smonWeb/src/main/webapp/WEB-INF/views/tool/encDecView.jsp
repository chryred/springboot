<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <jsp:include page="../../common/navigation.jsp" flush="false"/> --%>
<jsp:include page="../mtr/common/navigation.jsp" flush="false"/>


<meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>


<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	var date = new Date();
</script>

<!-- Morris Charts CSS -->

<!-- lib -->
<script src="<%=request.getContextPath() %>/resources/js/ztree/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.blockUI.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/jquery.qtip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/dataTables.buttons.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/buttons.html5.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/js/jszip.min.js"></script>

<!-- css -->
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/jquery.qtip.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/toggle/bootstrap-toggle.min.css" />
<link href="<%=request.getContextPath() %>/resources/bootstrap/bower_components/datatables/media/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css" media="screen" />
<%-- <link href="<%=request.getContextPath() %>/bootstrap/bower_components/datatables/media/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="screen" /> --%>

<style type="text/css">
h2 {
	padding: 0px;
	margin: 0px;
	text-decoration:underline;
}
/* select {
	border: 1px solid #dcdcdc;
	font-size: 1em;
	height: 25px;
	padding: 2px;
	margin: 1px 0;
}

.lp0 {
	padding-left: 0;
}

table tbody td {
	min-width:30px;
	max-width:30px;
}

div.dataTables_wrapper {
        width: 100%;
        margin: 0 auto;
    }
    
div.panel-body {
	min-height:370px;
} */
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><small>ENC / DEC</small></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">    
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                	&nbsp;
                	<div style="float: left; width: 30%;"><b>처리구분</b></div>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body" style="min-height:0px;">
					<input type="radio" name="chk_gubn" value="1" checked><b><span style="font-size:12pt;" onclick="fn_chkGubnLabelClicked('chk_gubn', '1');">&nbsp;Base64&nbsp;&nbsp;</span></b>
					<input type="radio" name="chk_gubn" value="2"><b><span onclick="fn_chkGubnLabelClicked('chk_gubn', '2');" style="font-size:12pt;">&nbsp;URL&nbsp;&nbsp;</span></b>
					<input type="radio" name="chk_gubn" value="3"><b><span onclick="fn_chkGubnLabelClicked('chk_gubn', '3');" style="font-size:12pt;">&nbsp;Jasypt&nbsp;&nbsp;</span></b>
					<input type="radio" name="chk_gubn" value="4"><b><span onclick="fn_chkGubnLabelClicked('chk_gubn', '4');" style="font-size:12pt;">&nbsp;SHA-256&nbsp;&nbsp;</span></b>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->                
    </div>
    <!-- /.row -->
    <div  class="row" style="">    
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                	&nbsp;
                	<div style="float: left; width: 30%;"><b></b></div>
                </div>
                <!-- /.panel-heading -->
                <div id="div_canvas" class="panel-body" style="overflow-y:scroll;height:560px;padding:0px;">
				    <div id="div_1" class="div_move" style="background-clip:content-box;background-color:red;background-color:rgba(255,0,0,0.0);height:560px;padding:10px;">
				    	<div>
				    		<h2><b><small>Base64 인코딩/디코딩</small></b></h2>
				    	</div>
				    	<div style="margin-top:10px;">
				    		<input type="radio" name="chk_gubn2_1" value="1" checked><b><span onclick="fn_chkGubnLabelClicked('chk_gubn2_1', '1');" style="font-size:12pt;">&nbsp;Encoding&nbsp;&nbsp;</span></b>
							<input type="radio" name="chk_gubn2_1" value="2"><b><span onclick="fn_chkGubnLabelClicked('chk_gubn2_1', '2');" style="font-size:12pt;">&nbsp;Decoding&nbsp;&nbsp;</span></b>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_before_1" rows="13" style="width:100%;" placeholder="입력 문자열을 입력하세요."></textarea>
						</div>
						<div style="margin-top:10px;">
				    		<button type="button" class="btn_save btn btn-info btn-lg btn-block"><b>실행하기</b></button>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_after_1" rows="13" style="width:100%;" placeholder="결과 문자열을 보여줍니다."></textarea>
						</div>
				    </div>
				    <div id="div_2" class="div_move" style="background-clip:content-box;background-color:blue;background-color:rgba(255,0,0,0.0);height:560px;padding:10px;">
				    	<div>
				    		<h2><b><small>URL 인코딩/디코딩</small></b></h2>
				    	</div>
				    	<div style="margin-top:10px;">
				    		<input type="radio" name="chk_gubn2_2" value="1" checked><b><span onclick="fn_chkGubnLabelClicked('chk_gubn2_2', '1');" style="font-size:12pt;">&nbsp;Encoding&nbsp;&nbsp;</span></b>
							<input type="radio" name="chk_gubn2_2" value="2"><b><span onclick="fn_chkGubnLabelClicked('chk_gubn2_2', '2');" style="font-size:12pt;">&nbsp;Decoding&nbsp;&nbsp;</span></b>
						</div>
						<div style="margin-top:10px;">
				    		<input type="radio" name="chk_charsetName" value="UTF-8" checked><b><span onclick="fn_chkGubnLabelClicked('chk_charsetName', 'UTF-8');" style="font-size:12pt;">&nbsp;UTF-8&nbsp;&nbsp;</span></b>
				    		<input type="radio" name="chk_charsetName" value="euc-kr"><b><span onclick="fn_chkGubnLabelClicked('chk_charsetName', 'euc-kr');" style="font-size:12pt;">&nbsp;euc-kr&nbsp;&nbsp;</span></b>
				    		<input type="radio" name="chk_charsetName" value="ksc5601"><b><span onclick="fn_chkGubnLabelClicked('chk_charsetName', 'ksc5601');" style="font-size:12pt;">&nbsp;ksc5601&nbsp;&nbsp;</span></b>
				    		<input type="radio" name="chk_charsetName" value="iso-8859-1"><b><span onclick="fn_chkGubnLabelClicked('chk_charsetName', 'iso-8859-1');" style="font-size:12pt;">&nbsp;iso-8859-1&nbsp;&nbsp;</span></b>
				    		<input type="radio" name="chk_charsetName" value="8859_1"><b><span onclick="fn_chkGubnLabelClicked('chk_charsetName', '8859_1');" style="font-size:12pt;">&nbsp;8859_1&nbsp;&nbsp;</span></b>
				    		<input type="radio" name="chk_charsetName" value="ascii"><b><span onclick="fn_chkGubnLabelClicked('chk_charsetName', 'ascii');" style="font-size:12pt;">&nbsp;ascii&nbsp;&nbsp;</span></b>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_before_2" rows="12" style="width:100%;" placeholder="입력 문자열을 입력하세요."></textarea>
						</div>
						<div style="margin-top:10px;">
				    		<button type="button" class="btn_save btn btn-info btn-lg btn-block"><b>실행하기</b></button>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_after_2" rows="12" style="width:100%;" placeholder="결과 문자열을 보여줍니다."></textarea>
						</div>
				    </div>
				    <div id="div_3" class="div_move" style="background-clip:content-box;background-color:yellow;background-color:rgba(255,0,0,0.0);height:560px;padding:10px;">
				    	<div>
				    		<h2><b><small>Jasypt 암호화/복호화</small></b></h2>
				    	</div>
				    	<div style="margin-top:10px;">
				    		<input type="radio" name="chk_gubn2_3" value="1" checked><b><span onclick="fn_chkGubnLabelClicked('chk_gubn2_3', '1');" style="font-size:12pt;">&nbsp;Encrypt&nbsp;&nbsp;</span></b>
							<input type="radio" name="chk_gubn2_3" value="2"><b><span onclick="fn_chkGubnLabelClicked('chk_gubn2_3', '2');" style="font-size:12pt;">&nbsp;Decrypt&nbsp;&nbsp;</span></b>
						</div>
						<div style="margin-top:10px;">
				    		<select id="sel_jasyptAlg" style="height:25px;">
							</select>
						</div>
						<div style="margin-top:10px;">
				    		<input type="text" id="txt_key" style="width:100%; height:25px;" placeholder="시스템 KEY 정보를 입력하세요."/>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_before_3" rows="11" style="width:100%;" placeholder="입력 문자열을 입력하세요."></textarea>
						</div>
						<div style="margin-top:10px;">
				    		<button type="button" class="btn_save btn btn-info btn-lg btn-block"><b>실행하기</b></button>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_after_3" rows="11" style="width:100%;" placeholder="결과 문자열을 보여줍니다."></textarea>
						</div>
				    </div>
				    <div id="div_4" class="div_move" style="background-clip:content-box;background-color:yellow;background-color:rgba(255,0,0,0.0);height:560px;padding:10px;">
				    	<div>
				    		<h2><b><small>Message Digest 해시</small></b></h2>
				    	</div>
						<div style="margin-top:10px;">
				    		<select id="sel_msgDigestAlg" style="height:25px;">
							</select>
						</div>
						<div style="margin-top:10px;">
				    		<input type="text" id="txt_salt" style="width:100%; height:25px;" placeholder="Salt 정보를 입력하세요."/>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_before_4" rows="11" style="width:100%;" placeholder="입력 문자열을 입력하세요."></textarea>
						</div>
						<div style="margin-top:10px;">
				    		<button type="button" class="btn_save btn btn-info btn-lg btn-block"><b>실행하기</b></button>
						</div>
						<div style="margin-top:10px;">
				    		<textarea id="txt_after_4" rows="11" style="width:100%;" placeholder="결과 문자열을 보여줍니다."></textarea>
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

<script src="<%=request.getContextPath() %>/resources/js/app/tool/encDecView.js"></script>  				    	