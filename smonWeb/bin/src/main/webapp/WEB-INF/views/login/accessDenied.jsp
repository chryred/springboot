<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script language="JavaScript">
	alert("권한이 충분하지 않습니다.\n첫 페이지로 돌아갑니다."); 
	location.href = "<%=request.getContextPath()%>/index.do";
</script>
<body>

</body>
</html>