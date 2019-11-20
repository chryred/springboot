<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script language="JavaScript">
	alert("다른곳에서 접속된 사용자가 있습니다.\n로그인 정책에 의거 메인화면으로 이동합니다."); 
	location.href = "<%=request.getContextPath()%>/index.do";
</script>
<body>

</body>
</html>