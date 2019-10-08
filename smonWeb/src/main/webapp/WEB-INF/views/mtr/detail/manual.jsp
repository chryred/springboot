<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String appPath = request.getContextPath();
%>
<head>
<%
    String pageName = "장애 대응 메뉴얼";
%>

<jsp:include page="../common/navigation.jsp" flush="false"/>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Help</title>

</head>

<script type="text/javascript">
var msgGubn = "soss";

$(function(){

    $(document).ready(function(){
        fn_com_setMessage();
    });


    function fn_com_setMessage(){

       $.get("<%=appPath %>/views/mtr/data/manual.xml", function(data) {

            $info = $(data).find($('#systemCombo option:selected').val());

            if($info.length > 0){
                // $("#objects").html("<b>" + $info.find("objects").text() + "</b>");
                $("#web").html($info.find("web").text());
                $("#db").html($info.find("db").text());
            }

        });
    }

    $("#systemCombo").change(function() {
        fn_com_setMessage();
    });
});


</script>

    <div id="page-wrapper">

		  <!-- <form name="systemCombo" style="margin-bottom:10px;">
			  시스템 선택


			  <div class="form-group">
                                            <label>Selects</label>
                                            <select class="form-control">
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </select>
                                        </div>
		  </form> -->
		  <div class="panel-body">
              <div class="row">
                  <div class="col-lg-3">
					  <form role="form">
						  <div class="form-group">
						      <label>시스템</label>
							  <select class="form-control" id ="systemCombo" name="systemCombo">
					            <option value="soss" selected="selected">운영정보</option>
					            <option value="sso">통합정보</option>
					            <option value="imp">수입통관</option>
					            <option value="company">법인영업</option>
					            <option value="delivery">배송</option>
					            <!-- <option value="prm">PRM</option>
					            <option value="academy">아카데미</option> -->
					            <option value="paperless">PAPERLESS</option>
					            <!--option value="edi">EDI</option-->
					            <option value="smart">스마트허브,EDI</option>
					            <option value="pss">협력사원지원</option>
					            <!--option value="group">그룹정보</option-->
					            <option value="dw">분석</option>
					            <option value="crm">통합고객시스템</option>
					          </select>
						  </div>
					  </form>
			      </div>
			  </div>
		  </div>
		  <div class="panel panel-success ">
		      <div class="panel-heading">DB</div>
		      <div id="db" class="panel-body">설명 미등록</div>
		  </div>
		  <div class="panel panel-success">
		      <div class="panel-heading">WEB</div>
		      <div id="web" class="panel-body"></div>
		  </div>
    </div>
</div>

</body>
</html>