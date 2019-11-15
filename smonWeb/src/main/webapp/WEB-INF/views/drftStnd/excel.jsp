<%@ page language="java" contentType="application/vnd.ms-excel;charset=UTF-8" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "java.text.SimpleDateFormat" %>
<%  
	// 출력 데이터 받기
	Object obj = request.getAttribute("list");
	obj = (obj == null) ? new ArrayList() : obj;
	List<Map<String, String>> list = (List) obj;
	
	String fileName = list.get(0).get("YMD").toString() + "_" + list.get(0).get("SBJ").toString();
	
	// 한글파일명 깨짐 방지
	response.setHeader("Content-Disposition", "attachment; filename="+new String((fileName).getBytes("KSC5601"),"8859_1")+".xls");
	response.setHeader("Content-Description", "JSP Generated Date");
	
	
  
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>엑셀 다운로드</title>
</head>
<body>
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align = "center">
				<th scope="col" bgcolor="CDCDCD">이름</th>
				<th scope="col" bgcolor="CDCDCD">참석여부</th>
			</tr>
		</thead>
		<tbody>
		<% for(int i=0; i<list.size(); i++){	%>
			<tr>
				<td style="width:20%; text-align:center;"><%= list.get(i).get("NAME") %> </td>
			<% if(list.get(i).get("ATTNDYN").equals("1")){ %>
				<td style="width:20%; text-align:center;">참석</td>
			</tr>
			<%} else { %>
				<td style="width:20%; text-align:center;">미참석 </td>
			</tr>
			<%} %>
		<%} %>
		</tbody>			
	</table>
	

</body>
</html>