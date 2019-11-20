<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String pageName = "TableSpace 현황";
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
var url = "selectTableSpacePaging";

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
              colNames: ["테이블스페이스명", 
                         "할당량(MB)",
                         "가용량(MB)",
                         "사용량(MB)",
                         "가용률(%)",
                         "사용률(%)",
                         "전체량(MB)"],
              colModel: [
                          {id : 'TABLESPACE_NAME', name: 'TABLESPACE_NAME', index: 'TABLESPACE_NAME', width: 20, align: 'left'}
                         ,{id : 'CURRENT_SIZE', name: 'CURRENT_SIZE', index: 'CURRENT_SIZE', width: 10, align: 'right'}
                         ,{id : 'FREE_SIZE', name: 'FREE_SIZE', index: 'FREE_SIZE', width: 10, align: 'right'}
                         ,{id : 'USED_SIZE', name: 'USED_SIZE', index: 'USED_SIZE', width: 10, align: 'right'}
                         ,{id : 'FREE_RATE', name: 'FREE_RATE', index: 'FREE_RATE', width: 10, align: 'right'}
                         ,{id : 'USED_RATE', name: 'USED_RATE', index: 'USED_RATE', width: 10, align: 'right'}
                         ,{id : 'MAX_SIZE', name: 'MAX_SIZE', index: 'MAX_SIZE', width: 10, align: 'right'}
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