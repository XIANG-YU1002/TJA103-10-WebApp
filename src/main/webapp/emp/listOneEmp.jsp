<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO"); // EmpServlet.java(Controller), 存入req的empVO物件
%>

<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>

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
    width: 800px;
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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
    <tr><td>
         <h3>員工資料 - listOneEmp.jsp</h3>
         <h4>
           <a href="<%=request.getContextPath()%>/emp/select_page.jsp">
             <img src="<%=request.getContextPath()%>/resources/images/back1.gif" width="100" height="32" border="0">回首頁
           </a>
         </h4>
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
    </tr>
    <tr>
        <td><%=empVO.getEmployeeId()%></td>
        <td><%=empVO.getAccount()%></td>
        <td><%=empVO.getPassword()%></td>
        <td><%=empVO.getName()%></td>
        <td><%=empVO.getEmail()%></td>
        <td><%=empVO.getStatus()%></td>
        <td><%=empVO.getCreateTime()%></td>
        <td><%=empVO.getUpdatedTime()%></td>
    </tr>
</table>

</body>
</html>