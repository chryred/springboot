<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String pageName = "익일 삭제될 object 현황";
%>
<jsp:include page="../common/commonGrid.jsp" flush="false">
    <jsp:param name="pageName" value="<%=pageName%>" />
</jsp:include>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    .layer .pop-layer2 {display:block;}
   
</style>

<script type="text/javascript">
var gridId = "myGrid";
var url = "selectTempTablePasing";

var CONTENTS_HEIGHT_MINUS_PIXEL = 310;

$(function(){
    
    $(document).ready(function(){
        $("#btn_view").button({icons:{primary: 'ui-icon-search'}});
        fn_loadSystemCombo('SYSTEM', function() {
        	fn_selectGridData();
        });
    });

    $('#btn_view').click(function(){
        fn_search(gridId, url);
    });
   
    function fn_selectGridData(){
        
        $("#" + gridId).jqGrid({
              url:url + '.do',
              mtype: 'POST',
              datatype: 'JSON',
              postData: {
              },
              colNames: ["소유자", 
                         "오브젝트명",
                         "생성일자",
                         "구분"],
              colModel: [
                          {id : 'OWNER', name: 'OWNER', index: 'OWNER', width: 5, align: 'center'}
                         ,{id : 'OBJECT_NAME', name: 'OBJECT_NAME', index: 'OBJECT_NAME', width: 15, align: 'left'}
                         ,{id : 'CREATED', name: 'CREATED', index: 'CREATED', width: 10, align: 'center'}
                         ,{id : 'TYPE', name: 'TYPE', index: 'TYPE', width: 10, align: 'left'}
                        ],
              autowidth: true,
              gridview: true,     
              rownumbers: true,
              rowNum:20,
              rowList:[20,30,40],
              pager : '#pager',
              forceFit : true,
              cellEdit: false,    //클릭한 셀만 EDIT
              cellsubmit:'clientArray',
              viewrecords: true,  
              height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
              loadComplete: function () {
              },
              loadError: function (jqXHR, textStatus, errorThrown) {
                 alert('HTTP status code: ' + jqXHR.status + '\n' +
                    'textStatus: ' + textStatus + '\n' +
                    'errorThrown: ' + errorThrown); 
              }
          });
    }
    

});
</script>

</html>