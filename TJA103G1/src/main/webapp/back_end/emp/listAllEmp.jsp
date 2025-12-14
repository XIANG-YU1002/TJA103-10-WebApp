<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    EmpService empSvc = new EmpService();
    List<EmpVO> list = empSvc.getAll();
    pageContext.setAttribute("list", list);
%>

<html>
<head>
<title>所有員工資料 - listAllEmp.jsp</title>

<style>
  table#table-1 {
    background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
    width: 1000px;
    background-color: white;
    margin-top: 5px;
    margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
    <tr><td>
         <h3>所有員工資料 - listAllEmp.jsp</h3>
         <h4><a href="<%=request.getContextPath()%>/back_end/emp/select_page.jsp">
             <img src="<%=request.getContextPath()%>/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
    </td></tr>
</table>

<table>
    <tr>
        <th>員工編號</th>
        <th>帳號</th>
        <th>密碼</th>
        <th>姓名</th>
        <th>電子信箱</th>
        <th>狀態</th>
        <th>建立時間</th>
        <th>更新時間</th>
        <th>修改</th>
        <th>刪除</th>
    </tr>
    <%@ include file="page1.file" %> 
    <c:forEach var="empVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
        <tr>
            <td>${empVO.employeeId}</td>
            <td>${empVO.account}</td>
            <td>${empVO.password}</td>
            <td>${empVO.name}</td>
            <td>${empVO.email}</td>
            <td>${empVO.status}</td>
            <td>${empVO.createTime}</td>
            <td>${empVO.updatedTime}</td>
            <td>
              <form method="post" action="<%=request.getContextPath()%>/emp/emp.do" style="margin-bottom: 0px;">
                 <input type="submit" value="修改">
                 <input type="hidden" name="employee_id" value="${empVO.employeeId}">
                 <input type="hidden" name="action" value="getOne_For_Update">
              </form>
            </td>
            <td>
              <form method="post" action="<%=request.getContextPath()%>/emp/emp.do" style="margin-bottom: 0px;">
                 <input type="submit" value="刪除">
                 <input type="hidden" name="employee_id" value="${empVO.employeeId}">
                 <input type="hidden" name="action" value="delete">
              </form>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>