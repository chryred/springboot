<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String pageName = "Invalid Objects 현황";
%>
<jsp:include page="../common/commonGrid.jsp" flush="false">
    <jsp:param name="pageName" value="<%=pageName%>" />
</jsp:include>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=pageName%></title>
</head>

<style type="text/css">
    ui-progressbar {position: relative;}
    .progress-label {
         position: absolute;
         left: 40%;
         top: 4px;
         font-weight: bold;
         text-shadow: 1px 1px 0 #fff;
    }
    .layer {display:none; position:fixed; _position:absolute; top:0; left:0; width:100%; height:100%; z-index:100;}
    .layer .bg {position:absolute; top:0; left:0; width:100%; height:100%; background:#000; opacity:.5; filter:alpha(opacity=50);}
    .layer .pop-layer {display:block;}
</style>

<script type="text/javascript">
var gridId = "myGrid";
var url = "selectInvalidObjects";

var CONTENTS_HEIGHT_MINUS_PIXEL = 310;

$(function(){
    
    $(document).ready(function(){
        $("#btn_view").button({icons:{primary: 'ui-icon-search'}});
        fn_loadSystemCombo('SYSTEM', function() {
        	fn_selectGridData();
        });
    });
    
    $('#btn_view').click(function(){
    	fn_searchGrid({
    		system: $("#SYSTEM_COMBO").val()
    	});
    });
   
});

function fn_searchGrid(params) {
	$("#" + gridId).setGridParam({
		page: 1,
		postData: params
	}).trigger("reloadGrid");
};

function fn_selectGridData(){
    
//  fn_com_layer_open('layer');
 
 $("#" + gridId).jqGrid({
       url:url + '.do',
       mtype: 'POST',
       datatype: 'JSON',
       postData: {
    	   system: $("#SYSTEM_COMBO").val()
       },
       colNames: ["DB_NAME", 
       			  "OWNER", 
                  "오브젝트 타입",
                  "오브젝트 명",
                  "생성일자"
                 ],
       colModel: [
                   {id : 'DB_NAME', name: 'DB_NAME', index: 'DB_NAME', width: "200px", align: 'left'}
                  ,{id : 'OWNER', name: 'OWNER', index: 'OWNER', width: "200px", align: 'left'}
                  ,{id : 'OBJECT_TYPE', name: 'OBJECT_TYPE', index: 'OBJECT_TYPE', width: "300px", align: 'left'}
                  ,{id : 'OBJECT_NAME', name: 'OBJECT_NAME', index: 'OBJECT_NAME', width: "300px", align: 'left'}
                  ,{id : 'CREATE_DT', name: 'CREATE_DT', index: 'CREATE_DT', width: "150px", align: 'center'}
                 ],
	 	  shrinkToFit: false,
	 	  autowidth: true,
       height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
       rowNum:20,
       rowList:[20,30,40],
       pager : '#pager',
       rownumbers: true,
       loadComplete: function () {
//            fn_com_layer_close('layer');
       },
       loadError: function (jqXHR, textStatus, errorThrown) {
          if(jqXHR.status == '0'){
              alert("이미 조회 중 입니다.");
          }
          alert('HTTP status code: ' + jqXHR.status + '\n' +
             'textStatus: ' + textStatus + '\n' +
             'errorThrown: ' + errorThrown); 
       }
   });
}
</script>

</html>