<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script language="JavaScript">
	alert("접속허용 시간이 만료되었습니다.\n다시 로그인 하시기 바랍니다.."); 
	location.href = "<%=request.getContextPath()%>/index.do";
</script>
<body>

</body>
</html>