<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO"); // EmpServlet.java 存入 req 的 empVO
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>員工資料修改 - update_emp_input.jsp</title>

<style>
  table#table-1 {
    width: 450px;
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
    background-color: white;
    margin-top: 1px;
    margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
    <tr><td>
         <h3>員工資料修改 - update_emp_input.jsp</h3>
         <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
    </td></tr>
</table>

<h3>資料修改:</h3>

<FORM METHOD="post" ACTION="emp.do" name="form1">
<table>
    <tr>
        <td>員工編號:<font color=red><b>*</b></font></td>
        <td><input type="TEXT" name="employee_id" value="<%=empVO.getEmployeeId()%>" readonly/></td>
    </tr>
    <tr>
        <td>帳號:</td>
        <td><input type="TEXT" name="account" value="<%=empVO.getAccount()%>" size="45"/></td>
        <td>${errorMsgs.account}</td>
    </tr>
    <tr>
        <td>密碼:</td>
        <td><input type="TEXT" name="password" value="<%=empVO.getPassword()%>" size="45"/></td>
        <td>${errorMsgs.password}</td>
    </tr>
    <tr>
        <td>姓名:</td>
        <td><input type="TEXT" name="name" value="<%=empVO.getName()%>" size="45"/></td>
        <td>${errorMsgs.name}</td>
    </tr>
    <tr>
        <td>電子信箱:</td>
        <td><input type="TEXT" name="email" value="<%=empVO.getEmail()%>" size="45"/></td>
        <td>${errorMsgs.email}</td>
    </tr>
    <tr>
        <td>狀態:</td>
        <td><input type="TEXT" name="status" value="<%=empVO.getStatus()%>" size="45"/></td>
        <td>${errorMsgs.status}</td>
    </tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="submit" value="送出修改"></FORM>

</body>
</html>