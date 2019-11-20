<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%
	
	String appPath = request.getContextPath();

	String userId = (String)session.getAttribute("id");
	String userName = (String)session.getAttribute("name");
	String frontUrl = "http://10.253.21.183:8080/sysvoc";
%>
      <nav class="navbar navbar-inverse">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="#"><b>백화점팀 운영관리 시스템</b></a>
          </div>
          <ul class="nav navbar-nav">
            <!-- <li class="active"><a href="#">Home</a></li> -->
            <li><a href="#" onclick="fn_selectStatusOfInterlocking()"><i class="glyphicon glyphicon-indent-left"></i>&nbsp;연동현황</a></li>
            <li><a href="<%=request.getContextPath() %>/main.do"><i class="fa fa-dashboard fa-fw"></i>&nbsp;Dashboard</a></li>
                
    	    <c:forEach items="${ menuList }" var="item" varStatus="status">
    	   
    	     
              <c:if test="${ item.UP_MENU_ID eq '0' }">
		      	<c:if test="${ status.index ne 0}">
    	      	    </ul>
    	      	  </li>
    	        </c:if>    
                <li class="dropdown">
				  <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="${item.MENU_IMG }">
				    </i>&nbsp;${ item.MENU_NAME }
		              <span class="caret"></span>
		          </a>
		          
            	  <ul class="dropdown-menu">
            	</c:if> 
            	<c:if test="${ item.UP_MENU_ID ne '0' }">
            	
            	           	
				    <li>
				    <c:if test="${item.MENU_LINK eq '/Login_exe2.do' }">
		              <a href="<%=frontUrl %>/Login_exe2.do?writer=<%=userName %>&writer_id=<%=userId %>&system=214"><i class="glyphicon glyphicon-expand"></i>&nbsp;VOC 바로가기</a> 
		            </c:if>
		            <c:if test="${item.MENU_LINK ne '/Login_exe2.do' }">
		              <a href="<%=request.getContextPath() %>${item.MENU_LINK}"><i class="${item.MENU_IMG }"></i>&nbsp;${ item.MENU_NAME }</a>
		            </c:if>
		            </li>
            	</c:if>
	              
				<c:if test="${ status.last }">
				    </ul>
    	      	  </li>
				</c:if>
            </c:forEach>
              </ul>
    	    </li>
            <ul class="nav navbar-nav navbar-right">
              <li><a id="changePwd" class="showMask"><span class="glyphicon glyphicon-cog"></span> 비밀번호변경</a></li>
              <li><a href="#" onclick="javascript:document.frm_logout.submit();"><span class="glyphicon glyphicon-log-in"></span>Logout</a></li>
            </ul>
          </ul>
          <form name="frm_logout" action="<%=request.getContextPath() %>/logout.do" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
          </form>
        </div>          
	  </nav>