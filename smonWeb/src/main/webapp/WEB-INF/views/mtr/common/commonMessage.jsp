<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String msgGubn = request.getParameter("msgGubn");
    String appPath = request.getContextPath(); %>;
%>

<head>

<link href="<%=appPath %>/css/bootstrap.css" rel="stylesheet">
<link href="<%=appPath %>/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="<%=appPath %>/css/animate.css" rel="stylesheet">
<link href="<%=appPath %>/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=appPath %>/css/main.css" type="text/css">


<script src="<%=appPath %>/js/jqgrid/jquery-1.11.0.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Help</title>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  id= "mainBody">
    
 
<script type="text/javascript">

var msgGubn = "<%=msgGubn %>";
$(function(){

    $(document).ready(function(){
        fn_com_setMessage();
    });

    
    function fn_com_setMessage(){
        
        $.get("<%=appPath %>/views/mtr/data/message.xml", function(data) {
            $info = $(data).find(msgGubn);
            
            if($info.length > 0){
                $("#objects").html("<b>" + $info.find("objects").text() + "</b>");
                $("#header").html($info.find("header").text());
                $("#contents").html($info.find("contents").text()); 
            }
            
        });
    }
});
</script>

<div class="container">
  <h2></h2>
  <div class="panel panel-success ">
    <div class="panel-heading">화면 설명</div>
    <div id="objects" class="panel-body"></div>
    <div id="header" class="panel-body">설명 미등록</div>
  </div>
  <div class="panel panel-success">
    <div class="panel-heading">컬럼 설명</div>
    <div id="contents" class="panel-body"></div>
  </div>
</div>

    
 
</body>
</html>